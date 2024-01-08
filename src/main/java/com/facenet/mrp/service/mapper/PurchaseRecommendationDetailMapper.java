package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.PurchaseRecommendationDetailEntity;
import com.facenet.mrp.service.dto.mrp.MaterialPlanDTO;
import com.facenet.mrp.service.utils.Constants;
import org.springframework.stereotype.Service;

@Service
public class PurchaseRecommendationDetailMapper {
    public PurchaseRecommendationDetailEntity toPurchaseRecommendationEntity(MaterialPlanDTO item) {
        PurchaseRecommendationDetailEntity entity = new PurchaseRecommendationDetailEntity();
        entity.setItemCode(item.getItemCode());
        entity.setItemDescription(item.getItemName());
        entity.setStatus(Constants.PurchaseRecommendationDetail.STATUS_NEW);
        return entity;
    }
}
