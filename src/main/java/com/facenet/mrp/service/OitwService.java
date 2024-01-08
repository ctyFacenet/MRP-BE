package com.facenet.mrp.service;

import com.facenet.mrp.domain.sap.OitwEntity;
import com.facenet.mrp.repository.sap.OitwRepository;
import com.facenet.mrp.service.dto.OitwDTO;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.OitwMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OitwService {

    @Autowired
    OitwRepository repository;

    @Autowired
    OitwMapper mapper;

    public List<OitwDTO> listByItemCode(String itemCode){
        if(StringUtils.isEmpty(itemCode)){
            throw new CustomException("item_code.can.not.empty");
        }
        List<OitwEntity> oitwEntities = repository.getOitwEntityByItemCode(itemCode);
        if(CollectionUtils.isEmpty(oitwEntities)){
            throw new CustomException("record.not.found");
        }
        return oitwEntities.stream()
            .map(oitwEntity -> mapper.entityToDTO(oitwEntity))
            .collect(Collectors.toList());
    }
}
