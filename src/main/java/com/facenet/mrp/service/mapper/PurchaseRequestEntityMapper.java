package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.PurchaseRequestEntity;
import com.facenet.mrp.service.dto.PurchaseRequestEntityDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PurchaseRequestEntityMapper {
    PurchaseRequestEntity toEntity(PurchaseRequestEntityDto purchaseRequestEntityDto);

    PurchaseRequestEntityDto toDto(PurchaseRequestEntity purchaseRequestEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PurchaseRequestEntity partialUpdate(PurchaseRequestEntityDto purchaseRequestEntityDto, @MappingTarget PurchaseRequestEntity purchaseRequestEntity);
}
