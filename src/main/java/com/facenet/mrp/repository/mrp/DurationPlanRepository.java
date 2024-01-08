package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.DurationPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DurationPlanRepository extends JpaRepository<DurationPlanEntity,Long> {

    @Modifying
    @Query("update DurationPlanEntity d set d.isActive = false where d.planCode = :planCode")
    Integer deleteDurationPlanEntity(@Param("planCode") String planCode);
}
