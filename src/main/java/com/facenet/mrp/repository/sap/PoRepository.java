package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.OporEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PoRepository extends PagingAndSortingRepository<OporEntity, Integer>, PoCustomRepository {
}
