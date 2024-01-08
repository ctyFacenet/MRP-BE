package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.MrpBomDetailService;
import com.facenet.mrp.service.ProductOrderAnalyticService;
import com.facenet.mrp.service.dto.ProductOrderDto;
import com.facenet.mrp.service.dto.ProductOrderItemsDTO;
import com.facenet.mrp.service.dto.request.AddItemToBomRequest;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.ProductOrderInput;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-analytics")
public class ProductOrderAnalyticsResource {

    @Autowired
    ProductOrderAnalyticService productOrderAnalyticService;
    @Autowired
    private MrpBomDetailService mrpBomDetailService;

    @PostMapping("/list-order-analytics")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'MH', 'QLSX')")
    public ResponseEntity listOrder(@RequestBody ProductOrderInput input){
        if (input == null){
            throw new CustomException(HttpStatus.BAD_REQUEST,"invalid.param");
        }
        Page<ProductOrderDto> response = productOrderAnalyticService.getProductOrderAnalytics(input);
        return ResponseEntity.ok(
            new PageResponse<ProductOrderDto>()
                .dataCount(response.getTotalElements())
                .success()
                .data(response.getContent())
        );
    }

    @PostMapping("/detail/add-item")
    public ResponseEntity addItemToBom(@RequestBody AddItemToBomRequest request){
        if(StringUtils.isEmpty(request.getMrpPoId())){
            throw new CustomException("mrp_po_id.can.not.empty");
        }
        if(StringUtils.isEmpty(request.getParentPath())){
            throw new CustomException("parent_path.can.not.empty");
        }
        if(StringUtils.isEmpty(request.getItemCode())){
            throw new CustomException("item_code.can.not.empty");
        }
        if(request.getBasicQuantity() == null){
            throw new CustomException("basic_quantity.can.not.empty");
        }
        mrpBomDetailService.addBomVersionDetail(request);
        return ResponseEntity.ok(new CommonResponse<>().success());
    }

    /**
     * Lấy danh sách sản phẩm trong đơn hàng
     * @param productOrderCode mã đơn hàng
     * @param productCode mã sản phầm nếu có, không có sẽ = null
     * @return
     */
    @GetMapping(value = {"/products/{productOrderCode}", "/products/{productOrderCode}/{productCode}"})
    public CommonResponse<List<ProductOrderItemsDTO>> getAllPoDetail(@PathVariable("productOrderCode") String productOrderCode, @PathVariable(value = "productCode", required = false) String productCode) {
        if (productOrderCode == null) throw new CustomException(HttpStatus.BAD_REQUEST,"invalid.param");
        return productOrderAnalyticService.getAllProductDetail(productOrderCode, productCode);
    }
}
