package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.KeyValueEntity;
import com.facenet.mrp.domain.mrp.KeyValueEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface KeyValueRepository extends JpaRepository<KeyValueEntity, KeyValueEntityId> {
    @Query("select m from KeyValueEntity m " + "where m.id.entityId = :entityIds and m.id.entityKey in :entityKey and m.isActive = true ")
    List<KeyValueEntity> findByIdIn(@Param("entityIds") Integer entityIds, @Param("entityKey") Collection<Integer> entityKey);

    @Query("select m from KeyValueEntity m " + "where m.id.entityId = :entityIds and m.isActive = true ")
    List<KeyValueEntity> findAllByEntityId(@Param("entityIds") Integer entityIds);

}
