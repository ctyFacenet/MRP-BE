package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseResource {
    @Autowired
    private WarehouseService warehouseService;
    @PostMapping("/import")
    public ResponseEntity<String> importWarehouseData(
        @RequestParam("file") MultipartFile file,
        @RequestParam("type") Integer type) {
        try {
            warehouseService.importExcelData(file, type);
            return ResponseEntity.ok("File imported successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error importing file: " + e.getMessage());
        }
    }
}
