package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.PromotionService;
import com.facenet.mrp.service.dto.PromotionDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.exception.CustomException;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/vendor-information/unit-price-list")
public class PromotionResource {

    private static final Logger logger = LoggerFactory.getLogger(PromotionResource.class);

    private final PromotionService service;


    public PromotionResource(PromotionService service) {
        this.service = service;
    }

    @PostMapping("/{vendorCode}/{itemCode}/promotion-detail")
    @PreAuthorize("hasAnyAuthority('KHDH', 'MH', 'CATEGORY', 'VIEW','VIEW_S_C')")
    public ResponseEntity getNewestPromotionDetail(
        @PathVariable("vendorCode") String vendorCode,
        @PathVariable("itemCode") String itemCode
    )throws IOException, RuntimeException {

        try {
            PromotionDTO result = service.getNewestPromotionDTo(vendorCode, itemCode);
            if (!ObjectUtils.isEmpty(result)){
                return ResponseEntity.ok(new CommonResponse<PromotionDTO>()
                    .isOk(true)
                    .message("success")
                    .errorCode("00")
                    .data(result));
            }else {
                return ResponseEntity.ok(new CommonResponse<PromotionDTO>()
                    .isOk(false)
                    .message("Không tìm thấy bản ghi")
                    .errorCode("00"));
            }
        }catch (Exception e){
            throw new CustomException("internal.error");
        }

    }

    @PostMapping("/{vendorCode}/{itemCode}/new-promotion")
    @PreAuthorize("hasAnyAuthority('MH')")
    public ResponseEntity createPromotion(
        @PathVariable("vendorCode") String vendorCode,
        @PathVariable("itemCode") String itemCode,
        @RequestBody PromotionDTO dto
    ){
        service.createNewPromotion(vendorCode, itemCode, dto);
        return ResponseEntity.ok(new CommonResponse<>()
            .isOk(true)
            .message("Thêm mới khuyến mại thành công")
            .errorCode("00"));

    }

}
