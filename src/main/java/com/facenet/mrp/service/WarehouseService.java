package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.ItemEntity;
import com.facenet.mrp.domain.mrp.WarehouseEntity;
import com.facenet.mrp.repository.mrp.ItemRepository;
import com.facenet.mrp.repository.mrp.WarehouseEntityRepository;
import com.facenet.mrp.service.dto.mrp.WarehouseEntityDto;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.mapper.WarehouseEntityMapper;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.utils.Constants;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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
        Set<String> itemCodes = new HashSet<>();

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }

            String firstCellValue = getCellValueAsString(row.getCell(0));
            if (firstCellValue == null || firstCellValue.isEmpty()) {
                break;
            }

            String itemCode = getCellValueAsString(row.getCell(1));
            itemCodes.add(itemCode);

            WarehouseEntity entity = new WarehouseEntity();
            entity.setWarehouse(type);
            entity.setItemCode(itemCode);
            entity.setItemName(getCellValueAsString(row.getCell(2)));
            entity.setUnit(getCellValueAsString(row.getCell(3)));
            entity.setRemain(getCellValueAsDouble(row.getCell(4)));

            warehouseEntities.add(entity);
        }

        workbook.close();

        // Fetch existing item codes
        Map<String, ItemEntity> existingItems = itemRepository.getAllByItemCodeInMap(itemCodes);

        // Filter out the warehouse entities with non-existing item codes
        List<WarehouseEntity> validWarehouseEntities = warehouseEntities.stream()
            .filter(entity -> existingItems.containsKey(entity.getItemCode()))
            .collect(Collectors.toList());

        // Delete existing records for the warehouse type
        if(type == Constants.Warehouse.Hoa_an || type == Constants.Warehouse.Cty){
            warehouseRepository.deleteAllByWarehouse(type);
        }

        warehouseRepository.saveAll(validWarehouseEntities);
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

    private Double getCellValueAsDouble(Cell cell) {
        if (cell == null) {
            return 0.0;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Double.parseDouble(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                return 0.0;
            }
        } else {
            return 0.0;
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
}
