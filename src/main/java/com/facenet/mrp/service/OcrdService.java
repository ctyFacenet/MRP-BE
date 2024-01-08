package com.facenet.mrp.service;

import com.facenet.mrp.domain.sap.OcrdEntity;
import com.facenet.mrp.domain.sap.OitmEntity;
import com.facenet.mrp.repository.sap.OcrdRepository;
import com.facenet.mrp.repository.sap.OitmRepository;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.OitmMapper;
import com.facenet.mrp.service.model.OitmFilter;
import com.facenet.mrp.service.model.RequestInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OcrdService {

    @Autowired
    OcrdRepository ocrdRepository;

    public CommonResponse<String> getVendorName(String vendorCode) {
        if (ocrdRepository.existsByCardCode(vendorCode)) {
            OcrdEntity ocrdEntity = ocrdRepository.getVendor(vendorCode);
            return new CommonResponse().data(ocrdEntity.getCardName()).success();
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.vendor" , vendorCode);
        }
    }
}
