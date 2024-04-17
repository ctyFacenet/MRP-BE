package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.ExecutionPlanReportService;
import com.facenet.mrp.service.dto.mrp.ExecutionPlanReportDetailEntityDto;
import com.facenet.mrp.service.dto.mrp.ExecutionPlanReportEntityDto;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.model.PageFilterInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/execution-plan-report-compare")
public class ExecutionPlanReportResource {

    @Autowired
    ExecutionPlanReportService executionPlanReportService;

    // Load All
    @PostMapping(value = "")
    public CommonResponse getExecutionPlanReport(@RequestBody PageFilterInput<ExecutionPlanReportEntityDto> input) {
        return new CommonResponse().success().data(executionPlanReportService.getExecutionPlanReport(input));
    }


    @PostMapping(value = "/new")
    public CommonResponse addExecutionPlanReport(@RequestBody ExecutionPlanReportEntityDto entityDto) {
        return new CommonResponse().success().data(executionPlanReportService.addExecutionPlanReport(entityDto));
    }

    @DeleteMapping(value = "/{id}")
    public CommonResponse deleteExecutionPlanReport(@PathVariable Long id) {
        executionPlanReportService.deleteExecutionPlanReport(id);
        return new CommonResponse().success();
    }

    @PostMapping(value = "execution-plan-report-detail-compare/")
    public CommonResponse getExecutionPlanReportDetail(@RequestBody PageFilterInput<ExecutionPlanReportDetailEntityDto> input) {
        return new CommonResponse().success().data(executionPlanReportService.getExecutionPlanReportDetail(input));
    }

}
