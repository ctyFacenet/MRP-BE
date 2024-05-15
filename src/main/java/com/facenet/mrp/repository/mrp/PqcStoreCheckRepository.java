package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.PqcStoreCheckEntity;
import com.facenet.mrp.service.dto.request.QmsQuantityDailyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PqcStoreCheckRepository extends JpaRepository<PqcStoreCheckEntity,Long> {

    @Query("select new com.facenet.mrp.service.dto.request.QmsQuantityDailyDTO(n.productionCode,n.productionName,n.purchaseOrderCode,n.workOrderId,n.quantityPlan,m.checkDate,m.quatityStore,m.importDate) from PqcStoreCheckEntity m inner join PqcWorkOrder n on n.id = m.workOrderId " +
        "where :startDate <= m.importDate and m.importDate <= :endDate")
    List<QmsQuantityDailyDTO> findAllByDueDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("select a from PqcStoreCheckEntity a where a.importDate is null")
    List<PqcStoreCheckEntity> getAllIsNull();
}
