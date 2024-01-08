package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.AutoCompleteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product-orders")
public class AutoCompleteResource {
    private final AutoCompleteService autoCompleteService;

    public AutoCompleteResource(AutoCompleteService autoCompleteService) {
        this.autoCompleteService = autoCompleteService;
    }

    /**
     * Get data for product order autocomplete
     * @param poCode mã đơn hàng
     * @param customerCode mã khách hàng
     * @param customerName tên khách hàng
     * @param poType loại đơn hàng
     * @param salesCode mã sale
     * @param salesName tên của sale
     * @return
     */
    @GetMapping("/autocomplete")
    public ResponseEntity<?> autoCompleteProductOrder(
        @RequestParam(required = false) String poCode,
        @RequestParam(required = false) String customerCode,
        @RequestParam(required = false) String customerName,
        @RequestParam(required = false) String poType,
        @RequestParam(required = false) String salesCode,
        @RequestParam(required = false) String salesName
    ) {
        return autoCompleteService.suggestProductOrderFields(poCode, customerCode, customerName, poType, salesCode,salesName);
    }

    /**
     * Get data for product order autocomplete
     * @param productCode mã sản phẩm
     * @param productName tên sản phẩm
     * @param supplyMethod phương thức cung ứng
     * @param bomVersion bom version
     * @return
     */
    @GetMapping("/products/autocomplete")
    public ResponseEntity<?> autoCompleteProductOrderDetail(
        @RequestParam(required = false) String productCode,
        @RequestParam(required = false) String productName,
        @RequestParam(required = false) String supplyMethod,
        @RequestParam(required = false) String bomVersion
    ) {
        return autoCompleteService.suggestProductOrderDetailFields(productCode, productName, supplyMethod, bomVersion);
    }
}
