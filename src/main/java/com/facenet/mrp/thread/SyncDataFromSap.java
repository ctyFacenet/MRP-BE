package com.facenet.mrp.thread;

import com.facenet.mrp.domain.mrp.ItemHoldEntity;
import com.facenet.mrp.domain.mrp.PurchaseRecommendationDetailEntity;
import com.facenet.mrp.domain.mrp.PurchaseRecommendationEntity;
import com.facenet.mrp.domain.mrp.SapOnOrderSummary;
import com.facenet.mrp.repository.mrp.*;
import com.facenet.mrp.repository.sap.OporRepository;
import com.facenet.mrp.repository.sap.PoRepository;
import com.facenet.mrp.service.GrpoService;
import com.facenet.mrp.service.ListSaleService;
import com.facenet.mrp.service.PurchaseRequestService;
import com.facenet.mrp.service.dto.GrpoDTO;
import com.facenet.mrp.service.dto.PoDto;
import com.facenet.mrp.service.dto.PurchaseRequestDTO;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.dto.sap.PrByItemSapDTO;
import com.facenet.mrp.service.mapper.SapMaterialOnOrderMapper;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.model.PoFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SyncDataFromSap {
    private final Logger log = LogManager.getLogger(ListSaleService.class);

    @Autowired
    private OporRepository oporRepository;

    @Autowired
    private ItemHoldRepository itemHoldRepository;

    @Autowired
    private PurchaseRecommendationDetailRepository purchaseRecommendationDetailRepository;

    @Autowired
    private PurchaseRecommendationRepository purchaseRecommendationRepository;

//    @Autowired
//    private MaterialOnOrderSummaryJob materialOnOrderSummaryJob;

    @Transactional
    public void syncData() throws ParseException {
        List<ItemHoldEntity> itemHoldEntities = new ArrayList<>();
        List<PurchaseRecommendationDetailEntity> recommendationDetailEntities = new ArrayList<>();
//        List<PurchaseRecommendationPurchasePlanEntity> recommendationPurchasePlanEntities = new ArrayList<>();
        PurchaseRecommendationEntity purchaseRecommendation = new PurchaseRecommendationEntity();

        purchaseRecommendation.setMrpSubCode("RAL-MRP-0");
        purchaseRecommendation.setMrpPoId("SO-0");
        purchaseRecommendation.setActive(true);
        purchaseRecommendation.setStatus(2);

        PurchaseRecommendationEntity purchaseRecommendationEntity = purchaseRecommendationRepository.save(purchaseRecommendation);
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MONTH,-3);
        List<PrByItemSapDTO> prByItemSapDTOS = oporRepository.getPrByItemSap();
        for (PrByItemSapDTO index:prByItemSapDTOS) {
            ItemHoldEntity itemHoldEntity = new ItemHoldEntity();
            PurchaseRecommendationDetailEntity purchaseRecommendationDetail = new PurchaseRecommendationDetailEntity();

            itemHoldEntity.setPurchaseRecommendationId(purchaseRecommendationEntity.getPurchaseRecommendationId());
            itemHoldEntity.setItemCode(index.getItemCode());
            itemHoldEntity.setQuantity(Double.valueOf(index.getQuantity()));
            itemHoldEntity.setMrpSubCode("RAL-MRP-0");
            itemHoldEntity.setProductOrderCode("SO-0");
            itemHoldEntity.setHoldDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-06-01"));
            itemHoldEntity.setStatus(2);
            itemHoldEntity.setActive(true);
            itemHoldEntities.add(itemHoldEntity);

            purchaseRecommendationDetail.setPurchaseRecommendationId(purchaseRecommendationEntity.getPurchaseRecommendationId());
            purchaseRecommendationDetail.setPurchaseRecommendation(purchaseRecommendationEntity);
            purchaseRecommendationDetail.setItemCode(index.getItemCode());
            purchaseRecommendationDetail.setItemDescription(index.getItemName());
            purchaseRecommendationDetail.setActive(true);
            purchaseRecommendationDetail.setQuantity(Double.valueOf(index.getQuantity()));
            purchaseRecommendationDetail.setStatus(3);
            recommendationDetailEntities.add(purchaseRecommendationDetail);
        }

        itemHoldRepository.saveAll(itemHoldEntities);

        purchaseRecommendationDetailRepository.saveAll(recommendationDetailEntities);
//        List<PurchaseRecommendationDetailEntity> list = purchaseRecommendationDetailRepository.saveAll(recommendationDetailEntities);
//        List<PrByItemSapDTO> poByItemSapDTOS = oporRepository.getPoByItemSap();
//        for (PrByItemSapDTO index:poByItemSapDTOS) {
//            PurchaseRecommendationDetailEntity purchaseRecommendationDetail = getExist(list,index);
//            if(purchaseRecommendationDetail != null){
//                PurchaseRecommendationPurchasePlanEntity recommendationPurchasePlanEntity = new PurchaseRecommendationPurchasePlanEntity();
//                recommendationPurchasePlanEntity.setPurchaseRecommendationDetailId(purchaseRecommendationDetail.getPurchaseRecommendationDetailId());
//                recommendationPurchasePlanEntity.setItemCode(index.getItemCode());
//                recommendationPurchasePlanEntity.setRequiredQuantity(Double.valueOf(index.getQuantity()));
//                recommendationPurchasePlanEntity.setStatus(3);
//                recommendationPurchasePlanEntity.setDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-06-01"));
//                recommendationPurchasePlanEntity.setIsActive(true);
//                recommendationPurchasePlanEntities.add(recommendationPurchasePlanEntity);
//            }
//        }
//        purchaseRecommendationPlanRepository.saveAll(recommendationPurchasePlanEntities);
    }

    private PurchaseRecommendationDetailEntity getExist(List<PurchaseRecommendationDetailEntity> list,PrByItemSapDTO index){
        for (PurchaseRecommendationDetailEntity purchaseRecommendationDetail:list) {
            if(purchaseRecommendationDetail.getItemCode().equals(index.getItemCode())){
                return purchaseRecommendationDetail;
            }
        }
        return null;
    }

//    @Profile("prod")
//    public void syncPrPoGrpo() {
//        PoFilter poFilter = new PoFilter();
//        poFilter.setStatus("O");
//        materialOnOrderSummaryJob.clonePo(poFilter);
//
//        PageFilterInput<PurchaseRequestDTO> prInput = new PageFilterInput<>();
//        prInput.setPageSize(0);
//        prInput.setPageNumber(0);
//        PurchaseRequestDTO prFilter = new PurchaseRequestDTO();
//        prFilter.setStatus("O");
//        prInput.setFilter(prFilter);
//        materialOnOrderSummaryJob.clonePr(prInput);
//
//        PageFilterInput<GrpoDTO> grpoInput = new PageFilterInput<>();
//        grpoInput.setPageSize(0);
//        grpoInput.setPageNumber(0);
//        GrpoDTO grpoFilter = new GrpoDTO();
//        grpoFilter.setStatus("O");
//        grpoInput.setFilter(grpoFilter);
//        materialOnOrderSummaryJob.cloneGrpo(grpoInput);
//    }
}
