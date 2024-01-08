package com.facenet.mrp.service.mapper;

import com.facenet.mrp.service.dto.DetailItemSyntheticDTO;
import com.facenet.mrp.service.dto.mrp.LandMarkDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DetailItemSyntheticMapper {
    @Mapping(target = "landMark", source = "landMarkDay")
    @Mapping(target = "requiredQuantity", source = "quantity")
    DetailItemSyntheticDTO detailItemSyntheticDTOAdapter(LandMarkDTO landMarkDTO);

    List<DetailItemSyntheticDTO> detailItemSyntheticDTOAdapter(List<LandMarkDTO> landMarkDTO);

}
