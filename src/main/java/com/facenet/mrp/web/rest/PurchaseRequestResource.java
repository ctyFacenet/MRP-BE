package com.facenet.mrp.web.rest;

import com.facenet.mrp.domain.mrp.PurchaseRequestDetailEntity;
import com.facenet.mrp.domain.mrp.PurchaseRequestEntity;
import com.facenet.mrp.service.PurchaseRequestService;
import com.facenet.mrp.service.dto.PurchaseRequestDTO;
import com.facenet.mrp.service.dto.PurchaseRequestEntityDto;
import com.facenet.mrp.service.dto.request.PurchaseRequestDetailPagingDTO;
import com.facenet.mrp.service.dto.request.PurchaseRequestPagingDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.PageFilterInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/purchase-request")
public class PurchaseRequestResource {
    private final PurchaseRequestService purchaseRequestService;

    public PurchaseRequestResource(PurchaseRequestService purchaseRequestService) {
        this.purchaseRequestService = purchaseRequestService;
    }

    @PostMapping("/purchase-requests")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'MH', 'QLSX' ,'PRPOGRPO','VIEW')")
    public CommonResponse<?> getPurchaseRequests(@RequestBody @Valid PageFilterInput<PurchaseRequestDTO> input) {
        return purchaseRequestService.getPurchaseRequestsWithPaging(input);
    }

    @PostMapping("/add")
    public CommonResponse<?> addPurchaseRequest(@RequestBody PurchaseRequestEntityDto purchaseRequestDTO)
    {
        purchaseRequestService.addPurchaseRequest(purchaseRequestDTO);
        return new CommonResponse<>().success();
    }
    @DeleteMapping("/delete/{prCode}")
    public CommonResponse<?> deletePurchaseRequest(@PathVariable String prCode)
    {
        purchaseRequestService.deletePurchaseRequest(prCode);
        return new CommonResponse<>().success();
    }

    @PostMapping("/get-all")
    public PageResponse<List<PurchaseRequestEntity>> getAllPurchaseRequest(@RequestBody PageFilterInput<PurchaseRequestPagingDTO> input) {
        Pageable pageable = input.getPageSize() == 0
            ? PageRequest.of(0, Integer.MAX_VALUE)
            : PageRequest.of(input.getPageNumber(), input.getPageSize());

        Page<PurchaseRequestEntity> result = purchaseRequestService.getAllPurchaseRequest(input, pageable);
        return new PageResponse<List<PurchaseRequestEntity>>().data(result.getContent()).dataCount(result.getTotalElements());
    }

    @PostMapping("/detail/{prId}")
    public PageResponse<List<PurchaseRequestDetailEntity>> getAllPurchaseRequest(@RequestBody PageFilterInput<PurchaseRequestDetailPagingDTO> input, @PathVariable Integer prId) {
        Pageable pageable = input.getPageSize() == 0
            ? PageRequest.of(0, Integer.MAX_VALUE)
            : PageRequest.of(input.getPageNumber(), input.getPageSize());

        Page<PurchaseRequestDetailEntity> result = purchaseRequestService.getAllPRDetailDetailListByPRId(input, pageable, prId);
        return new PageResponse<List<PurchaseRequestDetailEntity>>().data(result.getContent()).dataCount(result.getTotalElements());
    }
}
