package com.facenet.mrp.web.rest;

import com.facenet.mrp.domain.sap.OitmEntity;
import com.facenet.mrp.repository.sap.OitmRepository;
import com.facenet.mrp.security.SecurityUtils;
import com.facenet.mrp.service.OitmService;
import com.facenet.mrp.service.dto.OitmDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.OitmMapper;
import com.facenet.mrp.service.model.OitmFilter;
import com.facenet.mrp.service.model.RequestInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OitmResource {

    @Autowired
    OitmService oitmService;

    @Autowired
    OitmMapper oitmMapper;

    @Autowired
    OitmRepository oitmRepository;

    @PostMapping("/in-stock-products")
    public ResponseEntity getOitm(@RequestBody RequestInput<OitmFilter> requestInput){
        if ("p".equals(requestInput.getFilter().getSapType())) {
            if (!SecurityUtils.hasCurrentUserAnyOfAuthorities("DHSX", "KHDH", "K", "TK", "TKKT", "HT", "MH", "INVENTORY"))
                throw new CustomException(HttpStatus.UNAUTHORIZED, "access.denied");
        } else {
            if (!SecurityUtils.hasCurrentUserAnyOfAuthorities("DHSX", "KHDH", "TK", "QLSX", "HT", "MH","INVENTORY"))
                throw new CustomException(HttpStatus.UNAUTHORIZED, "access.denied");
        }
        Page<OitmEntity> oitmEntityList = oitmService.getOitmList(requestInput);
        List<OitmDTO> result = oitmMapper.mapList(oitmEntityList.getContent());
        return ResponseEntity.ok(new PageResponse<List<OitmDTO>>()
            .errorCode("00")
            .message("Success")
            .isOk(true)
            .dataCount(oitmEntityList.getTotalElements())
            .data(result));
    }
    @PostMapping("/in-stock-products-v2")
    public ResponseEntity getOitmV2(@RequestBody RequestInput<OitmFilter> requestInput){
        if ("p".equals(requestInput.getFilter().getSapType())) {
            if (!SecurityUtils.hasCurrentUserAnyOfAuthorities("DHSX", "KHDH", "K", "TK", "TKKT", "HT", "MH"))
                throw new CustomException(HttpStatus.UNAUTHORIZED, "access.denied");
        } else {
            if (!SecurityUtils.hasCurrentUserAnyOfAuthorities("DHSX", "KHDH", "TK", "QLSX", "HT", "MH"))
                throw new CustomException(HttpStatus.UNAUTHORIZED, "access.denied");
        }
        Page<OitmEntity> oitmEntityList = oitmRepository.search(requestInput.getFilter().getProductId()
            ,requestInput.getFilter().getName(),
            PageRequest.of(requestInput.getPageNumber(),requestInput.getPageSize()));
        List<OitmDTO> result = oitmMapper.mapList(oitmEntityList.getContent());
        return ResponseEntity.ok(new PageResponse<List<OitmDTO>>()
            .errorCode("00")
            .message("Success")
            .isOk(true)
            .dataCount(oitmEntityList.getTotalElements())
            .data(result));
    }

    @GetMapping("/item-info/{itemCode}")
    public CommonResponse getItemInfo(@PathVariable String itemCode) {
        return oitmService.getItemInfo(itemCode);
    }
}
