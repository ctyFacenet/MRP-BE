package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.ForecastOrderDetailEntity;
import com.facenet.mrp.domain.mrp.ProductOrder;
import com.facenet.mrp.service.ForecastOrderDetailService;
import com.facenet.mrp.service.dto.ProductDetail;
import com.facenet.mrp.service.dto.ProductOrderItemsDTO;
import com.facenet.mrp.service.dto.mrp.CountPo;
import com.facenet.mrp.service.dto.mrp.MrpItemQuantityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.List;

@Repository
public interface ForecastOrderDetailRepository extends JpaRepository<ForecastOrderDetailEntity, Long> {

    @Query(value = "select f from ProductOrder f where f.isActive = 1 and f.productOrderCode = :fcCode")
    ProductOrder getForecastOrder(@Param("fcCode") String fcCode);

    @Query( value = "select f from ForecastOrderDetailEntity f where f.isActive = true and f.productOrderCode = :fcCode")
    List<ForecastOrderDetailEntity> getListForecastOrderDetail(@Param("fcCode") String fcCode);

    @Query( value = "update forecast_order_detail f set f.is_active = false where f.is_active = true and f.product_order_code = :fcCode",nativeQuery = true)
    Integer deleteAllByFcCode(@Param("fcCode") String fcCode);

    @Query(value = "select f from ForecastOrderDetailEntity f where f.isActive = true and f.status = 2 and f.productOrderCode = :productOrderCode and f.itemCode = :itemCode")
    ForecastOrderDetailEntity findForecastDetailByItemCode(@Param("productOrderCode")String productOrderCode, @Param("itemCode")String itemCode );

    @Transactional
    @Modifying
    @Query(value = "update ForecastOrderDetailEntity f set f.status = 2 ,f.updatedAt = current_time,f.updatedBy= :updateBy where f.isActive = true and f.status < 3 and f.productOrderCode = :productOrderCode and f.itemCode in (:list)")
    Integer sendAnalysisAllFc(@Param("productOrderCode") String productOrderCode,@Param("updateBy") String updateBy,@Param("list") List<String> list);

    @Transactional
    @Modifying
    @Query(value = "update ForecastOrderDetailEntity f set f.status = 1, f.updatedAt = current_time , f.updatedBy = :updatedBy where f.isActive = true and f.status = 2 and f.productOrderCode = :productOrderCode")
    Integer deleteFCDetail(@Param("productOrderCode") String productOrderCode, @Param("updatedBy") String updateBy);

    @Transactional
    @Modifying
    @Query(value = "update ForecastOrderDetailEntity f set f.status = 1, f.updatedAt = current_time , f.updatedBy = :updatedBy where f.isActive = true and f.productOrderCode = :productOrderCode and f.itemCode = :itemCode")
    Integer deleteFCDetailByItemCode(@Param("productOrderCode") String productOrderCode, @Param("itemCode") String itemCode,@Param("updatedBy") String updateBy);

    @Query("select new com.facenet.mrp.service.dto.ProductOrderItemsDTO(f.forecastOrderDetailId, f.itemCode, f.itemDescription, f.bomVersion, f.status) from ForecastOrderDetailEntity f " +
        "where f.productOrderCode = :productOrderCode and f.status = 2 and f.isActive = true and f.itemGroupCode = 'BTP'")
    List<ProductOrderItemsDTO> getAllItems(@Param("productOrderCode") String productOrderCode);

    @Modifying
    @Query("update ForecastOrderDetailEntity f set f.status = :status where f.itemCode in :itemList")
    int changeItemStatusOf(@Param("itemList") List<String> itemList, @Param("status") int status);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.MrpItemQuantityDTO(f.forecastOrderDetailId, f.itemCode, f.totalRequest, f.bomVersion) " +
        "from ForecastOrderDetailEntity f, ProductOrder po " +
        "where f.productOrderCode = po.productOrderCode " +
        "and f.forecastOrderDetailId in (:itemId) " +
        "and po.productOrderCode = :fcCode " +
        "and f.isActive = true ")
    List<MrpItemQuantityDTO> getQuantity(@Param("itemId") List<Integer> itemId, @Param("fcCode") String fcCode);

    @Query(value = "select new com.facenet.mrp.service.dto.ProductDetail(f.productOrderCode, f.itemCode, f.bomVersion, f.itemGroupCode) from ForecastOrderDetailEntity f where f.productOrderCode in :soCode and f.isActive = true")
    List<ProductDetail> countFcBySoCode(@Param("soCode")List<String> soCode);

    @Query("select new com.facenet.mrp.service.dto.mrp.CountPo(p.productOrderCode, sum(d.materialChildrenCount)) from ProductOrder p, ForecastOrderDetailEntity d " +
        "where p.productOrderCode = d.productOrderCode " +
        "and p.isActive = 1 " +
        "and d.isActive = true " +
        "and p.productOrderCode in :productOrderCode " +
        "group by p.productOrderCode")
    List<CountPo> getCountMaterialByPo(@Param("productOrderCode") List<String> productOrderCode);

    @Query("select f from ForecastOrderDetailEntity f " +
        "where f.productOrderCode = :productOrderCode " +
        "and f.itemCode = :itemCode " +
        "and f.isActive = true ")
    ForecastOrderDetailEntity getForecastOrderDetailEntitiesByItem(@Param("productOrderCode")String productOrderCode, @Param("itemCode")String itemCode);
}
