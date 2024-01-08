package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.PurchaseRecommendationEntity;
import com.facenet.mrp.domain.mrp.PurchaseRecommendationPurchasePlanEntity;
import com.facenet.mrp.service.dto.RecommendationPlanDto;
import com.facenet.mrp.service.dto.mrp.ItemQuantity;
import com.facenet.mrp.service.dto.mrp.ItemQuantityWithDate;
import com.facenet.mrp.service.dto.mrp.PlanPrForSo;
import com.facenet.mrp.service.dto.mrp.QuantityModelForReport;
import com.facenet.mrp.service.dto.sap.PurchaseRequestDetailApiDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public interface PurchaseRecommendationPlanRepository extends PagingAndSortingRepository<PurchaseRecommendationPurchasePlanEntity, Long> {
    @Modifying
    @Transactional
    @Query(value = "update purchase_recommendation_purchase_plan as p " +
        "join purchase_recommendation_detail as pd on p.purchase_recommendation_detail_id = pd.purchase_recommendation_detail_id " +
        "set p.status = :status where" +
        " pd.is_active = true " +
        "and p.is_active = true " +
        "and pd.purchase_recommendation_id = :purchaseRecommendation " +
//        "and (p.status is null or p.status in (1,2,4)) " +
        "and p.item_code in :items and p.batch = :batchNumber",nativeQuery = true)
    int approveRecommendationPlan(@Param("purchaseRecommendation") PurchaseRecommendationEntity purchaseRecommendation,
                              @Param("status") int status,
                              @Param("items") List<String> items,
                              @Param("batchNumber") Integer batchNumber);

    @Query("select new com.facenet.mrp.service.dto.sap.PurchaseRequestDetailApiDTO(p.itemCode, m.vendorCode, p.requiredQuantity, p.dueDate) from PurchaseRecommendationPurchasePlanEntity p " +
        "left join PurchaseRecommendationDetailEntity pd on p.purchaseRecommendationDetailId = pd.purchaseRecommendationDetailId " +
        "left join MqqPriceEntity m on m.itemPriceId = pd.moqPriceId " +
        "where p.isActive = true and pd.isActive = true and m.isActive = 1 " +
        "and p.status = :status " +
        "and pd.purchaseRecommendation = :purchaseRecommendation " +
        "and p.itemCode in :items and p.batch = :batchNumber")
    List<PurchaseRequestDetailApiDTO> getAllApprovedByItems(
        @Param("purchaseRecommendation") PurchaseRecommendationEntity purchaseRecommendation,
        @Param("status") int status,
        @Param("items") List<String> items,
        @Param("batchNumber") Integer batchNumber
    );

    @Query("select p from PurchaseRecommendationPurchasePlanEntity p where p.purchaseRecommendationDetailId = :detailId and p.batch = :batch and p.isActive = true")
    List<PurchaseRecommendationPurchasePlanEntity> findAllPlanBatch(@Param("detailId") Integer detailId,@Param("batch") Integer batch);

    @Query("select p from PurchaseRecommendationPurchasePlanEntity p where p.purchaseRecommendationDetailId = :detailId and p.isActive = true")
    List<PurchaseRecommendationPurchasePlanEntity> findAllPlan(@Param("detailId") Integer detailId);

    @Query("select new com.facenet.mrp.service.dto.RecommendationPlanDto(pd.purchaseRecommendation.mrpSubCode, pl.recommendationPurchasePlanId, pl.itemCode, pl.requiredQuantity, pl.dueDate, pl.note, pl.status, pl.batch) " +
        "from PurchaseRecommendationPurchasePlanEntity pl " +
        "join PurchaseRecommendationDetailEntity pd on pl.purchaseRecommendationDetailId = pd.purchaseRecommendationDetailId " +
        "where pd.purchaseRecommendation.mrpPoId = :soCode and pl.itemCode = :itemCode and pl.isActive = true " +
        "and ((pl.status in :status and pd.purchaseRecommendation.mrpSubCode <> :mrpSubCode) or (pd.purchaseRecommendation.mrpSubCode = :mrpSubCode )) "
    )
    List<RecommendationPlanDto> findOtherPlanByStatus(@Param("soCode") String soCode,
                                                      @Param("mrpSubCode") String mrpSubCode,
                                                      @Param("itemCode") String itemCode,
                                                      @Param("status") List<Integer> status);

    PurchaseRecommendationPurchasePlanEntity findByRecommendationPurchasePlanId(Integer id);

    @Query("select pl from PurchaseRecommendationPurchasePlanEntity pl " +
        "join PurchaseRecommendationDetailEntity pd on pl.purchaseRecommendationDetailId = pd.purchaseRecommendationDetailId " +
        "where pd.purchaseRecommendation.mrpSubCode = :mrpSubCode")
    List<PurchaseRecommendationPurchasePlanEntity> findAllByMrpSubCode(@Param("mrpSubCode") String mrpSubCode);

    List<PurchaseRecommendationPurchasePlanEntity> findAllByRecommendationPurchasePlanId(Integer id);

    @Modifying
    @Query("update PurchaseRecommendationPurchasePlanEntity p set p.batch = :batch , p.status = 2 where p.isActive = true and p.purchaseRecommendationDetailId = :purchaseRecommendationDetailId and p.batch is null and p.status = 1")
    int setBatchPurchasePlan(@Param("purchaseRecommendationDetailId") Integer purchaseRecommendationDetailId,
                              @Param("batch") Integer batch);

    @Query("select sum (p.requiredQuantity) from PurchaseRecommendationPurchasePlanEntity p join PurchaseRecommendationDetailEntity pd on p.purchaseRecommendationDetailId = pd.purchaseRecommendationDetailId " +
        "where p.isActive = true and p.itemCode = :itemCode and pd.purchaseRecommendation.purchaseRecommendationId = :purchaseRecommendationId and p.batch = :batch")
    Double sumQuantity(@Param("itemCode") String itemCode,
                        @Param("purchaseRecommendationId") Integer purchaseRecommendationId,
                        @Param("batch") Integer batch
    );

    @Query("select new com.facenet.mrp.service.dto.mrp.ItemQuantity(p.itemCode, sum (p.requiredQuantity)) " +
        "from PurchaseRecommendationPurchasePlanEntity p " +
        "join PurchaseRecommendationDetailEntity pd on p.purchaseRecommendationDetailId = pd.purchaseRecommendationDetailId " +
        "where p.isActive = true and p.itemCode in :itemList " +
        "and pd.purchaseRecommendation.purchaseRecommendationId = :purchaseRecommendationId " +
        "and (:batch is null or p.batch = :batch) " +
        "group by p.itemCode")
    List<ItemQuantity> sumQuantityOfItems(@Param("itemList") List<String> itemList,
                                          @Param("purchaseRecommendationId") Integer purchaseRecommendationId,
                                          @Param("batch") Integer batch
    );

    @Query("select new com.facenet.mrp.service.dto.mrp.ItemQuantity(p.itemCode, sum (p.requiredQuantity)) " +
        "from PurchaseRecommendationPurchasePlanEntity p " +
        "join PurchaseRecommendationDetailEntity pd on p.purchaseRecommendationDetailId = pd.purchaseRecommendationDetailId " +
        "join PurchaseHasRecommendationEntity pb on pd.purchaseRecommendationId = pb.purchaseRecommendationId " +
        "where p.isActive = true and p.itemCode in :itemList " +
        "and pb.docDate >= :startTime and pb.dueDate <= :endTime " +
        "group by p.itemCode")
    List<ItemQuantity> sumAllQuantityOfItems(@Param("itemList") List<String> itemList,
                                             @Param("startTime") Date startTime,
                                             @Param("endTime") Date endTime);

    @Query("select new com.facenet.mrp.service.dto.mrp.ItemQuantityWithDate(DATE_FORMAT(p.dueDate , '%Y-%m-%d'), p.itemCode, sum (p.requiredQuantity)) " +
        "from PurchaseRecommendationPurchasePlanEntity p " +
        "join PurchaseRecommendationDetailEntity pd on p.purchaseRecommendationDetailId = pd.purchaseRecommendationDetailId " +
        "join PurchaseRecommendationEntity pr on pd.purchaseRecommendationId = pr.purchaseRecommendationId " +
        "where p.isActive = true and p.itemCode in :itemList " +
//        "and pr.mrpPoId = :soCode " +
        "and p.status = 3 " +
        "and p.dueDate >= :startTime and p.dueDate <= :endTime " +
        "group by p.itemCode, DATE_FORMAT(p.dueDate, '%Y-%m-%d') " +
        "order by DATE_FORMAT(p.dueDate , '%Y-%m-%d')")
    List<ItemQuantityWithDate> sumAllQuantityOfItemsByDay(@Param("itemList") List<String> itemList,
//                                                          @Param("soCode") String soCode,
                                                          @Param("startTime") Date startTime,
                                                          @Param("endTime") Date endTime);

    @Query("select new com.facenet.mrp.service.dto.mrp.ItemQuantityWithDate(DATE_FORMAT(p.dueDate , '%Y-%m-%d'), p.itemCode, sum (p.requiredQuantity)) " +
        "from PurchaseRecommendationPurchasePlanEntity p " +
        "join PurchaseRecommendationDetailEntity pd on p.purchaseRecommendationDetailId = pd.purchaseRecommendationDetailId " +
        "join PurchaseRecommendationEntity pr on pd.purchaseRecommendationId = pr.purchaseRecommendationId " +
        "where p.isActive = true " +
        "and p.status = 3 " +
        "and p.dueDate >= :startTime and p.dueDate <= :endTime " +
        "group by p.itemCode, DATE_FORMAT(p.dueDate, '%Y-%m-%d') ")
    List<ItemQuantityWithDate> sumAllQuantityOfItemsByDay(@Param("startTime") Date startTime,
                                                          @Param("endTime") Date endTime);

    default Map<String, List<ItemQuantityWithDate>> sumAllQuantityOfItemsByDayMap(List<String> items, String soCode, Date startTime, Date endTime) {
        return sumAllQuantityOfItemsByDay(
            items,
//            soCode,
            startTime, endTime).stream().collect(
            Collectors.groupingBy(
                ItemQuantityWithDate::getItemCode
            )
        );
    }


    default Map<String, List<ItemQuantityWithDate>> sumAllQuantityOfItemsByDayMap(Date startTime, Date endTime) {
        return sumAllQuantityOfItemsByDay(
            startTime,
            endTime).stream().collect(
            Collectors.groupingBy(
                ItemQuantityWithDate::getItemCode
            )
        );
    }
    @Query("select sum(pr.requiredQuantity) from PurchaseRecommendationPurchasePlanEntity pr join PurchaseRecommendationDetailEntity p on pr.purchaseRecommendationDetailId = p.purchaseRecommendationDetailId " +
        "join PurchaseRecommendationEntity pe on p.purchaseRecommendationId = pe.purchaseRecommendationId where pr.isActive = true and pr.itemCode = :itemCode and (pe.mrpSubCode is null or pe.mrpSubCode = :mrpSubCode) and pe.mrpPoId = :mrpPoId and pr.status = 3")
    Double sumItemInPlan(
        @Param("itemCode") String itemCode,
        @Param("mrpSubCode") String mrpSubCode,
        @Param("mrpPoId") String mrpPoId
    );

    @Query("select sum (p.requiredQuantity) from PurchaseRecommendationPurchasePlanEntity p join PurchaseRecommendationDetailEntity pd on p.purchaseRecommendationDetailId = pd.purchaseRecommendationDetailId " +
        "where p.isActive = true and p.itemCode = :itemCode and pd.purchaseRecommendation.purchaseRecommendationId = :purchaseRecommendationId and p.status = 3")
    Double sumPrQuantity(@Param("itemCode") String itemCode,
                       @Param("purchaseRecommendationId") Integer purchaseRecommendationId
    );

    @Query("select pd.quantity from PurchaseRecommendationDetailEntity pd where pd.isActive = true and pd.itemCode = :itemCode and pd.purchaseRecommendationId = :purchaseRecommendationId")
    Double sumRequestQuantity(@Param("itemCode") String itemCode,
                         @Param("purchaseRecommendationId") Integer purchaseRecommendationId
    );

    @Query("select p from PurchaseRecommendationPurchasePlanEntity p join PurchaseRecommendationDetailEntity pd on p.purchaseRecommendationDetailId = pd.purchaseRecommendationDetailId " +
        "join PurchaseRecommendationEntity pe on pd.purchaseRecommendationId = pe.purchaseRecommendationId where p.isActive = true and pe.isActive = true and pd.isActive = true " +
        "and p.itemCode = :itemCode and pe.mrpSubCode = :mrpSubCode and pe.mrpPoId = :soCode")
    List<PurchaseRecommendationPurchasePlanEntity> getAllPlan(
        @Param("itemCode") String itemCode,
        @Param("mrpSubCode") String mrpSubCode,
        @Param("soCode") String soCode
    );

    @Query("select new com.facenet.mrp.service.dto.mrp.QuantityModelForReport(d.requiredQuantity, sum(pl.requiredQuantity)) from PurchaseRecommendationEntity p join PurchaseRecommendationDetailEntity d " +
        "on p.purchaseRecommendationId = d.purchaseRecommendationId " +
        "join PurchaseRecommendationPurchasePlanEntity pl " +
        "on d.purchaseRecommendationDetailId = pl.purchaseRecommendationDetailId " +
        "where p.mrpSubCode = :mrpSupCode " +
        "and p.mrpPoId = :soCode " +
        "and d.itemCode = :poCode " +
        "and pl.dueDate <= :endDate " +
        "and pl.dueDate >= :startDate " +
        "and pl.status = 3 " +
        "and p.isActive = true " +
        "and d.isActive = true " +
        "and pl.isActive = true " +
        "group by pl.purchaseRecommendationDetailId")
    QuantityModelForReport getAllQuantityForReport(@Param("mrpSupCode")String mrpSupCode,
                                                   @Param("soCode")String soCode,
                                                   @Param("poCode")String poCode,
                                                   @Param("endDate")Date endDate,
                                                   @Param("startDate")Date startDate);

    @Query("select new com.facenet.mrp.service.dto.mrp.PlanPrForSo(p.mrpSubCode, pl.itemCode, sum(pl.requiredQuantity)) from PurchaseRecommendationEntity p join PurchaseRecommendationDetailEntity d " +
        "on p.purchaseRecommendationId = d.purchaseRecommendationId " +
        "join PurchaseRecommendationPurchasePlanEntity pl " +
        "on d.purchaseRecommendationDetailId = pl.purchaseRecommendationDetailId " +
        "where p.mrpPoId = :soCode " +
        "and pl.status = 3 " +
        "and p.isActive = true " +
        "and d.isActive = true " +
        "and pl.isActive = true " +
        "group by pl.purchaseRecommendationDetailId")
    List<PlanPrForSo> getTotalExpectedQuantityForDetailReport(@Param("soCode")String soCode);

    @Query("select p.itemCode " +
        "from PurchaseRecommendationPurchasePlanEntity p " +
        "join PurchaseRecommendationDetailEntity pd on pd.purchaseRecommendationDetailId = p.purchaseRecommendationDetailId " +
        "where p.isActive = true " +
        "and p.status = 1 " +
        "and pd.purchaseRecommendationId = :purchaseRecommendationId " +
        "and p.itemCode in :itemCode " +
        "group by p.itemCode")
    List<String> countByStatus(@Param("purchaseRecommendationId") Integer purchaseRecommendationId, @Param("itemCode") List<String> itemCode);

    @Query("select distinct p.itemCode " +
        "from PurchaseRecommendationPurchasePlanEntity p " +
        "where p.isActive = true " +
        "and p.status = :status " +
        "and p.purchaseRecommendationDetailId in :purchaseRecommendationDetailIdList ")
    Set<String> checkByStatus(
        @Param("purchaseRecommendationDetailIdList") List<Integer> purchaseRecommendationDetailIdList,
        @Param("status") Integer status
    );

}
