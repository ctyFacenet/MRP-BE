package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.MqqPriceEntity;
import com.facenet.mrp.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public interface MqqPriceRepository extends PagingAndSortingRepository<MqqPriceEntity, Integer>, MqqPriceCustomRepository {

    @Query("select new com.facenet.mrp.service.dto.MinMqqPriceLeadTimeDTO(lt.leadTime, min(mq.price), mq.rangeStart, mq.rangeEnd, mq.timeEnd, mq.currency) from LeadTimeEntity lt, MqqPriceEntity mq " +
        "where lt.vendorCode = mq.vendorCode " +
        "and lt.itemCode = mq.itemCode " +
        "and mq.vendorCode = :vendorCode " +
        "and mq.itemCode = :itemCode " +
        "and mq.isPromotion = false " +
        "and lt.isActive = 1 and mq.isActive = 1 ")
    MinMqqPriceLeadTimeDTO findMqqAndLeadTimeWithMinPrice(@Param("vendorCode")String vendorCode, @Param("itemCode")String itemCode);

    @Query("select distinct new com.facenet.mrp.service.dto.PromotionDTO(p.timeStart, p.timeEnd, p.note) " +
        "from MqqPriceEntity p " +
        "where p.vendorCode = :vendorCode " +
        "and p.itemCode = :itemCode " +
        "and p.isPromotion = true " +
        "and p.isActive = 1 " +
        "and p.timeEnd >= current_date " +
        "and p.timeStart <= current_date " +
        "order by p.createdAt desc ")
    List<PromotionDTO> getAllTimeInPromotion(
        @Param("vendorCode")String vendorCode,
        @Param("itemCode")String itemCode);

    @Query("select m.itemCode from MqqPriceEntity m " +
        "where m.itemCode in :items " +
        "and m.isPromotion = true " +
        "and m.isActive = 1 " +
        "and current_date <= m.timeStart and m.timeEnd <= current_date ")
    Set<String> getInPromotionItemsOfList(@Param("items") List<String> items);

    @Query("select distinct new com.facenet.mrp.service.dto.PriceListDTO(p.rangeStart, p.rangeEnd, p.price, p.currency) " +
        "from MqqPriceEntity p " +
        "where p.vendorCode = :vendorCode " +
        "and p.itemCode = :itemCode " +
        "and p.isActive = 1 " +
        "and p.isPromotion = true " +
        "and p.timeEnd >= current_date " +
        "and p.timeStart = :timeStart " +
        "and p.timeEnd = :timeEnd " +
        "order by p.createdAt asc ")
    List<PriceListDTO> getAllPriceList (
        @Param("vendorCode")String vendorCode,
        @Param("itemCode")String itemCode,
        @Param("timeStart") Date timeStart,
        @Param("timeEnd")Date timeEnd);

    MqqPriceEntity findByItemPriceId(Integer id);

//    @Query("select new com.facenet.mrp.service.dto.MoqDTO(mq.itemPriceId,mq.vendorCode,mq.itemCode, min(mq.price),lt.leadTime, mq.currency) from LeadTimeEntity lt join MqqPriceEntity mq on lt.itemCode = mq.itemCode " +
//        "where mq.itemCode = :itemCode and lt.isActive = 1 and mq.isActive = 1 ")
//    MoqDTO findMoqMin(@Param("itemCode")String itemCode);

    @Query("select new com.facenet.mrp.service.dto.MoqDTO(mq.itemPriceId,mq.vendorCode,mq.itemCode, mq.timeStart, mq.rangeStart, mq.rangeEnd,mq.price,lt.leadTime, mq.currency, mq.vendorItemEntity.timeUsed) " +
        "from LeadTimeEntity lt join MqqPriceEntity mq on lt.itemCode = mq.itemCode and lt.vendorCode = mq.vendorCode " +
        "where lt.isActive = 1 and mq.isActive = 1 " +
        "and (mq.timeEnd >= current_date or mq.timeEnd is null) " +
        "and (mq.timeStart is null or mq.timeStart <= current_date)" )
//        "and (mq.timeStart = (SELECT MAX(timeStart) FROM MqqPriceEntity WHERE timeStart <= CURDATE() and vendorCode = mq.vendorCode and itemCode = mq.itemCode))")
    List<MoqDTO> findMoqMinAndLeadTime();

    @Query("select new com.facenet.mrp.service.dto.MoqDTO(mq.itemPriceId,mq.vendorCode,mq.itemCode, mq.timeStart, mq.rangeStart, mq.rangeEnd, mq.price,lt.leadTime, mq.currency, v.timeUsed) " +
        "from MqqPriceEntity mq " +
        "left join LeadTimeEntity lt on lt.itemCode = mq.itemCode and mq.vendorCode = lt.vendorCode " +
        "left join VendorItemEntity v on v.itemCode = mq.itemCode and mq.vendorCode = v.vendorCode " +
        "where lt.isActive = 1 and mq.isActive = 1 " +
        "and mq.itemCode in :listItem " +
        "and (mq.timeEnd >= current_date or mq.timeEnd is null) " +
        "and (mq.timeStart is null or mq.timeStart <= current_date) ")
//        "and (mq.timeStart = (SELECT MAX(timeStart) FROM MqqPriceEntity WHERE timeStart <= CURDATE() and vendorCode = mq.vendorCode and itemCode = mq.itemCode)) ")
    List<MoqDTO> findMoqMinAndLeadTimeByItemCode(@Param("listItem") List<String> listItem);

    default Map<String, List<MoqDTO>> findPriceByItemCodeMap(List<String> listItem) {
        return findMoqMinAndLeadTimeByItemCode(listItem).stream().collect(Collectors.groupingBy(MoqDTO::getItemCode));
    }

    @Query(value = "select new com.facenet.mrp.service.dto.ItemOnPrPo(s.poCode, s.prCode, s.mrpCode, s.itemCode, s.itemName, s.quantity, s.createdAt, s.dueDate, s.createPoUser, s.providerCode, s.providerName) " +
    "from SapOnOrderSummary s " +
//        "join PurchaseRecommendationDetailEntity pd on s.itemCode = pd.itemCode join MqqPriceEntity m on m.itemPriceId = pd.moqPriceId " +
    "where s.itemCode in ?1 and s.status = 'O'")
//        "and s.dueDate >= ?2 and s.dueDate <= ?3")
      Page<ItemOnPrPo> getItemOnPrPo(@Param("itemCode") List<String> itemCode,
//                                     @Param("timeStart") Date timeStart,@Param("timeEnd") Date timeEnd,
                                     Pageable pageable);

    @Transactional
    @Modifying
    @Query("update MqqPriceEntity q set q.checkNew = false where q.itemCode = :itemCode and q.checkNew = true and q.vendorCode = :vendorCode")
    Integer updateNewestMoq(@Param("itemCode")String itemCode, @Param("vendorCode") String vendorCode);

    @Query(value = "select new com.facenet.mrp.service.dto.InventorySupplierDTO(m.vendorCode, m.price, m.currency) from MqqPriceEntity m where m.itemCode = :itemCode and m.isActive = 1 and m.isPromotion = false ")
    List<InventorySupplierDTO> getAllVendorAndPriceByItemCode(@Param("itemCode")String itemCode);

    @Query("select new com.facenet.mrp.service.dto.MoqDTO(mq.itemPriceId,mq.vendorCode,mq.itemCode, mq.rangeStart, mq.rangeEnd,mq.price,lt.leadTime, mq.currency,mq.timeEnd,mq.note) " +
        "from MqqPriceEntity mq " +
        "left join LeadTimeEntity lt on lt.itemCode = mq.itemCode and lt.vendorCode = mq.vendorCode " +
        "where lt.isActive = 1 and mq.isActive = 1 " +
        "and mq.itemCode = ?2 " +
        "and mq.vendorCode = ?1 " +
        "and (mq.timeEnd >= current_date or mq.timeEnd is null) " +
        "and ((mq.timeStart = (SELECT MAX(timeStart) FROM MqqPriceEntity WHERE timeStart <= CURDATE() and vendorCode = mq.vendorCode and itemCode = mq.itemCode)))")
    List<MoqDTO> findMoqMinAndLeadTimeByItemCodeAndVendorCode(String vendorCode, String itemCode);

}

