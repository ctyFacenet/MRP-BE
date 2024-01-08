package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.PurchaseRecommendationDetailEntity;
import com.facenet.mrp.domain.mrp.PurchaseRecommendationPurchasePlanEntity;
import com.facenet.mrp.service.dto.DetailItemSyntheticDTO;
import com.facenet.mrp.service.dto.RecommendationPlanDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RecommendationPlanMapper {

    @Mapping(target = "recommendationPurchasePlanId", source = "dto.recommendationPurchasePlanId")
    @Mapping(target = "requiredQuantity", source = "dto.requiredQuantity")
    @Mapping(target = "dueDate", source = "dto.requiredDueDate")
    @Mapping(target = "itemCode", source = "dto.itemCode")
    @Mapping(target = "note", source = "dto.note")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "batch", source = "dto.batch")
    PurchaseRecommendationPurchasePlanEntity dtoToEntity(RecommendationPlanDto dto);

    @Mapping(target = "recommendationPurchasePlanId", source = "entity.recommendationPurchasePlanId")
    @Mapping(target = "requiredQuantity", source = "entity.requiredQuantity")
    @Mapping(target = "requiredDueDate", source = "entity.dueDate")
    @Mapping(target = "itemCode", source = "entity.itemCode")
    @Mapping(target = "note", source = "entity.note")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "batch", source = "entity.batch")
    RecommendationPlanDto entityToDto(PurchaseRecommendationPurchasePlanEntity entity);

    @Mapping(target = "purchaseRecommendationDetailId", source = "prde.purchaseRecommendationDetailId")
    @Mapping(target = "requiredQuantity", source = "itemQuantityDetail.requiredQuantity")
    @Mapping(target = "dueDate", source = "itemQuantityDetail.landMark", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "itemCode", source = "prde.itemCode")
    @Mapping(target = "status", source = "status")
    PurchaseRecommendationPurchasePlanEntity toEntity(PurchaseRecommendationDetailEntity prde, DetailItemSyntheticDTO itemQuantityDetail, int status);
}
