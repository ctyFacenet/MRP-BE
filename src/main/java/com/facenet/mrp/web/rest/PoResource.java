package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.PoService;
import com.facenet.mrp.service.dto.PoDto;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.PoFilter;
import com.facenet.mrp.service.model.RequestInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PoResource {

    @Autowired
    private PoService poService;

    @PostMapping("/purchase-orders")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'MH', 'QLSX')")
    public ResponseEntity getPO(@RequestBody RequestInput<PoFilter> requestInput){
        Page<PoDto> dtos = poService.poDtoPage(requestInput);
        return ResponseEntity.ok(new PageResponse<List<PoDto>>()
            .errorCode("00")
            .message("Success").isOk(true)
            .dataCount(dtos.getTotalElements())
            .data(dtos.getContent())
        );

    }
}
