package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.SapOnOrderDurationDetail;
import com.facenet.mrp.domain.mrp.SapOnOrderSummary;
import com.facenet.mrp.service.dto.mrp.MonitoringDetailDTO;
import com.facenet.mrp.service.dto.mrp.OnOrderDurationReport;
import com.facenet.mrp.service.dto.mrp.PrGrpoAnalysisReport;
import com.facenet.mrp.service.dto.mrp.QuantityModelForReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public interface SapOnOrderDurationDetailRepository extends JpaRepository<SapOnOrderDurationDetail,Integer> {
    @Query("select s.note from SapOnOrderDurationDetail s where s.isActive = true and s.fkId = :id " +
        "and s.createdAt = (select max(n.dueDate) from SapOnOrderDurationDetail n " +
        "where n.fkId = :id)")
    String getNote(@Param("id") Integer id);


    @Query("select s from SapOnOrderDurationDetail s where s.isActive = true and s.fkId = :id")
    List<SapOnOrderDurationDetail> getAllDetail(@Param("id") Integer id);

    @Query("select new com.facenet.mrp.service.dto.mrp.MonitoringDetailDTO(s.id,s.dueDate,s.quantity,s.note) from SapOnOrderDurationDetail s where s.fkId = :id and s.isActive = true ")
    List<MonitoringDetailDTO> getAllDetailMonitoring(@Param("id") Integer id);

    @Query("select distinct (s.fkId) from SapOnOrderDurationDetail s where s.isActive = true and s.dueDate between :startTime and :endTime")
    Page<Integer> getDetailBetweenRangeTime(@Param("startTime") Date startTime, @Param("endTime") Date endTime, Pageable pageable);

    @Query("select distinct (s.fkId) from SapOnOrderDurationDetail s where s.isActive = true and s.dueDate between :startTime and :endTime")
    List<Integer> getListDetailBetweenRangeTime(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Query("select p from SapOnOrderSummary p where p.id in :list")
    List<SapOnOrderSummary> getSapOnOrderByListFk(@Param("list") List<Integer> list);

    @Query("select sum(p.quantity) from SapOnOrderDurationDetail p join SapOnOrderSummary s on s.id = p.fkId where p.isActive = true and s.soCode = :soCode")
    Double getSapOnOrderDurationDetailBySo(@Param("soCode") String soCode);

    @Query("select new com.facenet.mrp.service.dto.mrp.OnOrderDurationReport(s.id, s.soCode, s.mrpCode, s.itemCode, s.itemName, s.providerCode, s.providerName, d.id,d.dueDate, d.quantity) " +
        "from SapOnOrderSummary s, SapOnOrderDurationDetail d " +
        "where d.isActive = true and s.id = d.fkId " +
        "and d.dueDate >= :startDate " +
        "and d.dueDate <= :endDate " +
        "order by d.dueDate asc")
    List<OnOrderDurationReport> getDetailReportByRangeTime(@Param("startDate")Date startDate,@Param("endDate")Date endDate);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.MonitoringDetailDTO(s.id,s.dueDate,s.quantity,s.note,so.poCode) from SapOnOrderDurationDetail s join SapOnOrderSummary so on s.fkId = so.id where s.isActive = true and so.status = 'O' and s.fkId in :listFkId")
    List<MonitoringDetailDTO> getListMonitorWithPo(@Param("listFkId") List<Integer> listFkId);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.MonitoringDetailDTO(s.id,s.dueDate,s.quantity,s.note,so.poCode) from SapOnOrderDurationDetail s join SapOnOrderSummary so on s.fkId = so.id where s.isActive = true and so.status = 'O' and s.fkId = :id")
    List<MonitoringDetailDTO> getListMonitor(@Param("id") Integer id);

    @Query(value = "select count (p) from DurationPlanEntity p")
    Integer countAll();

    @Query("select new com.facenet.mrp.service.dto.mrp.OnOrderDurationReport(s.id, s.soCode, s.mrpCode, s.itemCode, d.id, d.dueDate, d.quantity) " +
        "from SapOnOrderSummary s, SapOnOrderDurationDetail d " +
        "where s.id = d.fkId " +
        "and s.soCode = :soCode " +
        "and d.isActive = true " +
        "order by d.dueDate")
    List<OnOrderDurationReport> getAllDurationBySoCode(@Param("soCode")String soCode);

    @Query("select sum(y.quantity) from SapOnOrderSummary a join SapOnOrderDurationDetail y on a.id = y.fkId where a.itemCode = :itemCode and a.mrpCode = :mrpCode")
    Double getSum(@Param("itemCode") String itemCode,  @Param("mrpCode") String mrpCode);
    @Query("select a from SapOnOrderSummary a where a.itemCode = :itemCode and a.mrpCode = :mrpCode and a.type in ('PO','PR')")
    List<SapOnOrderSummary> findSapOnOrderSummary(@Param("itemCode") String itemCode,  @Param("mrpCode") String mrpCode);

    @Query("select a from SapOnOrderSummary a where a.mrpCode in :listMrpCode and a.type in ('PO','PR')")
    List<SapOnOrderSummary> catchSapOnOrderSummary(  @Param("listMrpCode") List<String> listMrpCode);
}
