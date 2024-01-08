package com.facenet.mrp.service;

import org.springframework.http.ResponseEntity;

public interface AutoCompleteService {
    ResponseEntity<?> suggestProductOrderFields(String poCode, String customerCode, String customerName, String poType, String salesCode, String salesName);
    ResponseEntity<?> suggestProductOrderDetailFields(String productCode, String productName, String supplyMethod, String bomVersion);
}
