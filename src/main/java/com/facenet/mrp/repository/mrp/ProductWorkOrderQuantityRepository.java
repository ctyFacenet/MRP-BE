package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.ProductWorkOrderQuantityEntity;
import com.facenet.mrp.service.dto.request.ScadaQuantityDailyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductWorkOrderQuantityRepository extends JpaRepository<ProductWorkOrderQuantityEntity,Long> {
    @Query("select com.facenet.mrp.service.dto.request.ScadaQuantityDailyDTO(m.productCode,m.productName,m.woName,m.sapWO,n.date, n.packingQuantity) from ProductWorkOrderQuantityEntity m inner join ProductWorkOrderDailyQuantityEntity n on m.id = n.product_work_order_quantity_id " +
        "where :startDate <= n.date and n.date <= :endDate")
    List<ScadaQuantityDailyDTO> findAllByDueDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
