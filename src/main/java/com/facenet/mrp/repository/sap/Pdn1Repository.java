package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.Pdn1Entity;
import com.facenet.mrp.domain.sap.Pdn1EntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Pdn1Repository extends JpaRepository<Pdn1Entity, Pdn1EntityPK> {
    @Query("select sum(p.quantity) from Pdn1Entity p " +
        "where p.baseEntry = :poCode and p.baseType = 22 and p.baseLine = :lineNumber")
    Double getGrpoQuantityByItemCodeAndPoCode(
                                              @Param("poCode") Integer poCode,
                                              @Param("lineNumber") Integer lineNumber);

}
