package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.PurchaseRequestDetailEntity;
import com.facenet.mrp.domain.mrp.PurchaseRequestEntity;
import com.facenet.mrp.service.dto.mrp.ItemQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface PurchaseRequestDetailEntityRepository extends JpaRepository<PurchaseRequestDetailEntity, Integer>
{
    List<PurchaseRequestDetailEntity> findAllByPrCodeAndIsActive(String prCode, int active);
    List<PurchaseRequestDetailEntity> findByItemCode(String itemCode);

    @Query("select new com.facenet.mrp.service.dto.mrp.ItemQuantity(pr.itemCode, sum(pr.requiredQuantity)) " +
        "from PurchaseRequestDetailEntity pr " +
        "where pr.isActive = 1" +
        "group by pr.itemCode ")
    List<ItemQuantity> getOpenQuantity();

    default Map<String, Double> getOpenQuantityMap() {
        return getOpenQuantity().stream().collect(Collectors.toMap(
            ItemQuantity::getItemCode, ItemQuantity::getQuantity
        ));
    }
}
