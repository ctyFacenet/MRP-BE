package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.PoService;
import com.facenet.mrp.service.ReportService;
import com.facenet.mrp.service.dto.PoDto;
import com.facenet.mrp.service.dto.ReportXPDTO;
import com.facenet.mrp.service.dto.mrp.PurchaseOrderProgressDTO;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PoResource {

    @Autowired
    private PoService poService;
    @Autowired
    private ReportService reportService;

    @PostMapping("/purchase-orders")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'MH', 'QLSX' ,'VIEW','PRPOGRPO')")
    public ResponseEntity getPO(@RequestBody RequestInput<PoFilter> requestInput){
        Page<PoDto> dtos = poService.poDtoPage(requestInput);
        return ResponseEntity.ok(new PageResponse<List<PoDto>>()
            .errorCode("00")
            .message("Success").isOk(true)
            .dataCount(dtos.getTotalElements())
            .data(dtos.getContent())
        );

    }

    @PostMapping("/purchase-orders/report")
    public ResponseEntity<PageResponse<List<ReportXPDTO>>> reportPurchaseOrder(@RequestBody PageFilterInput<ReportXPDTO> body) {
        Pageable pageable = body.getPageSize() == 0 ? Pageable.unpaged() : PageRequest.of(body.getPageNumber(), body.getPageSize());
        PageResponse<List<ReportXPDTO>> result = reportService.getPOReport(body, pageable);
        // Trả về ResponseEntity với PageResponse
        return ResponseEntity.ok(result);
    }

    @PostMapping("/purchase-request/report")
    public ResponseEntity<PageResponse<List<ReportXPDTO>>> reportPurchaseRequest(@RequestBody PageFilterInput<ReportXPDTO> body) {
        Pageable pageable = body.getPageSize() == 0 ? Pageable.unpaged() : PageRequest.of(body.getPageNumber(), body.getPageSize());
        PageResponse<List<ReportXPDTO>> result = reportService.getSOReport(body, pageable);
        // Trả về ResponseEntity với PageResponse
        return ResponseEntity.ok(result);
    }

    @PostMapping(value="/to-excel-so", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportToExcelSO(@RequestBody PageFilterInput<ReportXPDTO> input) {
        try {
            Pageable pageable = input.getPageSize() == 0 ? Pageable.unpaged() : PageRequest.of(input.getPageNumber(), input.getPageSize());
            byte[] excelData = reportService.exportSOReportToExcel(input, pageable);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            String filename = "BÁO_CÁO_TỔNG_HỢP_MRP.xlsx";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(("Error occurred during export: " + e.getMessage()).getBytes());
        }
    }

    @PostMapping(value="/to-excel-order-detail", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportPurchaseByNCCReportToExcel(@RequestBody PageFilterInput<ReportXPDTO> input) {
        try {
            Pageable pageable = input.getPageSize() == 0 ? Pageable.unpaged() : PageRequest.of(input.getPageNumber(), input.getPageSize());
            byte[] excelData = reportService.exportPurchaseByNCCReportToExcel(input, pageable);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            String filename = "BÁO_CÁO_TỔNG_HỢP_MRP.xlsx";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(("Error occurred during export: " + e.getMessage()).getBytes());
        }
    }

    @PostMapping(value="/to-excel-ncc", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportNCCReportToExcel(@RequestBody PageFilterInput<ReportXPDTO> input) {
        try {
            Pageable pageable = input.getPageSize() == 0 ? Pageable.unpaged() : PageRequest.of(input.getPageNumber(), input.getPageSize());
            byte[] excelData = reportService.exportNCCReportToExcel(input, pageable);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            String filename = "BÁO_CÁO_TỔNG_HỢP_MRP.xlsx";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(("Error occurred during export: " + e.getMessage()).getBytes());
        }
    }
}
