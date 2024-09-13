package com.facenet.mrp.repository.mrp;
import com.facenet.mrp.domain.mrp.ProductOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ProductOrderRepository  extends PagingAndSortingRepository<ProductOrder, Integer>, ProductOrderCustomRepository{

    @Query(value = "SELECT p FROM ProductOrder p where p.isActive = 1 " +
        "AND (:pocode IS NULL OR p.productOrderCode like %:pocode%) " +
        "AND (:customerCode IS NULL OR p.customerId like %:customerCode%) " +
        "AND (:customerName IS NULL OR p.customerName like %:customerName%) " +
        "AND (:poType IS NULL OR p.productOrderType like %:poType%) " +
        "AND (:orderedTime IS NULL OR p.orderDate >= :orderedTime) " +
        "AND (:deliveryTime IS NULL OR p.deliverDate <= :deliveryTime) " +
        "AND (:salesCode IS NULL OR p.partCode like %:salesCode%) " +
        "AND (:salesName IS NULL OR p.partName like %:salesName%) "+
        "ORDER BY p.createdAt desc "
    )
    Page<ProductOrder> getAllProductOrder(Pageable pageable,
                                          @Param("pocode")String pocode,
                                          @Param("customerCode")String customerCode,
                                          @Param("customerName")String customerName,
                                          @Param("poType")String poType,
                                          @Param("orderedTime") LocalDate orderedTime,
                                          @Param("deliveryTime")LocalDate deliveryTime,
                                          @Param("salesCode")String salesCode,
                                          @Param("salesName")String salesName
    );
    @Query(value = "select p from ProductOrder p where p.isActive = 1 and p.productOrderCode = :productOrderCode")
    ProductOrder findAllByProductOrderCode(@Param("productOrderCode") String productOrderCode);

    ProductOrder findProductOrderByProductOrderCodeAndIsActive(String productOrderCode, Byte isActive);

    ProductOrder findProductOrderByMrpPoIdAndIsActive(String mrpPoID, Byte isActive);

    @Query(value = "select p from ProductOrder p where p.isActive = 1 and p.productOrderCode = :productOrderCode")
    ProductOrder findByProductOrderCode(@Param("productOrderCode") String productOrderCode);

    @Transactional
    @Modifying
    @Query(value = "update ProductOrder p set p.status = 2, p.updatedAt = current_time, p.updatedBy= :updateBy where p.isActive = 1 and p.productOrderCode = :productOrderCode")
    Integer sendAnalysisPO(@Param("productOrderCode") String productOrderCode,@Param("updateBy") String updateBy);

    @Transactional
    @Modifying
    @Query(value = "update ProductOrderDetail po set po.status = 2 ,po.updatedAt = current_time,po.updatedBy= :updateBy where po.isActive = 1 and po.status < 3 and po.productOrderCode.mrpPoId = :productOrderCode and po.productCode in (:list)")
    Integer sendAnalysisAllProductPoNotNullBomVersion(@Param("productOrderCode") String productOrderCode,@Param("updateBy") String updateBy,@Param("list") List<String> list);

    @Transactional
    @Modifying
    @Query(value = "update ProductOrderDetail po set po.status = 2, po.updatedAt = current_time ,po.updatedBy= :updateBy where po.isActive = 1 and po.status < 3 and po.productOrderCode.mrpPoId = :productOrderCode and po.productCode = :productCode")
    Integer sendAnalysisProductInPO(@Param("productOrderCode") String productOrderCode,@Param("productCode") String productCode,@Param("updateBy") String updateBy);

    @Transactional
    @Modifying
    @Query(value = "update ProductOrder p set p.status = :status ,p.updatedAt = current_time,p.updatedBy= :updateBy where p.isActive = 1 and p.productOrderCode = :productOrderCode")
    Integer updateStatusPO(@Param("status") Integer status,@Param("productOrderCode") String productOrderCode,@Param("updateBy") String updateBy);

    @Transactional
    @Modifying
    @Query(value = "update ProductOrder p set p.isActive = 2, p.mrpPoId = :newMrpPoId where p.isActive = 1 and p.productOrderCode = :productOrderCode")
    Integer deletePO(@Param("productOrderCode") String productOrderCode, @Param("newMrpPoId") String s);

    @Transactional
    @Modifying
    @Query(value = "update ProductOrder p set p.status = 1, p.updatedAt = current_time , p.updatedBy = :updatedBy where p.isActive = 1 and p.productOrderCode = :productOrderCode")
    Integer deleteFC(@Param("productOrderCode") String productOrderCode, @Param("updatedBy")String updatedBy);

    @Query("select distinct po.productOrderCode from ProductOrder po where po.isActive = 1 and po.productOrderCode like :input%")
    List<String> getAllPOCode(@Param("input") String input, Pageable pageable);

    @Query("select distinct po.customerId from ProductOrder po where po.isActive = 1 and po.customerId like :input%")
    List<String> getAllCustomerId(@Param("input") String input, Pageable pageable);

    @Query("select distinct po.customerName from ProductOrder po where po.isActive = 1 and po.customerName like :input%")
    List<String> getAllCustomerName(@Param("input") String customerName, Pageable pageable);

    @Query("select distinct po.productOrderType from ProductOrder po where po.isActive = 1 and po.productOrderType like :input%")
    List<String> getAllPOType(@Param("input") String poType, Pageable pageable);

    @Query("select distinct po.partCode from ProductOrder po where po.isActive = 1 and po.partCode like :input%")
    List<String> getAllSalesCode(@Param("input") String salesCode, Pageable pageable);

    @Query("select distinct po.partName from ProductOrder po where po.isActive = 1 and po.partName like :input%")
    List<String> getAllSalesName(@Param("input") String salesName, Pageable pageable);

    @Query(value = "select count(f) from ProductOrder f where f.type = 'Forecast' ")
    Integer getForecastCode();

    @Query("select p from ProductOrder p where p.isActive = 1 and p.deliverDate > :date")
    List<ProductOrder> getAllProductOrder(@Param("date") Date date);

    @Query(value = "SELECT COUNT(p) FROM ProductOrder p")
    Integer countActiveProductOrders();
}
