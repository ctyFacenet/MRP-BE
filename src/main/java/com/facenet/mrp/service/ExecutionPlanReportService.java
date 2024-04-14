package com.facenet.mrp.service;

import com.facenet.mrp.repository.mrp.ExecutionPlanReportDetailEntityRepository;
import com.facenet.mrp.repository.mrp.ExecutionPlanReportDetailQuantityEntityRepository;
import com.facenet.mrp.repository.mrp.ExecutionPlanReportEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutionPlanReportService {

    @Autowired
    private ExecutionPlanReportDetailEntityRepository executionPlanReportDetailEntityRepository;
    @Autowired
    private ExecutionPlanReportDetailQuantityEntityRepository executionPlanReportDetailQuantityEntityRepository;
    @Autowired
    private ExecutionPlanReportEntityRepository executionPlanReportEntityRepository;


    void getExecutionPlanReport() {

    }

    void addExecutionPlanReport() {

    }

    void deleteExecutionPlanReport() {

    }

    void viewDetail() {

    }

}
