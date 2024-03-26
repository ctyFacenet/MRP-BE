package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.ProductOrder;
import com.facenet.mrp.service.dto.ProductOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductOrderMapper {

    @Mapping(target = "id", source = "productOrder.id")
    @Mapping(target = "poCode", source = "productOrder.productOrderCode")
    @Mapping(target = "partCode", source = "productOrder.partCode")
    @Mapping(target = "partName", source = "productOrder.partName")
    @Mapping(target = "poType", source = "productOrder.productOrderType")
    @Mapping(target = "orderedTime", source = "productOrder.orderDate")
    @Mapping(target = "deliveryTime", source = "productOrder.deliverDate")
    @Mapping(target = "saleCode", source = "productOrder.saleCode")
    @Mapping(target = "saleName", source = "productOrder.saleName")
    @Mapping(target = "priority", source = "productOrder.priority")
    @Mapping(target = "status", source = "productOrder.status")
    @Mapping(target = "note", source = "productOrder.note")
    @Mapping(target = "isActive", source = "productOrder.isActive")
    @Mapping(target = "mrpPoId", source = "productOrder.mrpPoId")
    @Mapping(target = "quantity", source = "productOrder.quantity")
    @Mapping(target = "createdBy", source = "createdBy")
    ProductOrderDto entityToDto(ProductOrder productOrder);

}
