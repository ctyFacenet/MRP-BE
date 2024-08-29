package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.VendorEntity;
import com.facenet.mrp.service.dto.mrp.VendorEntityDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface VendorEntityMapper {
    VendorEntity toEntity(VendorEntityDto vendorEntityDto);

    VendorEntityDto toDto(VendorEntity vendorEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VendorEntity partialUpdate(VendorEntityDto vendorEntityDto, @MappingTarget VendorEntity vendorEntity);
}
