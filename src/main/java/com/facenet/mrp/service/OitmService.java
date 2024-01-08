package com.facenet.mrp.service;

import com.facenet.mrp.domain.sap.OitmEntity;
import com.facenet.mrp.repository.sap.OitmRepository;
import com.facenet.mrp.service.dto.ItemInfoDTO;
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
public class OitmService {

    @Autowired
    OitmRepository oitmRepository;
    @Autowired
    OitmMapper oitmMapper;

    public Page<OitmEntity> getOitmList(RequestInput<OitmFilter> requestInput) {
        Pageable pageable = PageRequest.of(requestInput.getPageNumber(), requestInput.getPageSize());
        Page<OitmEntity> resultData;

        resultData = oitmRepository.getOitmList(pageable, requestInput.getFilter());
        if (resultData != null) {
            return resultData;
        } else {
            throw new CustomException("record.notfound");
        }
    }

    public CommonResponse getItemInfo(String itemCode) {
        OitmEntity item = oitmRepository.getByItemCode(itemCode);
        if (item == null) throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        return new CommonResponse().success().data(new ItemInfoDTO(item));
    }
}
