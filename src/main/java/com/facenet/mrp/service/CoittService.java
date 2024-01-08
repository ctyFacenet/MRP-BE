package com.facenet.mrp.service;

import com.facenet.mrp.service.dto.DetailBomVersionDTO;
import com.facenet.mrp.service.dto.ListBomDTO;
import com.facenet.mrp.service.dto.ViewBomDTO;
import com.facenet.mrp.service.model.BomFilterInput;
import com.facenet.mrp.service.model.RequestInput;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface CoittService {

    ListBomDTO getListBomAndActive(String productCode);

    ViewBomDTO getDetailBomVersionWithProduct(String productCode, String version);

    Page<DetailBomVersionDTO> listBomDetail(RequestInput<BomFilterInput> requestInput);
}
