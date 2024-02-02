package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.PurchaseHasRecommendationEntity;
import com.facenet.mrp.domain.mrp.PurchaseRecommendationEntity;
import com.facenet.mrp.security.SecurityUtils;
import com.facenet.mrp.service.dto.sap.PurchaseRequestApiDTO;
import com.facenet.mrp.service.dto.sap.PurchaseRequestDetailApiDTO;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseRequestApiMapper {
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public PurchaseRequestApiDTO toDTO(PurchaseRecommendationEntity purchaseRecommendationEntity, List<PurchaseRequestDetailApiDTO> plans, PurchaseHasRecommendationEntity purchaseHasRecommendationEntity) {
        PurchaseRequestApiDTO purchaseRequestDTO = new PurchaseRequestApiDTO();
        purchaseRequestDTO.setType("PR");
        purchaseRequestDTO.setUser(SecurityUtils.getUserSap());
        purchaseRequestDTO.setTimeRequestPR(simpleDateFormat.format(purchaseHasRecommendationEntity.getDueDate()));
        purchaseRequestDTO.setRemark(purchaseHasRecommendationEntity.getNote());
        purchaseRequestDTO.setMrpSubCode(purchaseRecommendationEntity.getMrpSubCode());
        purchaseRequestDTO.setSoCode(purchaseRecommendationEntity.getMrpPoId());
        purchaseRequestDTO.setCreateTime(simpleDateFormat.format(new Date()));
        purchaseRequestDTO.setTimeRequest(purchaseRequestDTO.getTimeRequestPR());
        purchaseRequestDTO.setTimeApproved(purchaseRequestDTO.getCreateTime());
        purchaseRequestDTO.setStatus("");

        purchaseRequestDTO.setPurchaseRecommendationDetails(plans);
        return purchaseRequestDTO;
    }
}
