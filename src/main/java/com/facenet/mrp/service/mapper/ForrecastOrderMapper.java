package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.ForecastOrderDetailEntity;
import com.facenet.mrp.service.dto.ForecastOrderDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ForrecastOrderMapper {
    @Mapping(target = "id", source = "productOrderDetail.forecastOrderDetailId")
    @Mapping(target = "productCode", source = "productOrderDetail.itemCode")
    @Mapping(target = "productName", source = "productOrderDetail.itemDescription")
    @Mapping(target = "itemGroup", source = "productOrderDetail.itemGroupCode")
    @Mapping(target = "orderQuantity", source = "productOrderDetail.totalRequest")
    @Mapping(target = "instockQuantity", source = "instockQuantity")
    @Mapping(target = "missingQuantity", source = "missingQuantity")
    @Mapping(target = "orderedTime", source = "productOrderDetail.startTime")
    @Mapping(target = "deliveryTime", source = "productOrderDetail.endTime")
    @Mapping(target = "supplyMethod", source = "productOrderDetail.note")
    @Mapping(target = "priority", source = "productOrderDetail.priority")
    @Mapping(target = "bomVersion", source = "productOrderDetail.bomVersion")
    @Mapping(target = "bomStatus", source = "productOrderDetail.status")
    @Mapping(target = "status", source = "productOrderDetail.status")
    @Mapping(target = "note", source = "productOrderDetail.note")
    @Mapping(target = "mrpPoId", source = "productOrderDetail.productOrderCode")
    ForecastOrderDetailDTO entityToDto(ForecastOrderDetailEntity productOrderDetail, Double instockQuantity, String missingQuantity);

}
