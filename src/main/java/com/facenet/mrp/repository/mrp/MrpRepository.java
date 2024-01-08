package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.MrpEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface MrpRepository extends PagingAndSortingRepository<MrpEntity, Integer> {

    @Query(value = "select count(mrpCode) from MrpEntity")
    Integer mrpCodeCount();

    @Modifying
    @Query("update MrpEntity m set m.isActive = 0 where m.mrpPoId = :soCode")
    void deleteAllBySoCode(@Param("soCode") String productOrderCode);
}
