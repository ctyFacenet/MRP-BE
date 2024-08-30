package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.ItemEntity;
import com.facenet.mrp.domain.mrp.WarehouseEntity;
import com.facenet.mrp.repository.mrp.ItemRepository;
import com.facenet.mrp.repository.mrp.WarehouseEntityRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WarehouseService {
    private final WarehouseEntityRepository warehouseRepository;
    private final ItemRepository itemRepository;
    public WarehouseService(WarehouseEntityRepository warehouseRepository, ItemRepository itemRepository) {
        this.warehouseRepository = warehouseRepository;
        this.itemRepository = itemRepository;
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
        warehouseRepository.deleteAllByWarehouse(type);

        // Save only the valid warehouse entities
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


}
