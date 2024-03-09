package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.ForecastService;
import com.facenet.mrp.service.dto.mrp.ForecastDTO;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.PageFilterInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/forecast")
public class ForecastResource {

    @Autowired
    ForecastService forecastService;

    @PostMapping("/search")
    public PageResponse search(@RequestBody PageFilterInput<ForecastDTO> input){
        return forecastService.searchForecast(input);
    }

    @PostMapping("/createOrUpdate")
    public PageResponse createOrUpdate(@RequestBody ForecastDTO input){
        return forecastService.createOrUpdate(input);
    }
}
