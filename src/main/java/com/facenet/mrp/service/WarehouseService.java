package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.ItemEntity;
import com.facenet.mrp.domain.mrp.WarehouseEntity;
import com.facenet.mrp.domain.sap.OitmEntity;
import com.facenet.mrp.repository.mrp.ItemRepository;
import com.facenet.mrp.repository.mrp.WarehouseEntityRepository;
import com.facenet.mrp.service.dto.InventoryDetailDTO;
import com.facenet.mrp.service.dto.OitmDTO;
import com.facenet.mrp.service.dto.mrp.WarehouseEntityDto;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.OitmMapper;
import com.facenet.mrp.service.mapper.WarehouseEntityMapper;
import com.facenet.mrp.service.model.OitmFilter;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.model.RequestInput;
import com.facenet.mrp.service.utils.Constants;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WarehouseService {
    private final WarehouseEntityRepository warehouseRepository;
    private final ItemRepository itemRepository;

    @Autowired
    private OitmService oitmService;

    @Autowired
    private OitmMapper oitmMapper;

    @Autowired
    private WarehouseChosenService warehouseChosenService;
    private final WarehouseEntityMapper warehouseEntityMapper;
    public WarehouseService(WarehouseEntityRepository warehouseRepository, ItemRepository itemRepository, WarehouseEntityMapper warehouseEntityMapper) {
        this.warehouseRepository = warehouseRepository;
        this.itemRepository = itemRepository;
        this.warehouseEntityMapper = warehouseEntityMapper;
    }

    @Transactional
    public void importExcelData(MultipartFile file, Integer type) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        List<WarehouseEntity> warehouseEntities = new ArrayList<>();
        Set<String> itemCodesFromFile = new HashSet<>();

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }

            String firstCellValue = getCellValueAsString(row.getCell(0));
            if (firstCellValue == null || firstCellValue.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "excel.is.empty");
            }

            String itemCode = getCellValueAsString(row.getCell(1));
            itemCodesFromFile.add(itemCode);

            WarehouseEntity entity = new WarehouseEntity();
            entity.setWarehouse(type);
            if(type == Constants.Warehouse.Hoa_an){
                entity.setWhsCode("KHA");
            } else if(type == Constants.Warehouse.Cty){
                entity.setWhsCode("KVTCT");
            } else if(type == Constants.Warehouse.Tp){
                entity.setWhsCode("KTP");
            }
            entity.setItemCode(itemCode);
            entity.setItemName(getCellValueAsString(row.getCell(2)));
            entity.setUnit(getCellValueAsString(row.getCell(3)));
            entity.setRemain(getCellValueAsLong(row.getCell(4)));

            warehouseEntities.add(entity);
        }

        workbook.close();

        // Fetch existing item codes
        Map<String, ItemEntity> existingItems = itemRepository.getAllByItemCodeInMap(itemCodesFromFile);
        Set<String> validItemCodes = existingItems.keySet();
        // Filter out the warehouse entities with non-existing item codes
        List<WarehouseEntity> validWarehouseEntities = warehouseEntities.stream()
            .filter(entity -> existingItems.containsKey(entity.getItemCode()))
            .collect(Collectors.toList());

        // Fetch existing warehouse entities by warehouse type
        List<WarehouseEntity> existingWarehouseEntities = warehouseRepository.findAllByWarehouse(type);
        Map<String, WarehouseEntity> existingWarehouseEntitiesMap = existingWarehouseEntities.stream()
            .collect(Collectors.toMap(WarehouseEntity::getItemCode, entity -> entity));

        // Update existing entities and prepare new entities
        List<WarehouseEntity> entitiesToSave = new ArrayList<>();
        for (WarehouseEntity entity : validWarehouseEntities) {
            if (existingWarehouseEntitiesMap.containsKey(entity.getItemCode())) {
                // Update existing entity
                WarehouseEntity existingEntity = existingWarehouseEntitiesMap.get(entity.getItemCode());
                existingEntity.setItemName(entity.getItemName());
                existingEntity.setUnit(entity.getUnit());
                existingEntity.setRemain(entity.getRemain());
                entitiesToSave.add(existingEntity);
            } else {
                // Add new entity
                entitiesToSave.add(entity);
            }
        }

        // Identify entities to delete
        List<WarehouseEntity> entitiesToDelete = existingWarehouseEntities.stream()
            .filter(existingEntity -> !validItemCodes.contains(existingEntity.getItemCode()))
            .collect(Collectors.toList());

        // Perform database operations
        warehouseRepository.deleteAll(entitiesToDelete);
        warehouseRepository.saveAll(entitiesToSave);
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return null;
        }
    }

    private long getCellValueAsLong(Cell cell) {
        if (cell == null) {
            return 0L;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            // Convert numeric value to long
            return (long) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                // Parse string as double and convert to long
                return (long) Double.parseDouble(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                return 0L;
            }
        } else {
            return 0L;
        }
    }

    @Transactional(readOnly = true)
    public PageResponse<List<WarehouseEntityDto>> getAll(PageFilterInput<WarehouseEntityDto> input) {
        Pageable pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
        Specification<WarehouseEntity> spec = createSpecification(input.getFilter());

        Page<WarehouseEntity> page = warehouseRepository.findAll(spec, pageable);

        PageResponse<List<WarehouseEntityDto>> response = new PageResponse<>(page.getTotalElements());
        response.setData(page.map(warehouseEntityMapper::toDto).getContent());
        response.result("00", "Success", true);
        return response;
    }

    private Specification<WarehouseEntity> createSpecification(WarehouseEntityDto filter) {
        return (root, query, criteriaBuilder) -> {
            // Add conditions based on the filter
            Predicate predicate = criteriaBuilder.conjunction();

            if (filter.getWarehouse() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("warehouse"), filter.getWarehouse()));
            }
            if (filter.getItemCode() != null && !filter.getItemCode().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("itemCode"), "%" + filter.getItemCode() + "%"));
            }
            if (filter.getItemName() != null && !filter.getItemName().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("itemName"), "%" + filter.getItemName() + "%"));
            }
            if (filter.getRemain() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("remain"), filter.getRemain()));
            }
            if (filter.getUnit() != null && !filter.getUnit().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("unit"), "%" + filter.getUnit() + "%"));
            }
            if (filter.getColor() != null && !filter.getColor().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("color"), "%" + filter.getColor() + "%"));
            }

            return predicate;
        };
    }

    public List<InventoryDetailDTO> getInventoryDetails(String itemCode, Integer type) {
        List<InventoryDetailDTO> inventoryDetails = new ArrayList<>();
        if(type == Constants.Warehouse.Hoa_an){
            // Get details for type 2 (Kho Hòa An)
            List<InventoryDetailDTO> khoHoaAnDetails = warehouseRepository.getInventoryDetailByItemCode(itemCode, 2)
                .stream()
                .map(detail -> {
                    detail.setVendorId("KHA");
                    detail.setVendorName("Kho Hòa An");
                    setDefaultValuesIfNull(detail);
                    return detail;
                })
                .collect(Collectors.toList());
            if (khoHoaAnDetails.isEmpty()) {
                InventoryDetailDTO defaultDetail = new InventoryDetailDTO();
                defaultDetail.setVendorId("KHA");
                defaultDetail.setVendorName("Kho Hòa An");
                setDefaultValuesIfNull(defaultDetail);
                khoHoaAnDetails.add(defaultDetail);
            }
            inventoryDetails.addAll(khoHoaAnDetails);
        } else if (type == Constants.Warehouse.Cty){
            // Get details for type 3 (Kho vật tư công ty)
            List<InventoryDetailDTO> khoVatTuCongTyDetails = warehouseRepository.getInventoryDetailByItemCode(itemCode, 3)
                .stream()
                .map(detail -> {
                    detail.setVendorId("KVTCT");
                    detail.setVendorName("Kho vật tư công ty");
                    setDefaultValuesIfNull(detail);
                    return detail;
                })
                .collect(Collectors.toList());
            if (khoVatTuCongTyDetails.isEmpty()) {
                InventoryDetailDTO defaultDetail = new InventoryDetailDTO();
                defaultDetail.setVendorId("KVTCT");
                defaultDetail.setVendorName("Kho vật tư công ty");
                setDefaultValuesIfNull(defaultDetail);
                khoVatTuCongTyDetails.add(defaultDetail);
            }
            inventoryDetails.addAll(khoVatTuCongTyDetails);
        }
        return inventoryDetails;
    }

    private void setDefaultValuesIfNull(InventoryDetailDTO detail) {
        if (detail.getInStockTotal() == 0) {
            detail.setInStockTotal(0);
        }
        if (detail.getOnOrder() == 0) {
            detail.setOnOrder(0);
        }
        if (detail.getInStockMinimum() == 0) {
            detail.setInStockMinimum(0);
        }
        if (detail.getTotal() == 0) {
            detail.setTotal(0);
        }
    }

    @Transactional(readOnly = true)
    public List<WarehouseEntityDto> getAllByItemCodes(List<String> itemCodes) {
        List<String> warehouse = warehouseChosenService.getWarehouseCodes(1);
        int type;
        if (warehouse.contains("KHA")) {
            type = Constants.Warehouse.Hoa_an;
        } else if (warehouse.contains("KVTCT")) {
            type = Constants.Warehouse.Cty;
        } else {
            type = 0;
        }

        Specification<WarehouseEntity> spec = (root, query, criteriaBuilder) -> {
            Predicate itemCodePredicate = root.get("itemCode").in(itemCodes);

            Predicate typePredicate = criteriaBuilder.equal(root.get("type"), type);
            return criteriaBuilder.and(itemCodePredicate, typePredicate);
        };

        List<WarehouseEntity> warehouseEntities = warehouseRepository.findAll(spec);

        return warehouseEntities.stream()
            .map(warehouseEntityMapper::toDto)
            .collect(Collectors.toList());
    }
}
