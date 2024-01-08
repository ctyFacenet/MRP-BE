package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.OporEntity;
import com.facenet.mrp.service.dto.PurchaseRequestDTO;
import com.facenet.mrp.service.dto.sap.PrByItemSapDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OporRepository extends JpaRepository<OporEntity,Long> {

    @Query(value = "select new com.facenet.mrp.service.dto.sap.PrByItemSapDTO(p.itemCode,sum(p.quantity),p.dscription) from OprqEntity o join Prq1Entity p " +
        "on p.docEntry = o.docNum " +
        "where o.docStatus = 'O' " +
        "and p.uSo is NULL " +
        "and p.uMcode is NULL " +
        "and p.lineStatus = 'O' " +
//        "and ((o.docDate = :createdDate) or (o.docDate > :createdDate)) " +
        "group by p.itemCode,p.dscription ")
    List<PrByItemSapDTO> getPrByItemSap();

    @Query(value = "select new com.facenet.mrp.service.dto.sap.PrByItemSapDTO(p.itemCode,sum(p.quantity),p.dscription) from OporEntity o join Por1Entity p " +
        "on p.docEntry = o.docNum " +
        "where o.docStatus = 'O' " +
        "and p.lineStatus = 'O' " +
        "and p.uSo is NULL " +
        "and p.uMcode is NULL " +
//        "and ((o.docDate = :createdDate) or (o.docDate > :createdDate)) " +
        "group by p.itemCode,p.dscription ")
    List<PrByItemSapDTO> getPoByItemSap();

    @Query(value = "SELECT COUNT(DISTINCT a.docEntry) FROM OporEntity a left join Por1Entity b on a.docEntry = b.docEntry left join Pdn1Entity c on c.baseDocNum = b.docEntry left join OpdnEntity d on c.docEntry = d.docEntry left join Prq1Entity e on e.docEntry = b.baseDocNum left join OprqEntity f on e.docEntry = f.docEntry ")
    long countPo();

    @Query(value = "SELECT COUNT(DISTINCT b.docEntry) FROM Prq1Entity a left join OprqEntity b on a.docEntry = b.docEntry left join Por1Entity c on a.docEntry = c.baseDocNum left join OporEntity d on c.docEntry = d.docEntry left join Pdn1Entity e on e.baseDocNum = c.docEntry left join OpdnEntity f on e.docEntry = f.docEntry ")
    long countPr();

    @Query(value = "SELECT COUNT(DISTINCT b.docEntry) FROM Pdn1Entity a left join OpdnEntity b on a.docEntry = b.docEntry left join Por1Entity c on c.docEntry = a.baseDocNum left join OporEntity d on c.docEntry = d.docEntry left join Prq1Entity e on e.docEntry = c.baseDocNum left join OprqEntity f on e.docEntry = f.docEntry ")
    long countGrpo();

}
