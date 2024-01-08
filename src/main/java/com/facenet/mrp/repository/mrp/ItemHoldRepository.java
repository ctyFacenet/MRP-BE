package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.ItemHoldEntity;
import com.facenet.mrp.service.dto.ItemHoldDTO;
import com.facenet.mrp.service.dto.mrp.ItemQuantityWithDate;
import com.facenet.mrp.service.utils.Constants;
import com.facenet.mrp.service.utils.Utils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ItemHoldRepository extends JpaRepository<ItemHoldEntity, Integer> {
    List<ItemHoldEntity> findByPurchaseRecommendationId(Integer purchaseRecommendationId);
    @Query("select sum (i.quantity) from ItemHoldEntity i where i.isActive = true and i.status = 2 and i.itemCode = :itemCode and (i.mrpSubCode is null or i.mrpSubCode = :mrpSubCode) and i.productOrderCode = :productOrderCode")
    Double sumItemHold(
        @Param("itemCode") String itemCode,
        @Param("mrpSubCode") String mrpSubCode,
        @Param("productOrderCode") String productOrderCode
    );

    @Query("select new com.facenet.mrp.service.dto.mrp.ItemQuantityWithDate(i.holdDate, i.itemCode, sum(i.quantity)) " +
        "from ItemHoldEntity i " +
//        "join MrpSubEntity m on i.mrpSubCode = m.mrpSubCode " +
        "where i.itemCode in (:items) and i.warehouseCode in (:warehouses) " +
        "and :startTime <= i.holdDate and i.holdDate <= :endTime " +
        "and i.isActive = true " +
//        "and upper(m.analysisType) = upper(:analysisPeriod) " +
        "and i.status = :status " +
        "group by i.itemCode, i.holdDate " +
        "order by i.holdDate")
    List<ItemQuantityWithDate> getAllHoldQuantityItem(@Param("items") List<String> items,
                                                      @Param("warehouses") List<String> warehouses,
                                                      @Param("startTime") Date startTime,
                                                      @Param("endTime") Date endTime,
//                                                      @Param("analysisPeriod") String analysisPeriod,
                                                      @Param("status") int status
    );

    @Query("select new com.facenet.mrp.service.dto.mrp.ItemQuantityWithDate(i.holdDate, i.itemCode, sum(i.quantity)) " +
        "from ItemHoldEntity i " +
//        "join MrpSubEntity m on i.mrpSubCode = m.mrpSubCode " +
        "where i.warehouseCode in (:warehouses) " +
        "and :startTime <= i.holdDate and i.holdDate <= :endTime " +
        "and i.isActive = true " +
//        "and upper(m.analysisType) = upper(:analysisPeriod) " +
        "and i.status = :status " +
        "group by i.itemCode, i.holdDate ")
    List<ItemQuantityWithDate> getAllHoldQuantityItem(@Param("warehouses") List<String> warehouses,
                                                      @Param("startTime") Date startTime,
                                                      @Param("endTime") Date endTime,
                                                      @Param("status") int status);

    default Map<String, List<ItemQuantityWithDate>> getAllHoldQuantityItemMapByWhs(List<String> items,List<String> warehouses, Date startTime, Date endTime, String analysisPeriod) {
        return getAllHoldQuantityItem(items,
            warehouses,
            Utils.toUTC(startTime),
            Utils.toUTC(endTime),
//            analysisPeriod,
            Constants.ItemHold.ACTIVE
        ).stream().collect(Collectors.groupingBy(
            ItemQuantityWithDate::getItemCode
        ));
    }

    default Map<String, List<ItemQuantityWithDate>> getAllHoldQuantityItemMapByWhs(List<String> warehouses, Date startTime, Date endTime, String analysisPeriod) {
        return getAllHoldQuantityItem(
            warehouses,
            Utils.toUTC(startTime),
            Utils.toUTC(endTime),
//            analysisPeriod,
            Constants.ItemHold.ACTIVE
        ).stream().collect(Collectors.groupingBy(
            ItemQuantityWithDate::getItemCode
        ));
    }

    @Query("select new com.facenet.mrp.service.dto.mrp.ItemQuantityWithDate(i.holdDate, i.itemCode, sum(i.quantity)) " +
        "from ItemHoldEntity i " +
//        "join MrpSubEntity m on i.mrpSubCode = m.mrpSubCode " +
        "where i.itemCode in (:items) and i.warehouseCode is null " +
        "and :startTime <= i.holdDate and i.holdDate <= :endTime " +
        "and i.isActive = true " +
//        "and upper(m.analysisType) = upper(:analysisPeriod) " +
        "and i.status = :status " +
        "group by i.itemCode, i.holdDate " +
        "order by i.holdDate")
    List<ItemQuantityWithDate> getAllHoldNeedQuantityItem(@Param("items") List<String> items,
                                                      @Param("startTime") Date startTime,
                                                      @Param("endTime") Date endTime,
//                                                      @Param("analysisPeriod") String analysisPeriod,
                                                      @Param("status") int status
    );
    default Map<String, List<ItemQuantityWithDate>> getAllHoldQuantityItemMap(List<String> items,List<String> warehouses, Date startTime, Date endTime, String analysisPeriod) {
        return getAllHoldNeedQuantityItem(
            items,
//startTime,endTime,
            Utils.toUTC(startTime),
            Utils.toUTC(endTime),
//            analysisPeriod,
            Constants.ItemHold.ACTIVE
        ).stream().collect(Collectors.groupingBy(
            ItemQuantityWithDate::getItemCode
        ));
    }

    @Query("select new com.facenet.mrp.service.dto.mrp.ItemQuantityWithDate(i.holdDate, i.itemCode, sum(i.quantity)) " +
        "from ItemHoldEntity i " +
//        "join MrpSubEntity m on i.mrpSubCode = m.mrpSubCode " +
        "where i.warehouseCode is null " +
        "and :startTime <= i.holdDate and i.holdDate <= :endTime " +
        "and i.isActive = true " +
        "and i.status = :status " +
        "group by i.itemCode, i.holdDate " +
        "order by i.holdDate")
    List<ItemQuantityWithDate> getAllHoldNeedQuantityItem(
                                                          @Param("startTime") Date startTime,
                                                          @Param("endTime") Date endTime,
                                                          @Param("status") int status
    );
    default Map<String, List<ItemQuantityWithDate>> getAllHoldQuantityItemMap(List<String> warehouses, Date startTime, Date endTime) {
        return getAllHoldNeedQuantityItem(
            //startTime,endTime,
            Utils.toUTC(startTime),
            Utils.toUTC(endTime),
            Constants.ItemHold.ACTIVE
        ).stream().collect(Collectors.groupingBy(
            ItemQuantityWithDate::getItemCode
        ));
    }

    @Query("select i from ItemHoldEntity i " +
        "where i.isActive = true " +
        "and i.itemCode in :items " +
        "and i.purchaseRecommendationId = :purchaseRecommendationId")
    List<ItemHoldEntity> getAllSourceHoldingItem(@Param("purchaseRecommendationId") Integer purchaseRecommendationId, @Param("items") Collection<String> items);

    @Query("select i from ItemHoldEntity i " +
        "where i.isActive = true " +
        "and i.itemCode in :items " +
        "and i.productOrderCode = :productOrderCode " +
        "and i.status = 2 and i.warehouseCode is null ")
    List<ItemHoldEntity> getAllHoldingItemForUpdate(@Param("productOrderCode") String productOrderCode, @Param("items") Collection<String> items);

    @Transactional
    @Modifying
    @Query("update ItemHoldEntity i set i.isActive = false where i.mrpSubCode = :mrpSubCode")
    int unHoldItemsOf(@Param("mrpSubCode") String mrpSubCode);

    @Transactional
    @Modifying
    @Query("update ItemHoldEntity i set i.isActive = false where i.mrpSubCode in :mrpSubCode")
    int unHoldItemsOf(@Param("mrpSubCode") List<String> mrpSubCode);

    @Transactional
    @Modifying
    @Query("update ItemHoldEntity i set i.isActive = false where i.purchaseRecommendationId = :purchaseRecommendationId")
    int unHoldItemsOfPurchaseRecommendation(@Param("purchaseRecommendationId") Integer purchaseRecommendationId);

    @Transactional
    @Modifying
    @Query("update ItemHoldEntity i set i.isActive = false where i.productOrderCode = :soCode")
    int unHoldItemsOfSO(@Param("soCode") String soCode);

    @Transactional
    @Modifying
    @Query("update ItemHoldEntity i set i.isActive = false where i.mrpSubCode = :mrpSubCode and i.itemCode in :listItems")
    int unHoldItemsOfBatch(@Param("mrpSubCode") String mrpSubCode,@Param("listItems") List<String> listItems);

    @Transactional
    @Modifying
    @Query("update ItemHoldEntity i set i.isActive = false where i.itemCode = :itemCode and i.mrpSubCode = :mrpSubCode")
    int unHoldItemCodeAndMrpSubCode(@Param("itemCode") String itemCode, @Param("mrpSubCode") String mrpSubCode);

    @Transactional
    @Modifying
    @Query("update ItemHoldEntity i set i.isActive = false where i.itemCode = :itemCode and i.purchaseRecommendationId = :purchaseRecommendationId and i.quantity < 0")
    int unHoldPROfItemCodeAndMrpSubCode(@Param("itemCode") String itemCode, @Param("purchaseRecommendationId") Integer purchaseRecommendationId);

    @Query("select new com.facenet.mrp.service.dto.ItemHoldDTO(i.mrpSubCode, i.productOrderCode, i.quantity, i.warehouseCode, i.holdDate) " +
        "from ItemHoldEntity i " +
//        "left join PurchaseHasRecommendationEntity p on p.purchaseRecommendationId = i.purchaseRecommendationId " +
        "where i.itemCode = :itemCode and i.status = 2 " +
//        "and i.warehouseCode is null " +
        "and i.isActive = true " +
        "order by i.holdDate asc"
//        "and (p.isActive is null or p.isActive = true) "
        )
    List<ItemHoldDTO> getAllByItemCode(@Param("itemCode") String itemCode);

    @Query("select new com.facenet.mrp.service.dto.ItemHoldDTO(i.mrpSubCode, i.productOrderCode, i.quantity, i.warehouseCode, i.holdDate) " +
        "from ItemHoldEntity i " +
//        "left join PurchaseHasRecommendationEntity p on p.purchaseRecommendationId = i.purchaseRecommendationId " +
//        "left join MrpSubEntity m on m.mrpSubCode = i.mrpSubCode " +
        "where i.itemCode = :itemCode and i.status = 2 " +
//        "and i.warehouseCode is null " +
//        "and upper(m.analysisType) = upper(:analysisPeriod) " +
        "and :timeStart <= i.holdDate and i.holdDate <= :timeEnd " +
        "and i.isActive = true " +
        "order by i.holdDate asc"
//        "and (p.isActive is null or p.isActive = true) "
    )
    List<ItemHoldDTO> getAllByItemCode(@Param("itemCode") String itemCode,
                                       @Param("timeStart") Date timeStart,
                                       @Param("timeEnd") Date timeEnd
//                                       @Param("analysisPeriod")String analysisPeriod
    );
}
