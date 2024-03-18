package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.PurchaseRequestService;
import com.facenet.mrp.service.dto.PurchaseRequestDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.model.PageFilterInput;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class PurchaseRequestResource {
    private final PurchaseRequestService purchaseRequestService;

    public PurchaseRequestResource(PurchaseRequestService purchaseRequestService) {
        this.purchaseRequestService = purchaseRequestService;
    }

    @PostMapping("/purchase-requests")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'MH', 'QLSX' ,'PRPOGRPO')")
    public CommonResponse<?> getPurchaseRequests(@RequestBody @Valid PageFilterInput<PurchaseRequestDTO> input) {
        return purchaseRequestService.getPurchaseRequestsWithPaging(input);
    }
}
