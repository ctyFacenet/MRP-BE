package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.sap.Itt1Entity;
import com.facenet.mrp.service.dto.sap.Itt1Dto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface Itt1Mapper {
    Itt1Entity toEntity(Itt1Dto itt1Dto);

    Itt1Dto toDto(Itt1Entity itt1Entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Itt1Entity partialUpdate(Itt1Dto itt1Dto, @MappingTarget Itt1Entity itt1Entity);
}
