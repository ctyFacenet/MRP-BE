package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.SapBranchGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchGroupRepository extends JpaRepository<SapBranchGroupEntity,Long> {
    @Query("select DISTINCT s.uBranchCode from SapBranchGroupEntity s")
    List<String> getDistinctBranchCodes();

    @Query("select s from SapBranchGroupEntity s where s.uBranchCode = :branch")
    List<SapBranchGroupEntity> getSapGroupEntities(@Param("branch") String branch);
}
