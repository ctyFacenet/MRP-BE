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

    @Value("${planning.api.updatePo}")
    private String apiUpdateProductOrder;

    @Value("${planning.api.deletePo}")
    private String apiDeleteProductOrder;

    public planningService(RestTemplate restTemplate, PlanningConfigure configure) {
        this.restTemplate = restTemplate;
        this.configure = configure;
    }

    public String callApiPlanning(List<List<PlanningProductionOrder>> donHangArrayList,Boolean type) {
        String accessToken = configure.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        String apiSyncPo = apiSyncProductOrder+type;
        System.out.println(apiSyncPo+"-----------------------donHangArrayList-----------------"+donHangArrayList);
        System.out.println("api: "+apiSyncPo);
        System.out.println("token:  "+accessToken);
        HttpEntity<List<List<PlanningProductionOrder>>> httpEntity = new HttpEntity<>(donHangArrayList,headers);
        ResponseEntity<String> response = restTemplate.exchange(
            apiSyncPo,
            HttpMethod.POST,
            httpEntity,
            new ParameterizedTypeReference<>() {
            }
        );

        return response.getBody();
    }

    public String callApiPlanningUpdatePo(PlanningProductionOrder donHangArrayList,String productCode,Boolean isSend) {
        String accessToken = configure.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        String api = apiUpdateProductOrder+isSend+"/code="+productCode;
        System.out.println("test api: "+api);
        HttpEntity<PlanningProductionOrder> httpEntity = new HttpEntity<>(donHangArrayList,headers);
        ResponseEntity<String> response = restTemplate.exchange(
            api,
            HttpMethod.POST,
            httpEntity,
            new ParameterizedTypeReference<>() {
            }
        );

        return response.getBody();
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

    public String callApiDeletePo(String orderCode, String productCode, Boolean type) {
        String accessToken = configure.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String api = apiDeleteProductOrder+ orderCode + "/productCode="+productCode+"/type="+type;
        System.out.println("--------api xóa đơn hàng "+api);
        ResponseEntity<String> response = restTemplate.exchange(
            api,
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
        String chuoiCanXoa = "RAL-SO-";

        // Sử dụng phương thức replace() để xóa chuỗi con
        String newProductOrder = productOrder.replace(chuoiCanXoa, "");
        String callWo = apiListWo+newProductOrder;
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
