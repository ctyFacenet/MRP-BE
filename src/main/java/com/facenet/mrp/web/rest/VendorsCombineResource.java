package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.VendorsCombineService;
import com.facenet.mrp.service.dto.VendorsCombineEntityDto;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.model.PageFilterInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/vendors-combine")
public class VendorsCombineResource {
    @Autowired
    private VendorsCombineService vendorsCombineService;

    // API: Lấy danh sách vendors với phân trang và lọc động
    @PostMapping("/get-vendors")
    public CommonResponse<Page<VendorsCombineEntityDto>> getAllVendors(@RequestBody PageFilterInput<VendorsCombineEntityDto> input) {
        Page<VendorsCombineEntityDto> result = vendorsCombineService.getAllVendors(input);
        return new CommonResponse<>().success().data(result);
    }

    // API: Thêm mới vendor
    @PostMapping
    public ResponseEntity<VendorsCombineEntityDto> createVendor(@RequestBody VendorsCombineEntityDto vendorsCombineEntityDto) {
        VendorsCombineEntityDto createdVendor = vendorsCombineService.createVendor(vendorsCombineEntityDto);
        return new ResponseEntity<>(createdVendor, HttpStatus.CREATED);
    }

    // API: Cập nhật vendor
    @PutMapping("/{id}")
    public ResponseEntity<VendorsCombineEntityDto> updateVendor(
        @PathVariable int id,
        @RequestBody VendorsCombineEntityDto vendorsCombineEntityDto) {
        Optional<VendorsCombineEntityDto> updatedVendor = vendorsCombineService.updateVendor(id, vendorsCombineEntityDto);
        return updatedVendor
            .map(vendor -> new ResponseEntity<>(vendor, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/sync")
    public ResponseEntity<String> syncVendorsFromSap() {
        try {
            // Gọi hàm đồng bộ từ service
            vendorsCombineService.syncVendorsFromSap();
            return ResponseEntity.ok("Đồng bộ dữ liệu thành công từ SAP.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi đồng bộ dữ liệu từ SAP: " + e.getMessage());
        }
    }
}
