package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.AnalysisDetailReportService;
import com.facenet.mrp.service.ReportService;
import com.facenet.mrp.service.dto.ReportComparisonDTO;
import com.facenet.mrp.service.dto.ReportDTO;
import com.facenet.mrp.service.dto.ReportDetailDTO;
import com.facenet.mrp.service.dto.mrp.AnalysisDetailReportFilter;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    public ResponseEntity getReport(@PathVariable("reportMode")Integer reportMode, @PathVariable("soCode")String soCode){
        return ResponseEntity.ok(new CommonResponse<List<ReportDetailDTO>>()
            .isOk(true)
            .message("Thành công")
            .errorCode("00")
            .data(reportService.getDetailReport(reportMode, soCode))
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
    public CommonResponse<ReportComparisonDTO> getComparisonReport(@RequestBody ComparisonReportFilter filter){
        return comparisonReportService.getComparisonReport(filter);
    }

    @PostMapping("/report-item")
    public CommonResponse reportItemPO(@RequestBody PageFilterInput<ItemFilter> filterInput){
        return reportService.getAllItemReport(filterInput);
    }
}
