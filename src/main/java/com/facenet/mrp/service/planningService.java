package com.facenet.mrp.service;


import com.facenet.mrp.config.PlanningConfigure;
import com.facenet.mrp.service.dto.CreateWoFromMrp;
import com.facenet.mrp.service.dto.WorkOrder;
import com.facenet.mrp.service.dto.mrp.PlanningProductionOrder;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Service
public class planningService {

    private final RestTemplate restTemplate;
    private final PlanningConfigure configure;

    @Value("${planning.api.syncPo}")
    private String apiSyncProductOrder;

    @Value("${planning.api.getWO}")
    private String apiCallWO;

    @Value("${planning.api.getListWo}")
    private String apiListWo;

    @Value("${planning.api.createWo}")
    private String apiCreateWo;

    public planningService(RestTemplate restTemplate, PlanningConfigure configure) {
        this.restTemplate = restTemplate;
        this.configure = configure;
    }

    public Integer callApiPlanning(List<PlanningProductionOrder> donHangArrayList) {
        String accessToken = configure.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        System.out.println("-----------------------donHangArrayList-----------------"+donHangArrayList);
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

    public String callApiCreateWorkOrder(List<CreateWoFromMrp> createWoFromMrps) {
        String accessToken = configure.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        HttpEntity<List<CreateWoFromMrp>> httpEntity = new HttpEntity<>(createWoFromMrps,headers);

        ResponseEntity<String> response = restTemplate.exchange(
            apiCreateWo,
            HttpMethod.POST,
            httpEntity,
            new ParameterizedTypeReference<>() {
            }
        );

        return response.getBody();
    }

    public List<WorkOrder> callApiFindPlanningWorkOrder(String productOrder) {
        String accessToken = configure.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        String callWo = apiListWo+productOrder;
        ResponseEntity<List<WorkOrder>> response = restTemplate.exchange(
            callWo,
            HttpMethod.GET,
            httpEntity,
            new ParameterizedTypeReference<>() {
            }
        );

        return response.getBody();
    }
}
