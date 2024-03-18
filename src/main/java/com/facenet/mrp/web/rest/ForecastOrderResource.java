package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.ForecastOrderService;
import com.facenet.mrp.service.dto.ForecastOrderDTO;
import com.facenet.mrp.service.dto.mrp.ForecastMaterialDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.model.ProductOrderDetailInput;
import com.facenet.mrp.service.model.ProductOrderDetailResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.el.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/forecast-orders")
public class ForecastOrderResource {
    private final ForecastOrderService forecastOrderService;

    public ForecastOrderResource(ForecastOrderService forecastOrderService) {
        this.forecastOrderService = forecastOrderService;
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'TK', 'QLSX', 'HT', 'K','PLANITEM')")
    public PageResponse<List<ForecastOrderDTO>> getAllForecastOrder(@RequestBody PageFilterInput<ForecastOrderDTO> input) {
        return forecastOrderService.getAllForecastOrder(input);
    }

    @DeleteMapping("/{fcCode}")
    public ResponseEntity deleteForecastOrder(@PathVariable String fcCode) {
        String response =forecastOrderService.deleteForecastOrder(fcCode);
        if(response == null){
            return ResponseEntity.ok(
                new CommonResponse<>()
                    .isOk(false)
                    .errorCode("404")
                    .message("Không tồn tại bản ghi")
            );
        }
        return ResponseEntity.ok(
            new CommonResponse<>()
                .isOk(false)
                .success("00")
                .message("Đã xóa!")
        );
    }

    @GetMapping("/get-detail-forecast/{fcCode}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'TK', 'TKKT', 'QLSX', 'HT', 'K')")
    public ResponseEntity getDetailForecast(@PathVariable String fcCode) throws JsonProcessingException {
        ForecastMaterialDTO forecastMaterialDTO = forecastOrderService.getDetailForecast(fcCode);
        if(forecastMaterialDTO == null){
            return ResponseEntity.ok(
                new CommonResponse<>()
                    .isOk(false)
                    .errorCode("404")
                    .message("Không tìm thấy bản ghi")
            );
        }
        return ResponseEntity.ok(
            new CommonResponse<>()
                .isOk(true)
                .success("200")
                .data(forecastMaterialDTO)
        );
    }

    @PostMapping("/list-item-fc/{productOrderCode}")
    public ResponseEntity<ProductOrderDetailResponse> getListItem(
        @PathVariable("productOrderCode")String productOrderCode,
        @RequestBody ProductOrderDetailInput input) throws IOException, ParseException {
        ProductOrderDetailResponse response = forecastOrderService.getAllAnalyticsForecast(productOrderCode,input);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
