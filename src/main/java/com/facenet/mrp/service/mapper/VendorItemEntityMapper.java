package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.VendorItemEntity;
import com.facenet.mrp.service.dto.mrp.VendorItemEntityDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface VendorItemEntityMapper {
    VendorItemEntity toEntity(VendorItemEntityDto vendorItemEntityDto);

    VendorItemEntityDto toDto(VendorItemEntity vendorItemEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VendorItemEntity partialUpdate(VendorItemEntityDto vendorItemEntityDto, @MappingTarget VendorItemEntity vendorItemEntity);
}
