package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.ProductOrderDetailService;
import com.facenet.mrp.service.ProductOrderService;
import com.facenet.mrp.service.dto.ProductOrderDetailDto;
import com.facenet.mrp.service.dto.request.SendAnalysisRequest;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.ProductOrderDetailInput;
import com.facenet.mrp.service.model.ProductOrderDetailResponse;
import org.apache.el.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductOrderDetailResource {

    private static final Logger logger = LoggerFactory.getLogger(ProductOrderService.class);

    private final ProductOrderDetailService productOrderDetailService;

    public ProductOrderDetailResource(ProductOrderDetailService productOrderDetailService) {
        this.productOrderDetailService = productOrderDetailService;
    }

    @PostMapping("product-order-detail/{productOrderCode}")
    @PreAuthorize("hasAnyAuthority('DHSX','VIEW','SELLORDER')")
    public ResponseEntity<ProductOrderDetailResponse> getAllPoDetail(
        @PathVariable("productOrderCode") String productOrderCode,
        @RequestBody ProductOrderDetailInput input
        ) throws ParseException, IOException {

        if (productOrderCode == null || input == null){
            throw new CustomException(HttpStatus.BAD_REQUEST,"invalid.param");
        }

        ProductOrderDetailResponse response = productOrderDetailService.getAllProductDetail(productOrderCode, input);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("product-order-detail/new-product-order-detail/{productOrderCode}")
    public ResponseEntity createNewProductOrderDetail(
        @PathVariable("productOrderCode")String productOrderCode,
        @RequestBody ProductOrderDetailDto dto
    )throws IOException, ParseException{

        if (productOrderCode == null || dto == null){
            throw new CustomException(HttpStatus.BAD_REQUEST,"invalid.param");
        }
        productOrderDetailService.createNewProductOrderDetail(productOrderCode, dto);
        return ResponseEntity.ok(
            new CommonResponse<>()
                .isOk(true)
                .errorCode("200")
                .message("Thêm mới sản phẩm thành công")
        );
    }

    @PutMapping("product-order-detail/updated-detail/{productOrderCode}/products/{productCode}/{isSend}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'S')")
    public ResponseEntity updateProductOrderDetail (
        @PathVariable("productCode")String productCode,
        @PathVariable("productOrderCode")String productOrderCode,
        @RequestBody ProductOrderDetailDto dto,
        @PathVariable Boolean isSend
    ) {
        if (productCode == null || dto == null){
            throw new CustomException(HttpStatus.BAD_REQUEST,"invalid.param");
        }
        productOrderDetailService.updateProductOrderDetail(productCode, productOrderCode, dto,isSend);
        return ResponseEntity.ok(
            new CommonResponse<>()
                .isOk(true)
                .errorCode("200")
                .message("Cập nhật Sản phẩm thành công")
        );
    }


    @PutMapping("product-order-detail/syncItem/{productOrderCode}/products/{productCode}/{isSend}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'S')")
    public ResponseEntity syncProductOrderDetail (
        @PathVariable("productCode")String productCode,
        @PathVariable("productOrderCode")String productOrderCode,
        @RequestBody ProductOrderDetailDto dto,
        @PathVariable Boolean isSend
    ) {
        if (productCode == null || dto == null){
            throw new CustomException(HttpStatus.BAD_REQUEST,"invalid.param");
        }
        productOrderDetailService.syncItemProductOrderDetail(productCode, productOrderCode, dto,isSend);
        return ResponseEntity.ok(
            new CommonResponse<>()
                .isOk(true)
                .errorCode("200")
                .message("Cập nhật Sản phẩm thành công")
        );
    }


    @PutMapping("/product-orders/{productOrderCode}/product-order-detail/new-status/{productCode}/{status}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'S')")
    public ResponseEntity updateStatusProductInPO(@PathVariable String productOrderCode,@PathVariable String productCode,@PathVariable Byte status){
        Integer in = productOrderDetailService.updateStatusProductInPO(productCode,status,productOrderCode);
        if(in == -1){
            return ResponseEntity.ok(
                new CommonResponse<>().isOk(false).message("yêu cầu không hợp lệ").errorCode("400"));
        }
        if(in == -2){
            return ResponseEntity.ok(
                new CommonResponse<>().isOk(false).message("không có sản phẩm này").errorCode("404"));
        }
        return ResponseEntity.ok(
            new CommonResponse<>().isOk(true).message("chuyển trạng thái thành công").errorCode("00"));
    }

    @PutMapping("/product-orders/product-order-detail/new-status/list")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'S')")
    public ResponseEntity updateStatusListProductInPO(@RequestBody List<SendAnalysisRequest> requestList){
        Integer in = productOrderDetailService.updateStatusListProductInPO(requestList);
        if(in == -1){
            return ResponseEntity.ok(
                new CommonResponse<>().isOk(false).message("yêu cầu không hợp lệ").errorCode("400"));
        }
        if(in == -2){
            return ResponseEntity.ok(
                new CommonResponse<>().isOk(false).message("không có sản phẩm này").errorCode("404"));
        }
        return ResponseEntity.ok(
            new CommonResponse<>().isOk(true).message("chuyển trạng thái thành công").errorCode("00"));
    }

    @PutMapping("/product-orders/{productOrderCode}/product-order-detail/{productCode}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public ResponseEntity deleteProductInPO(@PathVariable String productOrderCode,@PathVariable String productCode){
        Integer in = productOrderDetailService.deleteProductInPO(productOrderCode,productCode);
        if(in == -1){
            return ResponseEntity.ok(
                new CommonResponse<>().isOk(false).message("yêu cầu không hợp lệ").errorCode("400"));
        }
        if(in == -2){
            return ResponseEntity.ok(
                new CommonResponse<>().isOk(false).message("không có sản phẩm này").errorCode("404"));
        }
        return ResponseEntity.ok(
            new CommonResponse<>().isOk(true).message("xóa sản phẩm thành công").errorCode("00"));
    }

    @PostMapping("product-order-detail/analytics-products/{productOrderCode}/{type}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'MH', 'QLSX','LISTSCRIPT','VIEW')")
    public ResponseEntity<ProductOrderDetailResponse> getAllAnalyticsPoDetail(
        @PathVariable("productOrderCode")String productOrderCode,
        @RequestBody ProductOrderDetailInput input,
        @PathVariable("type") Integer type
    ) throws ParseException, IOException {

        if (productOrderCode == null || input == null){
            throw new CustomException(HttpStatus.BAD_REQUEST,"invalid.param");
        }

        ProductOrderDetailResponse response = productOrderDetailService.getAllAnalyticsProduct(productOrderCode, input,type);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
