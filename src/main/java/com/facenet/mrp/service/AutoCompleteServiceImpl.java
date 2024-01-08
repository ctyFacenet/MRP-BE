package com.facenet.mrp.service;

import com.facenet.mrp.repository.mrp.ProductOrderDetailRepository;
import com.facenet.mrp.repository.mrp.ProductOrderRepository;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AutoCompleteServiceImpl implements AutoCompleteService {
    private final static Logger logger = LoggerFactory.getLogger(AutoCompleteServiceImpl.class);
    private final ProductOrderRepository productOrderRepository;
    private final ProductOrderDetailRepository productOrderDetailRepository;
    @Value("${query.limit.autocomplete-elements}")
    private int limitElements;

    public AutoCompleteServiceImpl(ProductOrderRepository productOrderRepository, ProductOrderDetailRepository productOrderDetailRepository) {
        this.productOrderRepository = productOrderRepository;
        this.productOrderDetailRepository = productOrderDetailRepository;
    }

    /**
     * Get data for product order autocomplete
     * @param poCode Product Order Code/mã PO
     * @param customerCode Customer Code/mã khách hàng
     * @param customerName Customer Name/tên khách hàng
     * @param poType Product Order Type/loại đơn hàng
     * @param salesCode sales code/mã nhân viên sale
     * @param salesName sales name/tên nhân viên sale
     * @return
     */
    public ResponseEntity<?> suggestProductOrderFields(String poCode, String customerCode, String customerName, String poType, String salesCode, String salesName) {
        validateParams(poCode, customerCode, customerName, poType, salesCode, salesName);
        Pageable limit = PageRequest.of(0, limitElements);
        try {
            Map<String, List<String>> result = new HashMap<>(1);
            if (poCode != null)
                result.put("productCode", productOrderRepository.getAllPOCode(poCode, limit));
            if (customerCode != null)
                result.put("customerCode", productOrderRepository.getAllCustomerId(customerCode, limit));
            if (customerName != null)
                result.put("customerNames", productOrderRepository.getAllCustomerName(customerName, limit));
            if (poType != null)
                result.put("poTypes", productOrderRepository.getAllPOType(poType, limit));
            if (salesCode != null)
                result.put("salesCodes", productOrderRepository.getAllSalesCode(salesCode, limit));
            if (salesName != null)
                result.put("salesNames", productOrderRepository.getAllSalesName(salesName, limit));
            return ResponseEntity.ok(
                new CommonResponse<>()
                    .errorCode("00")
                    .message("Thành công")
                    .isOk(true)
                    .data(result)
            );
        } catch (Exception e) {
            logger.error("Error when query data for product order autocomplete", e);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "internal.error");
        }
    }

    /**
     * Get data for product order detail autocomplete
     * @param productCode Product Code/mã sản phẩm
     * @param productName Product Name/tên sản phẩm
     * @param supplyMethod Supply Type/phương thức cung ứng
     * @param bomVersion Bom Version
     * @return
     */
    public ResponseEntity<?> suggestProductOrderDetailFields(String productCode, String productName, String supplyMethod, String bomVersion) {
        validateParams(productCode, productName, supplyMethod, bomVersion);
        Pageable limit = PageRequest.of(0, limitElements);
        try {
            Map<String, List<String>> result = new HashMap<>(1);
            if (productCode != null)
                result.put("productCodes", productOrderDetailRepository.getAllProductCodes(productCode, limit));
            if (productName != null)
                result.put("productNames", productOrderDetailRepository.getAllProductNames(productName, limit));
            if (supplyMethod != null)
                result.put("supplyMethods", productOrderDetailRepository.getAllSupplyTypes(supplyMethod, limit));
            if (bomVersion != null)
                result.put("bomVersions", productOrderDetailRepository.getAllBomVersion(bomVersion, limit));
            return ResponseEntity.ok(
                new CommonResponse<>()
                    .errorCode("00")
                    .message("Thành công")
                    .isOk(true)
                    .data(result)
            );
        } catch (Exception e) {
            logger.error("Error when query data for product order detail autocomplete", e);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "internal.error");
        }
    }

    private void validateParams(String... params) {
        int numberOfValidParams = 0;
        for (String param : params) {
            if (param != null) numberOfValidParams++;
        }
        if (numberOfValidParams != 1) {
            logger.info("Invalid params, number of params passed is {}", numberOfValidParams);
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.param");
        }
    }
}
