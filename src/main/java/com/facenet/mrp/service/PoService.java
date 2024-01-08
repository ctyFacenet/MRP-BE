package com.facenet.mrp.service;

import com.facenet.mrp.repository.sap.PoRepository;
import com.facenet.mrp.service.dto.PoDto;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.PoFilter;
import com.facenet.mrp.service.model.RequestInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PoService {

    @Autowired
    PoRepository poRepository;

    public Page<PoDto> poDtoPage(RequestInput<PoFilter> requestInput ){
        Pageable pageable = PageRequest.of(requestInput.getPageNumber(), requestInput.getPageSize());
        Page<PoDto> result = poRepository.getAllPoList(pageable, requestInput.getFilter());
        if (result != null) {
            return result;
        } else {
            throw new CustomException("record.notfound");
        }
    }

}
