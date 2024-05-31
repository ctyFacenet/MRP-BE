package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.MrpProductionQuantityEntity;
import com.facenet.mrp.domain.mrp.PurchaseRecommendationDetailEntity;
import com.facenet.mrp.domain.mrp.PurchaseRecommendationEntity;
import com.facenet.mrp.producer_kafka.InfoItemDTO;
import com.facenet.mrp.producer_kafka.ItemContextAlert;
import com.facenet.mrp.service.dto.ItemPriceOfVendorDTO;
import com.facenet.mrp.service.dto.mrp.CountPo;
import com.facenet.mrp.service.dto.mrp.VendorCodeForDetailReport;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Repository
public interface PurchaseRecommendationDetailRepository extends JpaRepository<PurchaseRecommendationDetailEntity, Integer> {

    @Query("select p from PurchaseRecommendationDetailEntity p where p.purchaseRecommendation.purchaseRecommendationId = :purchaseRecommendationEntity and p.itemCode = :itemCode and p.isActive = true ")
    PurchaseRecommendationDetailEntity findByPRIdAndItemCode(@Param("purchaseRecommendationEntity") Integer purchaseRecommendationEntity, @Param("itemCode") String itemCode);

    @Query("select mq from PurchaseRecommendationDetailEntity mq where mq.isActive = true and mq.itemCode = :itemCode and mq.purchaseRecommendation.purchaseRecommendationId = :purchaseRecommendationId")
    PurchaseRecommendationDetailEntity getSyntheticByItemCode(@Param("itemCode") String itemCode,@Param("purchaseRecommendationId") Integer purchaseRecommendationId);

    List<PurchaseRecommendationDetailEntity> getAllByPurchaseRecommendationAndIsActiveTrue(PurchaseRecommendationEntity purchaseRecommendationEntity);

    @Modifying
    @Query("update PurchaseRecommendationDetailEntity p set p.quantity = :quantity, p.receiveDate = :receiveDate where p.isActive = true and p.purchaseRecommendation = :purchaseRecommendation and p.itemCode = :itemCode")
    int updatePurchaseRecommendation(@Param("purchaseRecommendation") PurchaseRecommendationEntity purchaseRecommendation,
                                     @Param("itemCode") String itemCode,
                                     @Param("quantity") Double quantity,
                                     @Param("receiveDate")Date receiveDate);

    @Query("select p from PurchaseRecommendationDetailEntity p where p.isActive = true and p.purchaseRecommendation = :purchaseRecommendationEntity and p.itemCode in :items")
    List<PurchaseRecommendationDetailEntity> getAllByInItemList(@Param("purchaseRecommendationEntity") PurchaseRecommendationEntity purchaseRecommendationEntity, @Param("items") Set<String> items);

//    @Query("select new com.facenet.mrp.service.dto.ItemPriceOfVendorDTO(mqq.itemPriceId, mqq.vendorCode, l.leadTime, mqq.rangeStart, mqq.rangeEnd, mqq.price, mqq.timeStart, mqq.currency, mqq.isPromotion) " +
//        "from MqqPriceEntity mqq " +
//        "left join LeadTimeEntity l on mqq.itemCode = l.itemCode and mqq.vendorCode = l.vendorCode " +
//        "where mqq.itemCode = :itemCode " +
//        "and l.isActive = 1 and mqq.isActive = 1 " +
//        "and (mqq.timeStart is null or mqq.timeStart <= current_date) " +
//        "and (current_date <= mqq.timeEnd or mqq.timeEnd is null)")
    @Query("select new com.facenet.mrp.service.dto.ItemPriceOfVendorDTO(mqq.itemPriceId, mqq.vendorCode, v.vendorName, l.leadTime, mqq.rangeStart, mqq.rangeEnd, mqq.price, mqq.timeStart, mqq.currency, mqq.isPromotion) " +
    "from MqqPriceEntity mqq " +
    "left join LeadTimeEntity l on mqq.itemCode = l.itemCode and mqq.vendorCode = l.vendorCode " +
        "left join VendorEntity v on v.vendorCode = mqq.vendorCode " +
        "left join VendorItemEntity vi on  mqq.itemCode = vi.itemCode and mqq.vendorCode = vi.vendorCode " +
    "where mqq.itemCode = :itemCode " +
    "and l.isActive = 1 and mqq.isActive = 1 " +
        "order by vi.timeUsed desc, mqq.timeStart desc")
    List<ItemPriceOfVendorDTO> getAllItemPrice(@Param("itemCode") String itemCode);

    @Modifying
    @Query("update PurchaseRecommendationDetailEntity p set p.status = :newStatus " +
        "where p.status in :oldStatus " +
        "and p.purchaseRecommendation.purchaseRecommendationId = :purchaseRecommendationId " +
        "and p.itemCode in :items "+
        "and p.isActive = true"
    )
    int changeStatus(@Param("purchaseRecommendationId") Integer purchaseRecommendationId,
                     @Param("items") List<String> items,
                     @Param("newStatus") int newStatus,
                     @Param("oldStatus") List<Integer> oldStatus);

    @Modifying
    @Query("update PurchaseRecommendationDetailEntity p set p.status = :newStatus " +
        "where " +
        "p.purchaseRecommendation.purchaseRecommendationId = :purchaseRecommendationId " +
        "and p.itemCode in :items "+
        "and p.isActive = true "
    )
    int changeStatusRecommendation(@Param("purchaseRecommendationId") Integer purchaseRecommendationId,
                     @Param("items") List<String> items,
                     @Param("newStatus") int newStatus);

    @Modifying
    @Query("update PurchaseRecommendationDetailEntity p set p.note = :note, p.status = :status where p.isActive = true and p.purchaseRecommendation = :purchaseRecommendation and (p.status = 2 or p.status = 4) and p.itemCode in :items ")
    int approveRecommendation(@Param("purchaseRecommendation") PurchaseRecommendationEntity purchaseRecommendation,
                              @Param("status") int status,
                              @Param("items") List<String> items,
                              @Param("note") String note);

    int countByPurchaseRecommendationAndStatusInAndIsActiveTrue(PurchaseRecommendationEntity entity, List<Integer> status);

    @Query("select count(p) from PurchaseRecommendationDetailEntity p where p.isActive = true and p.status in (1,2,3,4) and p.purchaseRecommendationId = :purchaseRecommendationId")
    int countPurchaseRecommendationNotClose(@Param("purchaseRecommendationId") Integer purchaseRecommendationId);

    @Modifying
    @Query("update PurchaseRecommendationDetailEntity p set p.status = :newStatus " +
        "where p.purchaseRecommendation = :purchaseRecommendationId " +
        "and p.status = :oldStatus and p.isActive = true ")
    int changeStatus(@Param("purchaseRecommendationId") PurchaseRecommendationEntity purchaseRecommendationId, @Param("newStatus") int newStatus, @Param("oldStatus") int oldStatus);

    @Query("select pd from PurchaseRecommendationEntity p, PurchaseRecommendationDetailEntity pd" +
        " where p.purchaseRecommendationId = pd.purchaseRecommendation.purchaseRecommendationId" +
        " and p.mrpPoId = :soCode" +
        " and (p.mrpSubCode is null or p.mrpSubCode = :mrpSubCode)" +
        " and pd.itemCode = :itemCode" +
        " and p.isActive = true and  pd.isActive = true")
    List<PurchaseRecommendationDetailEntity> getAllPurchaseRecommendationDetailEntity (
        @Param("soCode") String soCode,
        @Param("mrpSubCode") String mrpSubCode,
        @Param("itemCode") String itemCode
    );

    @Query("select pd from PurchaseRecommendationDetailEntity pd" +
        " where pd.purchaseRecommendation.purchaseRecommendationId = :puchaseRecommendatioId"+
        " and pd.isActive = true"+
        " and pd.itemCode in :itemList "
    )
    List<PurchaseRecommendationDetailEntity> getListPurchaseRecommendationDetail (
        @Param("puchaseRecommendatioId") Integer puchaseRecommendatioId,
        @Param("itemList") List<String> itemList
    );

    @Query("select p from PurchaseRecommendationDetailEntity p join PurchaseRecommendationEntity pd on p.purchaseRecommendationId = pd.purchaseRecommendationId where p.isActive = true and pd.isActive = true and p.itemCode = :itemCode and pd.mrpPoId = :soCode and pd.mrpSubCode = :mrpSubCode")
    Double getRequiredQuantity(@Param("itemCode") String itemCode,@Param("soCode") String soCode,@Param("mrpSubCode") String mrpSubCode);

    @Query("select new com.facenet.mrp.service.dto.mrp.CountPo(p.mrpPoId ,count(distinct d.itemCode)) from PurchaseRecommendationEntity p , PurchaseRecommendationDetailEntity d, PurchaseRecommendationPurchasePlanEntity pl " +
        "where p.purchaseRecommendationId = d.purchaseRecommendationId " +
        "and d.purchaseRecommendationDetailId = pl.purchaseRecommendationDetailId " +
        "and p.isActive = true " +
        "and d.isActive = true " +
        "and pl.isActive = true " +
        "and pl.status = 3 " +
        "and p.mrpPoId in :soCodeList " +
        "group by p.mrpPoId")
    List<CountPo> countPrBySoCode (@Param("soCodeList")List<String> soCodeList);

    @Query("select new com.facenet.mrp.service.dto.mrp.VendorCodeForDetailReport(p.mrpSubCode, d.itemCode, m.vendorCode) " +
        "from PurchaseRecommendationEntity p, PurchaseRecommendationDetailEntity d, MqqPriceEntity m " +
        "where p.purchaseRecommendationId = d.purchaseRecommendationId " +
        "and d.moqPriceId = m.itemPriceId " +
        "and d.moqPriceId is not null " +
        "and m.vendorCode = :vendorCode " +
        "and p.mrpPoId = :soCode " +
        "and p.isActive = true " +
        "and d.isActive = true " +
        "and m.isActive = 1 ")
    List<VendorCodeForDetailReport> getVendorBySoCodeForDetailReport(
        @Param("soCode")String soCode,
        @Param("vendorCode")String vendorCode
        );

    @Query("select new com.facenet.mrp.producer_kafka.InfoItemDTO(b.mrpPoId,b.mrpSubCode,a.itemCode,a.itemDescription,b.startTime,b.endTime) from PurchaseRecommendationDetailEntity a join PurchaseRecommendationEntity b on a.purchaseRecommendationId = b.purchaseRecommendationId where a.isActive = true and b.isActive = true and b.endTime > :date")
    List<InfoItemDTO> getAllItemOnTime(@Param("date") Date date);

    @Query("select a from PurchaseRecommendationEntity a where a.isActive = true and a.mrpSubCode = :mrpSubCode")
    PurchaseRecommendationEntity getPurchaseRecommendation(@Param("mrpSubCode") String mrpSubCode);

    @Query(value = "select new com.facenet.mrp.producer_kafka.InfoItemDTO(p.soCode,p.mrpSubCode,p.itemCode,p.itemName,p.dueDate,max(p.dueDate)) from MrpProductionQuantityEntity as p where p.isActive = true and p.dueDate > :date group by p.itemCode")
    List<InfoItemDTO> getAllOnTime(@Param("date") Date date);

    @Query(value = "select sum(pd.quantity) from PurchaseRecommendationDetailEntity pd join PurchaseRecommendationEntity p on p.purchaseRecommendationId = pd.purchaseRecommendationId where pd.isActive = true and p.isActive= true and p.mrpPoId = :soCode")
    Double sumQuantitySo(@Param("soCode") String soCode);

    @Query("select p from PurchaseRecommendationDetailEntity p " +
        "left join fetch p.moqPriceEntity m " +
        "left join fetch m.vendorItemEntity v " +
        "where p.isActive = true " +
        "and p.purchaseRecommendation = :purchaseRecommendation " +
        "and p.status in :status and p.itemCode in :items")
    List<PurchaseRecommendationDetailEntity> getAllWaitingForApprove(@Param("purchaseRecommendation") PurchaseRecommendationEntity purchaseRecommendationEntity,
                                                                     @Param("status") List<Integer> status,
                                                                     @Param("items") List<String> items);
}
