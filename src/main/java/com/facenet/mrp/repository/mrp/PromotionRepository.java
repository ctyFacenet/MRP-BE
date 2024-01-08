package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.PromotionEntity;
import com.facenet.mrp.service.dto.PriceListDTO;
import com.facenet.mrp.service.dto.PromotionDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PromotionRepository extends PagingAndSortingRepository<PromotionEntity, Integer> {

    @Query("select distinct new com.facenet.mrp.service.dto.PromotionDTO(p.timeStart, p.timeEnd, p.note) " +
        "from PromotionEntity p " +
        "where p.vendorCode = :vendorCode " +
        "and p.itemCode = :itemCode " +
        "and p.isActive = 1 " +
        "and p.timeEnd >= current_date " +
        "and p.timeStart <= current_date " +
        "order by p.createdAt desc ")
    List<PromotionDTO> getAllTimeInPromotion(
        @Param("vendorCode")String vendorCode,
        @Param("itemCode")String itemCode);

//    @Query("select distinct new com.facenet.mrp.service.dto.PriceListDTO(p.rangeStart, p.rangeEnd, p.price, p.currency) " +
//        "from PromotionEntity p " +
//        "where p.vendorCode = :vendorCode " +
//        "and p.itemCode = :itemCode " +
//        "and p.isActive = 1 " +
//        "and p.timeEnd >= current_date " +
//        "and p.timeStart = :timeStart " +
//        "and p.timeEnd = :timeEnd " +
//        "order by p.createdAt asc ")
//    List<PriceListDTO> getAllPriceList (
//        @Param("vendorCode")String vendorCode,
//        @Param("itemCode")String itemCode,
//        @Param("timeStart")Date timeStart,
//        @Param("timeEnd")Date timeEnd);

}
