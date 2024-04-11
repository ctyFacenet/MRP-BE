package com.facenet.mrp.service;

import com.facenet.mrp.service.dto.scada.ProductionLineDTO;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.dto.scada.*;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.ProductionLineMapper;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.model.ProductionLineFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ProductionLineService {
    private static final Logger logger = LoggerFactory.getLogger(ProductionLineService.class);
    @Value("${scada.auth-url}")
    private String authUrl;

    @Value("${scada.all-assets}")
    private String baseAssetsUrl;

    @Value("${scada.asset-detail}")
    private String baseAssetDetailUrl;

    @Value("${scada.timeout}")
    private int timeout;

    @Value("${scada.user}")
    private String user;

    @Value("${scada.password}")
    private String password;

    private final ProductionLineMapper productionLineMapper;

    public ProductionLineService(ProductionLineMapper productionLineMapper) {
        this.productionLineMapper = productionLineMapper;
    }

    /**
     * Gọi API Scada lấy thông tin dây chuyền sản xuất, máy móc
     * @param input
     * @return
     * @throws JsonProcessingException
     */
    public PageResponse<List<ProductionLineDTO>> getAllProductionLines(PageFilterInput<ProductionLineFilter> input) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

        HttpEntity<String> header = getHeader(restTemplate);

        if (StringUtils.isEmpty(input.getFilter().getProductionLineName())) input.getFilter().setProductionLineName("");
        if (StringUtils.isEmpty(input.getFilter().getComputingProductivity())) input.getFilter().setComputingProductivity("");
        if (StringUtils.isEmpty(input.getFilter().getAssetType())) input.getFilter().setAssetType("");
        if (StringUtils.isEmpty(input.getFilter().getGroup())) input.getFilter().setGroup("");
        if (StringUtils.isEmpty(input.getFilter().getCycleTime())) input.getFilter().setCycleTime("");
        if (StringUtils.isEmpty(input.getFilter().getLabel())) input.getFilter().setLabel("");
        String assetsUrl = String.format(baseAssetsUrl, input.getPageSize(), input.getPageNumber(), input.getFilter().getProductionLineName(),
            input.getFilter().getAssetType(),
            input.getFilter().getLabel(),
            input.getFilter().getComputingProductivity(),
            input.getFilter().getCycleTime(),
            input.getFilter().getGroup());

        logger.info("Get all assets at {}", assetsUrl);
        ResponseEntity<AssetList> allAsset = restTemplate.exchange(
            assetsUrl,
            HttpMethod.GET,
            header,
            AssetList.class
        );

        List<ProductionLineDTO> assets = Objects.requireNonNull(allAsset.getBody()).getData();
        if (assets.isEmpty()) throw new CustomException("record.notfound");
        logger.info("Start get Asset detail");
        for (ProductionLineDTO asset : assets) {
            String assetDetailUrl = String.format(baseAssetDetailUrl, asset.getId().getActualId());
            logger.info("Get Asset detail for {} at {}", asset.getProductionLineName(), assetDetailUrl);
            ResponseEntity<List<KeyValue>> assetDetail = restTemplate.exchange(
                assetDetailUrl,
                HttpMethod.GET,
                header,
                new ParameterizedTypeReference<>() {
                }
            );
            for (KeyValue keyValue : Objects.requireNonNull(assetDetail.getBody())) {
                switch (keyValue.getKey()) {
                    case "Computing_Productivity":
                        asset.setComputingProductivity(keyValue.getValue());
                        break;
                    case "Line_Type":
                        asset.setGroup(keyValue.getValue());
                        break;
                    case "Cycle_Time":
                        asset.setCycleTime(keyValue.getValue());
                        break;
                }
            }
//            baseAsset.setAssetDetail(assetDetail.getBody());
            asset.setProductionLineCode(asset.getProductionLineName());
        }
        logger.info("End get Asset detail");
        return new PageResponse<List<ProductionLineDTO>>()
            .result("00", "Thành công", true)
            .dataCount(allAsset.getBody().getTotalElements())
            .data(assets);
    }

    /**
     * Tạo header chứa token gọi API Scada
     * @return HTTP Header
     */
    private HttpEntity<String> getHeader(RestTemplate restTemplate) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Authorization", getLoginToken(restTemplate));
        return new HttpEntity<>("body", httpHeaders);
    }

    /**
     * Login lấy Token
     * @return Token
     */
    private String getLoginToken(RestTemplate restTemplate) {
        Map<String, String> loginAccount = new HashMap<>(2);
        loginAccount.put("username", user);
        loginAccount.put("password", password);
        ScadaToken loginInformation = restTemplate.postForObject(
            authUrl,
            loginAccount,
            ScadaToken.class
        );
        if (loginInformation == null || loginInformation.getToken() == null)
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "internal.error");
        return "Bearer " + loginInformation.getToken();
    }

    /**
     * Config timeout cho RestTemplate
     * @return params cho RestTemplate
     */
    private SimpleClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory
            = new SimpleClientHttpRequestFactory();
        //Connect timeout
        clientHttpRequestFactory.setConnectTimeout(timeout);

        //Read timeout
        clientHttpRequestFactory.setReadTimeout(timeout);
        return clientHttpRequestFactory;
    }
}
