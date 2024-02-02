package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.MrpOrderQuantityEntity;
import com.facenet.mrp.service.dto.DetailItemSyntheticDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MrpOrderQuantityMapper {
    @Mapping(target = "quantity", source = "item.orderQuantity")
    @Mapping(target = "itemCode", source = "itemCode")
    @Mapping(target = "mrpSubCode", source = "mrpSubCode")
    @Mapping(target = "date", source = "item.landMark")
    MrpOrderQuantityEntity toEntity(String mrpSubCode, String itemCode, DetailItemSyntheticDTO item);
}
