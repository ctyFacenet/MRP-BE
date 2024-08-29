package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.ItemEntity;
import com.facenet.mrp.service.dto.mrp.ItemEntityDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemEntityMapper {
    ItemEntity toEntity(ItemEntityDto itemEntityDto);

    ItemEntityDto toDto(ItemEntity itemEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ItemEntity partialUpdate(ItemEntityDto itemEntityDto, @MappingTarget ItemEntity itemEntity);
}
