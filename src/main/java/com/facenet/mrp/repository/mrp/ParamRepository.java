package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ParamRepository extends JpaRepository<Param, Integer> {
    @Query("select p.paramValue from Param p where p.paramCode = :paramCode")
    Set<String > findAllParamValueByCode(@org.springframework.data.repository.query.Param("paramCode") String paramCode);
    List<Param> getAllByParamCode(Object paramCode);
}
