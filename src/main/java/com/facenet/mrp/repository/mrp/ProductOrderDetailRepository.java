package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.ProductOrderDetail;
import com.facenet.mrp.service.dto.ProductDetail;
import com.facenet.mrp.service.dto.ProductOrderItemsDTO;
import com.facenet.mrp.service.dto.mrp.CountPo;
import com.facenet.mrp.service.dto.mrp.MrpItemQuantityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface ProductOrderDetailRepository extends PagingAndSortingRepository<ProductOrderDetail,Integer> {
    @Query("select count (p.id) from ProductOrderDetail p where p.productOrderCode.productOrderCode = :productOrderCode and p.status in :status and p.isActive = 1")
    Integer countByItemOfSOAndStatusIn(@Param("productOrderCode") String productOrderCode, @Param("status") List<Integer> status);

    @Query(value = "select p from ProductOrderDetail p join ProductOrder po on p.productOrderCode = po.productOrderCode " +
        "where po.productOrderCode = :productOrderCode and po.isActive = 1 and p.isActive = 1 " +
        "and (:productCode is null or p.productCode like %:productCode%) " +
        "and (:productName is null or p.productName like %:productName%) " +
        "and (:bomVersion is null or p.bomVersion like %:bomVersion%) " +
        "and (:orderQuantity is null or p.quantity = :orderQuantity) " +
        "and (:orderedTime is null or p.orderDate >= :orderedTime) " +
        "and (:deliveryTime is null or p.deliverDate <= :deliveryTime) " +
        "and (:supplyMethod is null or p.supplyType like %:supplyMethod%) "
    )
    Page<ProductOrderDetail> getAllByProductOrderCode (Pageable pageable,
                                                       @Param("productOrderCode")String productOrderCode,
                                                       @Param("productCode")String productCode,
                                                       @Param("productName")String productName,
                                                       @Param("bomVersion")String bomVersion,
                                                       @Param("orderQuantity")int orderQuantity,
                                                       @Param("orderedTime")Date orderedTime,
                                                       @Param("deliveryTime")Date deliveryTime,
                                                       @Param("supplyMethod")String supplyMethod);

    @Query(value = "select p from ProductOrderDetail p join ProductOrder po on p.productOrderCode = po.productOrderCode " +
        "where po.productOrderCode = :productOrderCode and po.isActive = 1 and p.isActive = 1 " +
        "and (:productCode is null or p.productCode like %:productCode%) " +
        "and (:productName is null or p.productName like %:productName%) " +
        "and (:bomVersion is null or p.bomVersion like %:bomVersion%) " +
        "and (:orderedTime is null or p.orderDate >= :orderedTime) " +
        "and (:deliveryTime is null or p.deliverDate <= :deliveryTime) " +
        "and (:supplyMethod is null or p.supplyType like %:supplyMethod%) "
    )
    Page<ProductOrderDetail> getAllByProductOrderCodeWithNoQuantity (Pageable pageable,
                                                       @Param("productOrderCode")String productOrderCode,
                                                       @Param("productCode")String productCode,
                                                       @Param("productName")String productName,
                                                       @Param("bomVersion")String bomVersion,
                                                       @Param("orderedTime")Date orderedTime,
                                                       @Param("deliveryTime")Date deliveryTime,
                                                       @Param("supplyMethod")String supplyMethod);


    @Query(value = "select p from ProductOrderDetail p join ProductOrder po on p.productOrderCode = po.mrpPoId where po.isActive = 1 and p.isActive = 1 and p.productCode = :productCode and po.productOrderCode = :productOrderCode")
    ProductOrderDetail findByProductCode(@Param("productCode") String productCode,@Param("productOrderCode") String productOrderCode);

    @Query("select new com.facenet.mrp.service.dto.ProductOrderItemsDTO(p.id, p.productCode, p.productName, p.bomVersion, p.status) from ProductOrderDetail p " +
        "where (p.status <> 1 and p.status <> 6) and p.isActive=1 " +
        "and p.productOrderCode.mrpPoId=:mrpPoId " +
        "and (:productCode is null or p.productCode=:productCode) order by p.priority asc")
    List<ProductOrderItemsDTO> findByMrpPoId(@Param("mrpPoId") String mrpPoId, @Param("productCode") String productCode);

    @Transactional
    @Modifying
    @Query(value = "update product_order_detail as p join product_order as po on p.product_order_code = po.mrp_po_id set p.status = :status where po.is_active = 1 and p.is_active = 1 and p.product_code = :productCode and po.product_order_code = :productOrderCode",nativeQuery = true)
    Integer updateStatusProductInPO(@Param("status") Byte status,@Param("productCode") String productCode,@Param("productOrderCode") String productOrderCode);

    @Transactional
    @Modifying
    @Query(value = "update product_order_detail as p join product_order as po on p.product_order_code = po.mrp_po_id set p.status = :status where po.is_active = 1 and p.is_active = 1 and p.product_code in :productCode and po.product_order_code = :productOrderCode",nativeQuery = true)
    Integer updateStatusProductInPO(@Param("status") Byte status, @Param("productCode") List<String> productCode,@Param("productOrderCode") String productOrderCode);


    @Transactional
    @Modifying
    @Query(value = "update product_order_detail as p join product_order as po on p.product_order_code = po.mrp_po_id set p.is_active = 2 where p.is_active = 1 and p.product_code = :productCode and po.product_order_code = :productOrderCode",nativeQuery = true)
    Integer deleteProductInPO(@Param("productCode") String productCode,@Param("productOrderCode") String productOrderCode);

    @Query("select distinct p.productCode from ProductOrderDetail p where p.isActive = 1 and p.productCode like :input% ")
    List<String> getAllProductCodes(@Param("input") String input, Pageable pageable);

    @Query("select distinct p.productName from ProductOrderDetail p where p.isActive = 1 and p.productName like :input%")
    List<String> getAllProductNames(@Param("input") String input, Pageable pageable);

    @Query("select distinct p.supplyType from ProductOrderDetail p where p.isActive = 1 and p.supplyType like :input%")
    List<String> getAllSupplyTypes(@Param("input") String input, Pageable pageable);

    @Query("select distinct p.bomVersion from ProductOrderDetail p where p.isActive = 1 and p.bomVersion like :input%")
    List<String> getAllBomVersion(@Param("input") String input, Pageable pageable);

    @Query(value = "select p from ProductOrderDetail p, ProductOrder po where p.productOrderCode = po.mrpPoId " +
        "and p.productCode = :productCode " +
        "and po.productOrderCode = :productOrderCode " +
        "and p.isActive = 1")
    ProductOrderDetail getOneProductOrderDetail (
        @Param("productCode")String productCode,
        @Param("productOrderCode")String productOrderCode
    );

    @Query(value = "select count(pd) from ProductOrderDetail pd, ProductOrder po where pd.productOrderCode = po.mrpPoId " +
        "and pd.productCode = :productCode " +
        "and po.productOrderCode = :productOrderCode " +
        "and pd.isActive = 1")
    Integer checkExistProduct(
        @Param("productCode")String productCode,
        @Param("productOrderCode")String productOrderCode
    );

    @Query(value = "select p from ProductOrderDetail p join ProductOrder po on p.productOrderCode.mrpPoId = po.mrpPoId where p.isActive = 1 and po.isActive = 1 and po.productOrderCode = :productOrderCode")
    List<ProductOrderDetail> findAllByProductOrderCode(@Param("productOrderCode") String productOrderCode);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.MrpItemQuantityDTO(pd.id, pd.productCode, pd.quantity, pd.bomVersion) from ProductOrderDetail pd, ProductOrder po " +
        "where pd.productOrderCode = po.mrpPoId " +
        "and pd.id in (:itemId) " +
        "and po.productOrderCode = :soCode " +
        "and pd.isActive = 1 ")
    List<MrpItemQuantityDTO> getQuantity(@Param("itemId") List<Integer> itemId, @Param("soCode") String soCode);

    @Modifying
    @Query("update ProductOrderDetail p set p.isActive = 2 where p.productOrderCode.mrpPoId = :mrpPoId and p.isActive = 1")
    int deleteAllProductOfPO(@Param("mrpPoId") String mrpPoId);

    @Query(value = "select new com.facenet.mrp.service.dto.ProductDetail(p.productOrderCode, pd.productCode, pd.bomVersion) from ProductOrderDetail pd, ProductOrder p " +
        "where p.mrpPoId = pd.productOrderCode " +
        "and p.productOrderCode in :soCode " +
        "and pd.isActive = 1 " +
        "and p.isActive = 1")
    List<ProductDetail> countSoBySoCode(@Param("soCode")List<String> soCode);

    @Query(value = "select p from ProductOrderDetail p where p.isActive = 1 and p.status in (1,2,3,4,5) and p.productOrderCode.productOrderCode = :productOrderCode")
    List<ProductOrderDetail> getAllBySO(@Param("productOrderCode") String productOrderCode);

    @Query("select new com.facenet.mrp.service.dto.mrp.CountPo(p.productOrderCode, sum(d.materialChildrenCount)) from ProductOrder p, ProductOrderDetail d " +
        "where p.mrpPoId = d.productOrderCode " +
        "and p.isActive = 1 " +
        "and d.isActive = 1 " +
        "and p.productOrderCode in :productOrderCode " +
        "group by p.productOrderCode")
    List<CountPo> getCountMaterialByPo(@Param("productOrderCode") List<String> productOrderCode);

    @Query("select p from ProductOrderDetail p " +
        "where p.productOrderCode.mrpPoId = :mrpPoId " +
        "and p.productCode = :itemCode " +
        "and p.isActive = 1 ")
    ProductOrderDetail getProductOrderDetailByItem(@Param("mrpPoId")String mrpPoId, @Param("itemCode")String itemCode);
}
