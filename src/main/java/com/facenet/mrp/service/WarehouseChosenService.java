package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.WarehouseChosenEntity;
import com.facenet.mrp.repository.mrp.WarehouseChosenEntityRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WarehouseChosenService
{
    private final WarehouseChosenEntityRepository warehouseChosenEntityRepository;

    public WarehouseChosenService(WarehouseChosenEntityRepository warehouseChosenEntityRepository) {
        this.warehouseChosenEntityRepository = warehouseChosenEntityRepository;
    }

    public void addWarehouseChosen(List<String> wareHouseChosenCode)
    {
        long count = warehouseChosenEntityRepository.count();

        if(count == 0) {
            WarehouseChosenEntity warehouseChosenEntity = new WarehouseChosenEntity();
            warehouseChosenEntity.setId(1);

            String wareHouseCode = "";
            for(int i = 0; i < wareHouseChosenCode.size(); i++) {
                wareHouseCode += wareHouseChosenCode.get(i);
                if(i != wareHouseChosenCode.size()-1) {
                    wareHouseCode += ", ";
                }
            }
            setWarehouseFromList(warehouseChosenEntity, wareHouseChosenCode);
            warehouseChosenEntityRepository.save(warehouseChosenEntity);
        }
        else {
            WarehouseChosenEntity warehouseChosenEntity = warehouseChosenEntityRepository.findById(1).orElseThrow();
            setWarehouseFromList(warehouseChosenEntity, wareHouseChosenCode);
            warehouseChosenEntityRepository.save(warehouseChosenEntity);
        }
    }

    public List<String> getWarehouseCodes(int id) {
        WarehouseChosenEntity warehouseChosenEntity = warehouseChosenEntityRepository.findById(id).orElseThrow();
        String[] wareHouseCodes = warehouseChosenEntity.getWarehouse().split(", ");
        return new ArrayList<>(Arrays.asList(wareHouseCodes));
    }

    private void setWarehouseFromList(WarehouseChosenEntity warehouseChosenEntity, List<String> wareHouseChosenCode) {
        String wareHouseCode = "";
        for(int i = 0; i < wareHouseChosenCode.size(); i++) {
            wareHouseCode += wareHouseChosenCode.get(i);
            if(i != wareHouseChosenCode.size()-1) {
                wareHouseCode += ", ";
            }
        }
        warehouseChosenEntity.setWarehouse(wareHouseCode);
    }
}
