package com.facenet.mrp.web.rest;

import com.facenet.mrp.domain.sap.OwhsEntity;
import com.facenet.mrp.service.OwhsService;
import com.facenet.mrp.service.dto.response.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/owhs")
public class OwhsResource {
    private final OwhsService owhsService;

    public OwhsResource(OwhsService owhsService) {
        this.owhsService = owhsService;
    }

    @GetMapping("")
    public CommonResponse<List<OwhsEntity>> getWarehousesChosen() {
        return owhsService.getWarehousesChosen();
    }

    @GetMapping("/all")
    public CommonResponse<List<OwhsEntity>> getAllWarehouses() {
        return owhsService.getAllWarehouses();
    }

    @GetMapping("/whs-codes")
    public CommonResponse getAllWhsCode(){
        return new CommonResponse()
            .isOk(true)
            .message("Thành công")
            .errorCode("00")
            .data(owhsService.getAllWhsCode());
    }
}
