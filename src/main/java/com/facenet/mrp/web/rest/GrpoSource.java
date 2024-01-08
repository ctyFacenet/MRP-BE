package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.GrpoService;
import com.facenet.mrp.service.dto.GrpoDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.PageFilterInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class GrpoSource {

    @Autowired
    private GrpoService grpoService;

    @PostMapping("/grpos")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'MH', 'QLSX')")
    public ResponseEntity getAllGrpo(@RequestBody PageFilterInput<GrpoDTO> grpoSearchForm){
        try{
            Pageable page = PageRequest.of(grpoSearchForm.getPageNumber(),grpoSearchForm.getPageSize());
            PageResponse<?> list = grpoService.getAllGrpo(grpoSearchForm,page);
            return ResponseEntity.ok(list);
        }catch (Exception e){
            return ResponseEntity.ok(new CommonResponse<>().result("500","Failed",true));
        }
    }
}
