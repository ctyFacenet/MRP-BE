package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.ResultMrpJsonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultMrpRepository extends JpaRepository<ResultMrpJsonEntity,Long> {
    @Query(value = "select r from ResultMrpJsonEntity r where r.isActive = 1 and r.mrpSubCode = :mrpSubCode")
    ResultMrpJsonEntity getResultMrpJsonByMrpSubCode(@Param("mrpSubCode") String mrpSubCode);

    @Query(value = "select * from result_mrp_json r where r.is_active = 1 and r.mrp_code = :mrpCode order by r.created_at desc limit 1",nativeQuery = true)
    ResultMrpJsonEntity getResultMrpJson(@Param("mrpCode") String mrpCode);
}
