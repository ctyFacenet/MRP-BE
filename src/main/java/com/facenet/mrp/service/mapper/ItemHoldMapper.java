package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.ItemHoldEntity;
import com.facenet.mrp.domain.mrp.PurchaseRecommendationEntity;
import com.facenet.mrp.service.dto.ItemHoldDTO;
import com.facenet.mrp.service.dto.ItemSyntheticDTO;
import com.facenet.mrp.service.dto.mrp.ItemQuantity;
import com.facenet.mrp.service.dto.mrp.MaterialPlanDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Date;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ItemHoldMapper {
    @Mapping(target = "soCode", source = "productOrderCode")
    ItemHoldDTO toDTO(ItemHoldEntity entity);

    List<ItemHoldDTO> toDTO(List<ItemHoldEntity> entities);

    @Mapping(target = "itemCode", source = "itemSyntheticDTO.itemCode")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "mrpSubCode", source = "purchaseRecommendationEntity.mrpSubCode")
    @Mapping(target = "productOrderCode", source = "purchaseRecommendationEntity.mrpPoId")
    @Mapping(target = "purchaseRecommendationId", source = "purchaseRecommendationEntity.purchaseRecommendationId")
    @Mapping(target = "holdDate", source = "holdDate")
    @Mapping(target = "warehouseCode", source = "warehouseCode")
    @Mapping(target = "status", ignore = true)
    ItemHoldEntity toItemHoldEntity(ItemSyntheticDTO itemSyntheticDTO, PurchaseRecommendationEntity purchaseRecommendationEntity, double quantity, String warehouseCode,Date holdDate);

    @Mapping(target = "itemCode", source = "item.itemCode")
    @Mapping(target = "quantity", source = "totalQuantity")
    @Mapping(target = "purchaseRecommendationId", source = "purchaseRecommendationEntity.purchaseRecommendationId")
    @Mapping(target = "mrpSubCode", source = "purchaseRecommendationEntity.mrpSubCode")
    @Mapping(target = "productOrderCode", source = "purchaseRecommendationEntity.mrpPoId")
    @Mapping(target = "holdDate", source = "holdDate")
    @Mapping(target = "status", ignore = true)
    ItemHoldEntity toItemHoldEntity(MaterialPlanDTO item, PurchaseRecommendationEntity purchaseRecommendationEntity, double totalQuantity, Date holdDate);
}
