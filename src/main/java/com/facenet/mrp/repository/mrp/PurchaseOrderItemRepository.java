package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.PurchaseOrderItemEntity;
import com.facenet.mrp.service.dto.mrp.ItemQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItemEntity, Long> {
    @Query("SELECT p FROM PurchaseOrderItemEntity p WHERE p.purchaseOrder.id = :purchaseOrderId")
    List<PurchaseOrderItemEntity> findByPurchaseOrderId(@Param("purchaseOrderId") Long purchaseOrderId);

    @Query("SELECT new com.facenet.mrp.service.dto.mrp.ItemQuantity(poi.itemCode, "
        + "COALESCE(SUM(poi.quantity) * 1.0, 0.0) - COALESCE(SUM(poig.quantity) * 1.0, 0.0)) "
        + "FROM PurchaseOrderItemEntity poi "
        + "LEFT JOIN PurchaseOrderItemProgressEntity poig ON poi.id = poig.purchaseOrderItem.id "
        + "GROUP BY poi.itemCode")
    List<ItemQuantity> getOpenQuantity();

    default Map<String, Double> getOpenQuantityMap() {
        return getOpenQuantity().stream().collect(Collectors.toMap(
            ItemQuantity::getItemCode, ItemQuantity::getQuantity
        ));
    }

}
