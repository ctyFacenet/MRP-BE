package com.facenet.mrp.web.rest;

import com.facenet.mrp.domain.sap.OitmEntity;
import com.facenet.mrp.repository.sap.OcrdRepository;
import com.facenet.mrp.security.SecurityUtils;
import com.facenet.mrp.service.OcrdService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OcrdResource {

    @Autowired
    OcrdService ocrdService;

    @GetMapping("/vendor-name/{vendorCode}")
    public CommonResponse getVendorName(@PathVariable String vendorCode){
        return ocrdService.getVendorName(vendorCode);
    }
}
