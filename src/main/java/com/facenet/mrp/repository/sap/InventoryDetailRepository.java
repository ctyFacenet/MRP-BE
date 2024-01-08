package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.OwhsEntity;
import com.facenet.mrp.service.dto.InventoryDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryDetailRepository extends JpaRepository<OwhsEntity,Long> {
    @Query(value = "select new com.facenet.mrp.service.dto.InventoryDetailDTO(a.whsCode,a.whsName,b.onHand,b.onOrder,b.minStock,b.maxStock) from OwhsEntity as a join OitwEntity as b on a.whsCode = b.whsCode where b.itemCode = :ItemCode")
    List<InventoryDetailDTO> getInventoryDetailByItemCode(@Param("ItemCode") String ItemCode);

//    @Query(value = "select new com.facenet.mrp.service.dto.InventorySupplierDTO(b.cardCode,b.cardName,p.price,p.currency) from Itm2Entity as a join OcrdEntity AS b on a.vendorCode = b.cardCode join Pdn1Entity AS p on a.itemCode = p.itemCode where a.itemCode = :ItemCode order by p.docDate desc")
//    List<InventorySupplierDTO> getInventorySupplierByItemCode(@Param("ItemCode") String ItemCode, Pageable pageable);
}
