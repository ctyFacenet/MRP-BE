package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.WarehouseService;
import com.facenet.mrp.service.dto.mrp.VendorEntityDto;
import com.facenet.mrp.service.dto.mrp.WarehouseEntityDto;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.PageFilterInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @PostMapping("/get-warehouse")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'TK', 'MH','SUPPLIER','VIEW','VIEWSC')")
    public CommonResponse getAllVendors(@RequestBody PageFilterInput<WarehouseEntityDto> input) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            PageResponse<List<WarehouseEntityDto>> result = warehouseService.getAll(input);

            if (result == null) {
                return commonResponse.result("404", "Không tìm thấy tài nguyên!", false);
            }

            return result;
        } catch (Exception e) {
            return commonResponse.result("500", "Có lỗi server!", false);
        }
    }
}
