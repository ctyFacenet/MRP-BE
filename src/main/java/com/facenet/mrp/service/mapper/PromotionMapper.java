package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.PromotionEntity;
import com.facenet.mrp.service.dto.PriceListDTO;
import com.facenet.mrp.service.dto.PromotionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PromotionMapper {

    @Mapping(target = "vendorCode", source = "vendorCode")
    @Mapping(target = "itemCode", source = "itemCode")
    @Mapping(target = "timeStart", source = "promotionDTO.startDate")
    @Mapping(target = "timeEnd", source = "promotionDTO.endDate")
    @Mapping(target = "note", source = "promotionDTO.note")
    @Mapping(target = "rangeStart", source = "priceListDTO.minQuantity")
    @Mapping(target = "rangeEnd", source = "priceListDTO.maxQuantity")
    @Mapping(target = "price", source = "priceListDTO.unitPrice")
    PromotionEntity dtoToEntity (String vendorCode, String itemCode, PromotionDTO promotionDTO, PriceListDTO priceListDTO);


}
