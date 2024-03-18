package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.ListOfUnitPricesService;
import com.facenet.mrp.service.dto.ListOfUnitPricesDTO;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.ListOfUnitPricesFilter;
import com.facenet.mrp.service.model.RequestInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-information")
public class ListOfUnitPricesResource {

    private static final Logger logger = LoggerFactory.getLogger(ListOfUnitPricesResource.class);

    private final ListOfUnitPricesService service;

    public ListOfUnitPricesResource(ListOfUnitPricesService service) {
        this.service = service;
    }

    @PostMapping("/unit-price-list")
    @PreAuthorize("hasAnyAuthority('KHDH', 'MH','CATEGORY')")
    public ResponseEntity getAllListOfUnitPrices(@RequestBody RequestInput<ListOfUnitPricesFilter> input){
        Page<ListOfUnitPricesDTO> pageResult = service.getPageListOfUnitPrices(input);
        return ResponseEntity.ok(new PageResponse<List<ListOfUnitPricesDTO>>()
            .isOk(true)
            .message("success")
            .errorCode("00")
            .dataCount(pageResult.getTotalElements())
            .data(pageResult.getContent())
        );
    }

}
