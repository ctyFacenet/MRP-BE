package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.InvoiceService;
import com.facenet.mrp.service.dto.InvoiceDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.model.PageFilterInput;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class InvoiceResource {
    private final InvoiceService invoiceService;

    public InvoiceResource(InvoiceService invoiceService){
        this.invoiceService = invoiceService;
    }
    @PostMapping("/invoices")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('VIEW','DHSX', 'KHDH', 'K', 'TK', 'HT', 'MH', 'QLSX','PRPOGRPO' )")
    public CommonResponse<?> getInvoice(@RequestBody @Valid PageFilterInput<InvoiceDTO> input){
        return invoiceService.getInvoiceWithPaging(input);
    }
}
