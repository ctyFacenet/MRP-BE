package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.ForecastOrderDetailService;
import com.facenet.mrp.service.dto.mrp.ForecastMaterialDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/forecast-orders")
public class ForecastResultResource {

    private static final Logger logger = LoggerFactory.getLogger(ForecastResultResource.class);
    private final ForecastOrderDetailService forecastOrderDetailService;

    public ForecastResultResource(ForecastOrderDetailService forecastOrderDetailService) {
        this.forecastOrderDetailService = forecastOrderDetailService;
    }

    @PostMapping("/forecast-order-plan")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public ResponseEntity getForecastResult(@RequestBody ForecastMaterialDTO ForecastMaterialDTO) throws ParseException {
        return ResponseEntity.ok(
            new CommonResponse<>()
                .isOk(true)
                .message("Thành công")
                .errorCode("00")
                .data(forecastOrderDetailService.creatResultOfForecast(ForecastMaterialDTO))
        );
    }

    @PostMapping("/analytics-forecast")
    public ResponseEntity sentForecastToAnalysis(@RequestBody ForecastMaterialDTO ForecastMaterialDTO) throws ParseException, JsonProcessingException {
        forecastOrderDetailService.sendForecastToAnalysis(ForecastMaterialDTO);
        return ResponseEntity.ok(
            new CommonResponse<>()
                .isOk(true)
                .message("Thành công")
                .errorCode("00")
        );
    }
}
