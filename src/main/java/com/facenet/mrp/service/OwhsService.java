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

    public OwhsService(OwhsRepository owhsRepository) {
        this.owhsRepository = owhsRepository;
    }

    public CommonResponse<List<OwhsEntity>> getAllWarehouses() {
        return new CommonResponse<>()
            .success()
            .data(owhsRepository.findAll());
    }

    public List<String> getAllWhsCode(){
        List<String> whsCodes = new ArrayList<>();
        List<OwhsEntity> owhsEntities = owhsRepository.findAll();

        if (owhsEntities != null && !owhsEntities.isEmpty()){
            owhsEntities.stream().forEach(item -> whsCodes.add(item.getWhsCode()));
        }
        return whsCodes;
    }
}
