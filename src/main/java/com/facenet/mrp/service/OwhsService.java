package com.facenet.mrp.service;

import com.facenet.mrp.domain.sap.OwhsEntity;
import com.facenet.mrp.repository.sap.OwhsRepository;
import com.facenet.mrp.service.dto.response.CommonResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwhsService {
    private final OwhsRepository owhsRepository;
    private final WarehouseChosenService warehouseChosenService;
    public OwhsService(OwhsRepository owhsRepository, WarehouseChosenService warehouseChosenService) {
        this.owhsRepository = owhsRepository;
        this.warehouseChosenService = warehouseChosenService;
    }

    public CommonResponse<List<OwhsEntity>> getAllWarehouses() {
        List<String> warehouse = warehouseChosenService.getWarehouseCodes(1);
        List<OwhsEntity> warehouses = new ArrayList<>();
        if (warehouse.isEmpty()){
            warehouses = owhsRepository.findAll();
        } else {
            warehouses = owhsRepository.findByWarehouseCodes(warehouse);
        }

        // Manually add KVTCT and KHA warehouses
        OwhsEntity khoVatTuCongTy = new OwhsEntity();
        khoVatTuCongTy.setWhsCode("KVTCT");
        khoVatTuCongTy.setWhsName("Kho vật tư công ty");
        warehouses.add(khoVatTuCongTy);

        OwhsEntity khoHoaAn = new OwhsEntity();
        khoHoaAn.setWhsCode("KHA");
        khoHoaAn.setWhsName("Kho Hòa An");
        warehouses.add(khoHoaAn);

        return new CommonResponse<>()
            .success()
            .data(warehouses);
    }

    public List<String> getAllWhsCode(){
        List<String> whsCodes = new ArrayList<>();
        List<OwhsEntity> owhsEntities = owhsRepository.findAll();

        if (owhsEntities != null && !owhsEntities.isEmpty()){
            owhsEntities.stream().forEach(item -> whsCodes.add(item.getWhsCode()));
        }
        whsCodes.add("KVTCT"); // Kho vật tư công ty
        whsCodes.add("KHA");   // Kho Hòa An

        return whsCodes;
    }
}
