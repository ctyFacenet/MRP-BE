package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.WarehouseEntity;
import com.facenet.mrp.service.dto.mrp.WarehouseEntityDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WarehouseEntityMapper {
    WarehouseEntity toEntity(WarehouseEntityDto warehouseEntityDto);

    WarehouseEntityDto toDto(WarehouseEntity warehouseEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    WarehouseEntity partialUpdate(WarehouseEntityDto warehouseEntityDto, @MappingTarget WarehouseEntity warehouseEntity);
}
