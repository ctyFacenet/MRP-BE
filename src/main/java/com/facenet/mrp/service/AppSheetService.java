package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.WarehouseEntity;
import com.facenet.mrp.repository.mrp.WarehouseEntityRepository;
import com.facenet.mrp.service.dto.MaterialDTO;
import com.facenet.mrp.service.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppSheetService {
    @Value("${appsheet.appId}")
    private String appId;

    @Value("${appsheet.applicationAccessKey}")
    private String applicationAccessKey;
    private final WarehouseEntityRepository warehouseRepository;

    public AppSheetService(WarehouseEntityRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }


    public List<MaterialDTO> getData() {
        String url = "https://api.appsheet.com/api/v2/apps/" + appId + "/tables/" + Constants.Phu_tung_nhua + "/Action";

        HttpHeaders headers = new HttpHeaders();
        headers.set("ApplicationAccessKey", applicationAccessKey);
        headers.set("Content-Type", "application/json");

        // Construct the JSON body
        String jsonBody = "{\n" +
            "  \"Action\": \"Find\",\n" +
            "  \"Properties\": {},\n" +
            "  \"Rows\": []\n" +
            "}";

        // Create an HttpEntity with headers and body
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<MaterialDTO[]> response = restTemplate.exchange(url, HttpMethod.POST, entity, MaterialDTO[].class);

        return Arrays.asList(response.getBody());
    }

    @Scheduled(fixedDelay = 30 * 60 * 1000)
    @Transactional
    public void saveDataToWarehouse() {
        warehouseRepository.deleteAllByWarehouse(Constants.Warehouse.Phu_tung_nhua);
        List<MaterialDTO> materials = getData();
        List<WarehouseEntity> warehouseEntities = materials.stream()
            .map(material -> {
                WarehouseEntity entity = new WarehouseEntity();
                entity.setWarehouse(Constants.Warehouse.Phu_tung_nhua);
                entity.setItemName(material.getGhepChungloaiMausac());
                entity.setRemain(material.getTon() != null ? Double.parseDouble(material.getTon()) : 0.0);
                entity.setUnit("Cái");
                entity.setColor(material.getMauSac());
                entity.setType(material.getChungLoai());
                return entity;
            })
            .collect(Collectors.toList());
        warehouseRepository.saveAll(warehouseEntities);
    }
}
