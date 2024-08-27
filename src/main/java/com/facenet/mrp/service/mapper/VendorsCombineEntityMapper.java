package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.VendorsCombineEntity;
import com.facenet.mrp.service.dto.VendorsCombineEntityDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface VendorsCombineEntityMapper {
    VendorsCombineEntity toEntity(VendorsCombineEntityDto vendorsCombineEntityDto);

    VendorsCombineEntityDto toDto(VendorsCombineEntity vendorsCombineEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VendorsCombineEntity partialUpdate(VendorsCombineEntityDto vendorsCombineEntityDto, @MappingTarget VendorsCombineEntity vendorsCombineEntity);
}
