package com.facenet.mrp.domain.mrp;

import com.facenet.mrp.service.dto.KeyDictionaryDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ColumnPropertyCustomRepository {
    List<ColumnPropertyEntity> getAllColumn(KeyDictionaryDTO input, String common, Pageable pageable);
}
