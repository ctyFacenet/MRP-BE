package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.OitmEntity;
import com.facenet.mrp.service.dto.ListOfUnitPricesDTO;
import com.facenet.mrp.service.model.ListOfUnitPricesFilter;
import com.facenet.mrp.service.model.OitmFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OitmCustomRepository {

    Page<OitmEntity> getOitmList(Pageable pageable,OitmFilter oitmFilter);

    Page<ListOfUnitPricesDTO> getVendorItemFromSap(Pageable pageable, ListOfUnitPricesFilter filter);
}
