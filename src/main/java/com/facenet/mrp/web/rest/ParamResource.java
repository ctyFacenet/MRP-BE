package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.ParamService;
import com.facenet.mrp.service.dto.InputParamDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ParamResource {
    private final ParamService paramService;

    public ParamResource(ParamService paramService) {
        this.paramService = paramService;
    }

    @PostMapping("/params")
    public ResponseEntity<?> getParams(@RequestBody InputParamDto params) {
        return paramService.getParams(params);
    }
}
