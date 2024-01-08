package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.sap.OitmEntity;
import com.facenet.mrp.service.dto.OitmDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OitmMapper {

    public OitmDTO toDTO(OitmEntity oitmEntity) {
        return new OitmDTO(oitmEntity);
    }

    public List<OitmDTO> mapList(List<OitmEntity> oitmEntities) {
        return oitmEntities.stream().filter(Objects::nonNull).map(this::toDTO).collect(Collectors.toList());
    }
}
