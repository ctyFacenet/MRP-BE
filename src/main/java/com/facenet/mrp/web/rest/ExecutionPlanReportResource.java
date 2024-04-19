package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.ExecutionPlanReportService;
import com.facenet.mrp.service.dto.mrp.ExecutionPlanReportDetailEntityDto;
import com.facenet.mrp.service.dto.mrp.ExecutionPlanReportEntityDto;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.model.PageFilterInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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


    //TODO đối chiếu kế hoạch
    @PostMapping(value = "/new")
    public CommonResponse addExecutionPlanReport(@RequestPart("file") MultipartFile file, @RequestPart("entityDto") ExecutionPlanReportEntityDto entityDto) throws IOException {
        return new CommonResponse().success().data(executionPlanReportService.addExecutionPlanReport(file,entityDto));
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
    @GetMapping(value = "/{id}")
    public CommonResponse getExecutionPlanReport(@PathVariable Long id) {
        return executionPlanReportService.getExecution(id);
    }

}
