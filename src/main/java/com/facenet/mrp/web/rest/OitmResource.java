package com.facenet.mrp.web.rest;

import com.facenet.mrp.domain.sap.OitmEntity;
import com.facenet.mrp.repository.sap.OitmRepository;
import com.facenet.mrp.security.SecurityUtils;
import com.facenet.mrp.service.OitmService;
import com.facenet.mrp.service.WarehouseService;
import com.facenet.mrp.service.dto.OitmDTO;
import com.facenet.mrp.service.dto.mrp.WarehouseEntityDto;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.OitmMapper;
import com.facenet.mrp.service.model.OitmFilter;
import com.facenet.mrp.service.model.RequestInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class OitmResource {

    @Autowired
    OitmService oitmService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    OitmMapper oitmMapper;

    @Autowired
    OitmRepository oitmRepository;

//    @PostMapping("/in-stock-products")
//    public ResponseEntity getOitm(@RequestBody RequestInput<OitmFilter> requestInput){
//        if ("p".equals(requestInput.getFilter().getSapType())) {
//            if (!SecurityUtils.hasCurrentUserAnyOfAuthorities("DHSX", "KHDH", "K", "TK", "TKKT", "HT", "MH", "INVENTORY", "VIEW"))
//                throw new CustomException(HttpStatus.UNAUTHORIZED, "access.denied");
//        } else {
//            if (!SecurityUtils.hasCurrentUserAnyOfAuthorities("DHSX", "KHDH", "TK", "QLSX", "HT", "MH","INVENTORY", "VIEW"))
//                throw new CustomException(HttpStatus.UNAUTHORIZED, "access.denied");
//        }
//        Page<OitmEntity> oitmEntityList = oitmService.getOitmList(requestInput);
//        List<OitmDTO> result = oitmMapper.mapList(oitmEntityList.getContent());
//        return ResponseEntity.ok(new PageResponse<List<OitmDTO>>()
//            .errorCode("00")
//            .message("Success")
//            .isOk(true)
//            .dataCount(oitmEntityList.getTotalElements())
//            .data(result));
//    }

    @PostMapping("/in-stock-products")
    public ResponseEntity<PageResponse<List<OitmDTO>>> getOitmWithWarehouseStock(@RequestBody RequestInput<OitmFilter> requestInput) {
        if ("p".equals(requestInput.getFilter().getSapType())) {
            if (!SecurityUtils.hasCurrentUserAnyOfAuthorities("DHSX", "KHDH", "K", "TK", "TKKT", "HT", "MH", "INVENTORY", "VIEW")) {
                throw new CustomException(HttpStatus.UNAUTHORIZED, "access.denied");
            }
        } else {
            if (!SecurityUtils.hasCurrentUserAnyOfAuthorities("DHSX", "KHDH", "TK", "QLSX", "HT", "MH", "INVENTORY", "VIEW")) {
                throw new CustomException(HttpStatus.UNAUTHORIZED, "access.denied");
            }
        }

        // Fetch the OitmDTO list
        Page<OitmEntity> oitmEntities = oitmService.getOitmList(requestInput);
        List<OitmDTO> oitmDTOList = oitmMapper.mapList(oitmEntities.getContent());

        // Get a list of productIds (itemCodes) from the OitmDTO list
        List<String> productIds = oitmDTOList.stream()
            .map(OitmDTO::getProductId)
            .collect(Collectors.toList());

        // Fetch warehouse data for these productIds
        List<WarehouseEntityDto> warehouseEntities = warehouseService.getAllByItemCodes(productIds);

        // Aggregate total stock from warehouse for each productId
        Map<String, Long> totalStockByProductId = warehouseEntities.stream()
            .collect(Collectors.groupingBy(
                WarehouseEntityDto::getItemCode,
                Collectors.summingLong(WarehouseEntityDto::getRemain)
            ));

        // Update the OitmDTO with the aggregated total stock
        oitmDTOList.forEach(oitmDTO -> {
            Long totalStock = totalStockByProductId.getOrDefault(oitmDTO.getProductId(), 0L);
            oitmDTO.setTotalInStock(oitmDTO.getTotalInStock() + totalStock);
        });

        return ResponseEntity.ok(new PageResponse<List<OitmDTO>>()
            .errorCode("00")
            .message("Success")
            .isOk(true)
            .dataCount(oitmEntities.getTotalElements())
            .data(oitmDTOList));
    }
    @PostMapping("/in-stock-products-v2")
    public ResponseEntity getOitmV2(@RequestBody RequestInput<OitmFilter> requestInput){
        if ("p".equals(requestInput.getFilter().getSapType())) {
            if (!SecurityUtils.hasCurrentUserAnyOfAuthorities("DHSX", "KHDH", "K", "TK", "TKKT", "HT", "MH"))
                throw new CustomException(HttpStatus.UNAUTHORIZED, "access.denied");
        } else {
            if (!SecurityUtils.hasCurrentUserAnyOfAuthorities("DHSX", "KHDH", "TK", "QLSX", "HT", "MH"))
                throw new CustomException(HttpStatus.UNAUTHORIZED, "access.denied");
        }
        Page<OitmEntity> oitmEntityList = oitmRepository.search(requestInput.getFilter().getProductId()
            ,requestInput.getFilter().getName(),
            PageRequest.of(requestInput.getPageNumber(),requestInput.getPageSize()));
        List<OitmDTO> result = oitmMapper.mapList(oitmEntityList.getContent());
        return ResponseEntity.ok(new PageResponse<List<OitmDTO>>()
            .errorCode("00")
            .message("Success")
            .isOk(true)
            .dataCount(oitmEntityList.getTotalElements())
            .data(result));
    }

    @GetMapping("/item-info/{itemCode}")
    public CommonResponse getItemInfo(@PathVariable String itemCode) {
        return oitmService.getItemInfo(itemCode);
    }
}
