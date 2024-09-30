package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.AnalysisDetailReportService;
import com.facenet.mrp.service.ReportService;
import com.facenet.mrp.service.dto.*;
import com.facenet.mrp.service.dto.mrp.AnalysisDetailReportFilter;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportResource {

    private static final Logger logger = LogManager.getLogger(ReportResource.class);

    private final ReportService reportService;

    private final AnalysisDetailReportService detailReportService;

    private final ComparisonReportService comparisonReportService;

    public ReportResource(ReportService reportService, AnalysisDetailReportService detailReportService, ComparisonReportService comparisonReportService) {
        this.reportService = reportService;
        this.detailReportService = detailReportService;
        this.comparisonReportService = comparisonReportService;
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('BC','REPORTSYNTHETIC','VIEW','REPORTPERIOD')")
    public ResponseEntity getReport(@RequestBody ReportFilter filter){

        return ResponseEntity.ok(new CommonResponse<List<ReportDTO>>()
            .errorCode("00")
            .isOk(true)
            .message("Thành công")
            .data(reportService.getReport(filter))
        );
    }

    @PostMapping("report-detail/{reportMode}/{soCode}")
    public ResponseEntity getReport(@PathVariable("reportMode")Integer reportMode, @PathVariable("soCode")String soCode, @RequestBody DetailReportDTO reportDetailDTO){
        return ResponseEntity.ok(new CommonResponse<List<ReportDetailDTO>>()
            .isOk(true)
            .message("Thành công")
            .errorCode("00")
            .data(reportService.getDetailReport(reportMode, soCode, reportDetailDTO))
        );
    }

    @PostMapping("/analysis-detail-report")
    @PreAuthorize("hasAnyAuthority('BC')")
    public ResponseEntity getAnalysisDetailReport(@RequestBody AnalysisDetailReportFilter filter){
        return ResponseEntity.ok(new CommonResponse<List<ReportDTO>>()
            .errorCode("00")
            .isOk(true)
            .message("Thành công")
            .data(detailReportService.getAnalysisDetailReport(filter)));
    }

    @PostMapping("/compare-report")
    @PreAuthorize("hasAnyAuthority('BC')")
    public CommonResponse<ReportComparisonDTO> getComparisonReport(@RequestBody ComparisonReportFilter filter) throws JsonProcessingException {
        return comparisonReportService.getComparisonReport(filter);
    }

    @PostMapping("/report-item")
    public CommonResponse reportItemPO(@RequestBody PageFilterInput<ItemFilter> filterInput){
        return reportService.getAllItemReport(filterInput);
    }

//    @PostMapping(value="/to-excel-so", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public ResponseEntity<byte[]> exportToExcelSO(@RequestBody PageFilterInput<ReportXPDTO> input) {
//        try {
//            Pageable pageable = input.getPageSize() == 0
//                ? PageRequest.of(0, Integer.MAX_VALUE)
//                : PageRequest.of(input.getPageNumber(), input.getPageSize());
//
//            byte[] excelData = reportService.exportSOReportToExcel(input, pageable);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
//            String filename = "BÁO_CÁO_TỔNG_HỢP_MRP.xlsx";
//            headers.setContentDispositionFormData(filename, filename);
//            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(("Error occurred during export: " + e.getMessage()).getBytes());
//        }
//    }
}
