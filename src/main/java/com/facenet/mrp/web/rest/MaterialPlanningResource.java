package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.MaterialPlanningService;
import com.facenet.mrp.service.dto.mrp.ForecastMaterialDTO;
import com.facenet.mrp.service.dto.mrp.GenForecastCodeDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/forecast-orders")
public class MaterialPlanningResource {

    @Autowired
    private MaterialPlanningService materialPlanningService;

    @PostMapping("/import-forecast")
    public ResponseEntity importForecast(@RequestParam("file") MultipartFile file) throws IOException, ParseException {
        if(file == null){
            throw new CustomException(HttpStatus.BAD_REQUEST,"file.import.empty");
        }
        return materialPlanningService.readForeCast(file.getInputStream());
    }

    @GetMapping("/create-forecast-code")
    public ResponseEntity createForecastCode(){
        return ResponseEntity.ok(materialPlanningService.createForecastCode());
    }

    @PostMapping("/import-forecast-csv")
    public ResponseEntity importForecastCsv(@RequestParam("file") MultipartFile file) throws IOException, ParseException, SQLException, CsvException {
        if(file == null){
            throw new CustomException(HttpStatus.BAD_REQUEST,"file.import.empty");
        }
        return materialPlanningService.readForeCastCsv(file.getInputStream());
    }

    @PostMapping("/save-material-plan")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public ResponseEntity saveMaterialPlan(@RequestBody ForecastMaterialDTO forecastMaterialDTO) throws JsonProcessingException, ParseException {
        materialPlanningService.saveOrUpdateMaterialPlan(forecastMaterialDTO);
        return ResponseEntity.ok(new CommonResponse<>().success());
    }
}
