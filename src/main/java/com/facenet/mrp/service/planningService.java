package com.facenet.mrp.service;


import com.facenet.mrp.config.PlanningConfigure;
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

    public List<WorkOrder> callApiPlanningWorkOrder(String param) {
        String accessToken = configure.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        String filter = URLEncoder.encode(param, StandardCharsets.UTF_8);
        String api = apiCallWO+"view=_base&sort=-createTime&limit=15&offset=0&filter="+filter+"&returnCount=true";
        ObjectMapper objectMapper = new ObjectMapper();
        Object filterObject =new Object();
        try {
            filterObject = objectMapper.readValue(param, Object.class);
            System.out.println(filterObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("_--------------"+api);
        System.out.println(accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ParameterizedTypeReference<List<WorkOrder>> responseType = new ParameterizedTypeReference<List<WorkOrder>>() {};

        Object responseEntity = restTemplate.exchange(
            api,
            HttpMethod.GET,
            httpEntity,
            responseType
        );

        System.out.println(responseEntity);
        return new ArrayList<>();
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
