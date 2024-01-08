package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.sap.OitwEntity;
import com.facenet.mrp.service.dto.OitwDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OitwMapper {

    @Mapping(target = "onHand", source = "entity.onHand")
    @Mapping(target = "whsCode", source = "entity.owhsEntity.whsCode")
    @Mapping(target = "whsName", source = "entity.owhsEntity.whsName")
    OitwDTO entityToDTO (OitwEntity entity);
}
