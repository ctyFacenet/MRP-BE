package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.PurchaseRequestDetailEntity;
import com.facenet.mrp.service.dto.PurchaseRequestDetailEntityDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PurchaseRequestDetailEntityMapper {
    PurchaseRequestDetailEntity toEntity(PurchaseRequestDetailEntityDto purchaseRequestDetailEntityDto);

    PurchaseRequestDetailEntityDto toDto(PurchaseRequestDetailEntity purchaseRequestDetailEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PurchaseRequestDetailEntity partialUpdate(PurchaseRequestDetailEntityDto purchaseRequestDetailEntityDto, @MappingTarget PurchaseRequestDetailEntity purchaseRequestDetailEntity);
}
