package com.facenet.mrp.repository.sap;

import com.facenet.mrp.service.dto.PoDto;
import com.facenet.mrp.service.model.PoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface PoCustomRepository {

    Page<PoDto> getAllPoList(Pageable pageable, PoFilter poFilter);

}
