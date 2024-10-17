package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.ProductOrderDetail;
import com.facenet.mrp.service.dto.ProductOrderDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductOrderDetailMapper {

    @Mapping(target = "id", source = "productOrderDetail.id")
    @Mapping(target = "productId", source = "productOrderDetail.id")
    @Mapping(target = "productCode", source = "productOrderDetail.productCode")
    @Mapping(target = "productName", source = "productOrderDetail.productName")
    @Mapping(target = "itemGroup", constant = "TP")
    @Mapping(target = "orderQuantity", source = "productOrderDetail.quantity")
    @Mapping(target = "instockQuantity", source = "instockQuantity")
    @Mapping(target = "missingQuantity", source = "missingQuantity")
    @Mapping(target = "orderedTime", source = "productOrderDetail.orderDate")
    @Mapping(target = "deliveryTime", source = "productOrderDetail.deliverDate")
    @Mapping(target = "statusPlanning", source = "productOrderDetail.statusPlanning")
    @Mapping(target = "supplyMethod", source = "productOrderDetail.supplyType")
    @Mapping(target = "priority", source = "productOrderDetail.priority")
    @Mapping(target = "bomVersion", source = "productOrderDetail.bomVersion")
    @Mapping(target = "bomStatus", source = "productOrderDetail.bomStatus")
    @Mapping(target = "status", source = "productOrderDetail.status")
    @Mapping(target = "note", source = "productOrderDetail.note")
    @Mapping(target = "isActive", source = "productOrderDetail.isActive")
    @Mapping(target = "mrpPoId", source = "productOrderDetail.productOrderCode.mrpPoId")
    ProductOrderDetailDto entityToDto(ProductOrderDetail productOrderDetail, Double instockQuantity, String missingQuantity);

    @Mapping(target = "productCode", source = "dto.productCode")
    @Mapping(target = "productName", source = "dto.productName")
    @Mapping(target = "quantity", source = "dto.orderQuantity")
    @Mapping(target = "orderDate", source = "dto.orderedTime")
    @Mapping(target = "deliverDate", source = "dto.deliveryTime")
    @Mapping(target = "supplyType", source = "dto.supplyMethod")
    @Mapping(target = "priority", source = "dto.priority")
    @Mapping(target = "bomVersion", source = "dto.bomVersion")
    @Mapping(target = "bomStatus", source = "dto.bomStatus")
    @Mapping(target = "note", source = "dto.note")
    @Mapping(target = "productOrderCode.mrpPoId", source = "mrpPoId")
    ProductOrderDetail dtoToEntity(ProductOrderDetailDto dto);

}
