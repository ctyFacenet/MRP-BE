package com.facenet.mrp.service;

import com.facenet.mrp.service.dto.InputParamDto;
import org.springframework.http.ResponseEntity;

public interface ParamService {
    ResponseEntity<?> getParams(InputParamDto params);
}
