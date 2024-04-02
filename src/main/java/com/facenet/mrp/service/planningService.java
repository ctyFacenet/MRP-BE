package com.facenet.mrp.service;


import com.facenet.mrp.config.PlanningConfigure;
import com.facenet.mrp.service.dto.mrp.PlanningProductionOrder;
import com.facenet.mrp.service.dto.response.CommonResponse;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class planningService {

    private final RestTemplate restTemplate;
    private final PlanningConfigure configure;

    @Value("${planning.api.syncPo}")
    private String apiSyncProductOrder;

    public planningService(RestTemplate restTemplate, PlanningConfigure configure) {
        this.restTemplate = restTemplate;
        this.configure = configure;
    }

    public Integer callApiPlanning(List<PlanningProductionOrder> donHangArrayList) {
        String accessToken = configure.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<List<PlanningProductionOrder>> httpEntity = new HttpEntity<>(donHangArrayList,headers);

        ResponseEntity<String> response = restTemplate.exchange(
            apiSyncProductOrder,
            HttpMethod.POST,
            httpEntity,
            new ParameterizedTypeReference<>() {
            }
        );

        if (response.getBody().equals("SUCCESS")) {
            return 1;
        }
        return 0;
    }
}
