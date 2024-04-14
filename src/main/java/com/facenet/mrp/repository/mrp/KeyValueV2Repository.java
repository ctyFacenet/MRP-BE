package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.ColumnPropertyEntity;
import com.facenet.mrp.domain.mrp.KeyValueEntityV2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface KeyValueV2Repository extends JpaRepository<KeyValueEntityV2, Integer> {
    List<KeyValueEntityV2> findByEntityTypeAndEntityKeyInAndIsActiveTrue(Integer entityType, Collection<Integer> entityKeys);

    List<KeyValueEntityV2> findByEntityTypeAndEntityKeyAndIsActiveTrue(Integer entityType, Integer entityKeys);

    List<KeyValueEntityV2> findByEntityTypeAndIsActiveTrue(Integer entityType);

    KeyValueEntityV2 findByEntityKeyAndColumnPropertyEntity(Integer entityKey, ColumnPropertyEntity columnProperty);
}
