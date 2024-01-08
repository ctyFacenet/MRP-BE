package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.MrpBomVersionDetail;
import com.facenet.mrp.service.dto.request.AddItemToBomRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MrpBomDetailMapper {

    @Mapping(target = "mrpPoId", source = "dto.mrpPoId")
    @Mapping(target = "parentPath", source = "dto.parentPath")
    @Mapping(target = "itemCode", source = "dto.itemCode")
    @Mapping(target = "itemName", source = "dto.itemDescription")
    @Mapping(target = "quantity", source = "dto.basicQuantity")
    @Mapping(target = "itemGroup", source = "dto.itemGroup")
    @Mapping(target = "wareHouse", source = "dto.whsCode")
    @Mapping(target = "level", source = "dto.level")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "active", source = "dto.active")
    MrpBomVersionDetail DTOToEntity (AddItemToBomRequest dto);
}
