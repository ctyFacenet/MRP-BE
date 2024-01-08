package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.AlertItemSoEntity;
import com.facenet.mrp.domain.mrp.AlertSoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AlertSoRepository extends JpaRepository<AlertSoEntity,Long> {
    @Query(value = "select a from AlertSoEntity a where a.timeEnd > :timeLandMark")
    List<AlertSoEntity> getAlertSoEntityBeforeTime(@Param("timeLandMark") Date timeLandMark);

    @Query(value = "select a from AlertItemSoEntity a where a.timeEnd > :timeLandMark")
    List<AlertItemSoEntity> getAlertItemSoEntityBeforeTime(@Param("timeLandMark") Date timeLandMark);
}
