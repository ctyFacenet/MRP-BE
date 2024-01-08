package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.CoittServiceImpl;
import com.facenet.mrp.service.DetailVendorService;
import com.facenet.mrp.service.ListSaleService;
import com.facenet.mrp.service.dto.DataVendorAndSale;
import com.facenet.mrp.service.dto.ItemInVendorDTO;
import com.facenet.mrp.service.dto.SaleVendorForm;
import com.facenet.mrp.service.dto.VendorDetailForm;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.PageFilterInput;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author: trần đình thành
 * Controller trả api liên quan đến nghiệp vu ncc
 */
@RestController
@RequestMapping(value = "/api")
public class VendorResource {
    private final Logger log = LogManager.getLogger(CoittServiceImpl.class);

    @Autowired
    private ListSaleService listSaleService;

    @Autowired
    private DetailVendorService detailVendorService;

    @PostMapping(value = "/vendor-information/list-vendor")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'TK', 'MH')")
    public CommonResponse getAllVendor(@RequestBody PageFilterInput<DataVendorAndSale> vendorAndSaleForm){
        CommonResponse commonResponse = new CommonResponse();
        try{
            PageResponse<?> list;
            Pageable page = PageRequest.of(vendorAndSaleForm.getPageNumber(),vendorAndSaleForm.getPageSize());
            if(StringUtils.isEmpty(vendorAndSaleForm.getFilter().getSaleCode()) && StringUtils.isEmpty(vendorAndSaleForm.getFilter().getSaleName())){
                list = listSaleService.getAllData(vendorAndSaleForm,page);
            }
            else {
                list = listSaleService.getAllDataSource(vendorAndSaleForm,page);
            }
            if(list == null){
                return commonResponse.result("404","Không tìm thấy tài nguyên!",false);
            }
            return list;
        }catch (Exception e){
            log.error("Lỗi khi lấy danh sách nhà cung cấp", e);
            return commonResponse.result("500","Có lỗi server!",false);
        }
    }

    @PostMapping(value = "/vendor-information/vendor-detail/{vendorCode}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'TK', 'MH')")
    public CommonResponse getDetailVendor(@RequestBody PageFilterInput<VendorDetailForm> pageForm, @PathVariable String vendorCode) throws JsonProcessingException {
        Pageable page = PageRequest.of(pageForm.getPageNumber(),pageForm.getPageSize());
        PageResponse<?> list = detailVendorService.getData(pageForm,vendorCode,page);
        if(list == null){
            return new CommonResponse<>().result("404","Không tìm thấy tài nguyên!",false);
        }
        return list;
    }

    @PostMapping(value = "/vendor-information/vendor-detail/new-item-vendor")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'TK', 'MH')")
    public CommonResponse addNewItemForVendor(@RequestBody ItemInVendorDTO itemInVendorDTO){
        if(itemInVendorDTO == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST ,"invalid.param");
        }
        return detailVendorService.addItemForVendor(itemInVendorDTO);
    }

    @PostMapping(value = "/vendor-information/vendor-detail/{vendorCode}/new-sale-vendor-manager")
    @PreAuthorize("hasAnyAuthority('KHDH', 'MH')")
    public CommonResponse updateSaleVendor(@RequestBody SaleVendorForm saleVendorForm,@PathVariable String vendorCode){
        CommonResponse commonResponse = new CommonResponse();
        try{
            Integer in = listSaleService.updateSaleVendor(saleVendorForm,vendorCode);
            if(in != 1 && in != 2){
                return commonResponse.result("400","thất bại",true);
            }
            return commonResponse.result("00","thành công",true);
        }catch (Exception e){
            return commonResponse.result("500","có lỗi xay ra",false);
        }
    }

    @PostMapping("vendor-information/import-price")
    @PreAuthorize("hasAnyAuthority('KHDH', 'MH')")
    public CommonResponse importPrice(@RequestParam("file") MultipartFile file) throws IOException, ParseException {
        return detailVendorService.importPrice(file);
    }

    @PostMapping("vendor-information/import-price-csv")
    @PreAuthorize("hasAnyAuthority('KHDH', 'MH')")
    public CommonResponse importPriceCsv(@RequestParam("file") MultipartFile file) throws IOException, ParseException {
        return detailVendorService.importPriceCsv(file);
    }
}
