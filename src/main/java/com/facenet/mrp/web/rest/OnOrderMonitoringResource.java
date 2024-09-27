package com.facenet.mrp.web.rest;

import com.facenet.mrp.domain.mrp.PurchaseOrderEntity;
import com.facenet.mrp.service.MonitoringService;
import com.facenet.mrp.service.MqqPriceService;
import com.facenet.mrp.service.dto.PurchaseOrderDTO;
import com.facenet.mrp.service.dto.mrp.*;
import com.facenet.mrp.service.dto.request.AddMonitoringItemRequest;
import com.facenet.mrp.service.dto.request.AddMonitoringRequest;
import com.facenet.mrp.service.dto.request.CreatePurchaseOrderDTO;
import com.facenet.mrp.service.dto.request.ListMonitoringRequest;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.FindPurchaseOrderProgressFilter;
import com.facenet.mrp.service.model.MonitoringFilter;
import com.facenet.mrp.service.model.PageFilterInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/monitoring")
public class OnOrderMonitoringResource {

    private final Logger log = LogManager.getLogger(MqqPriceService.class);

    @Autowired
    MonitoringService monitoringService;

    @PostMapping("/list-all")
    @PreAuthorize("hasAnyAuthority('GSTD-View', 'PROGRESSPO', 'VIEW')")
    public ResponseEntity listAll(@RequestBody PageFilterInput<MonitoringFilter> bodyRequest){
        PageResponse<List<OnOrderMonitoringDTO>> data = monitoringService.monitoringDTOList(bodyRequest);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/list-po")
    @PreAuthorize("hasAnyAuthority('GSTD-View', 'PROGRESSPR', 'VIEW')")
    public ResponseEntity<PageResponse<List<PurchaseOrderProgressDTO>>> listAllPurchaseOrder(@RequestBody PageFilterInput<FindPurchaseOrderProgressFilter> body) {
        Pageable pageable = body.getPageSize() == 0 ? Pageable.unpaged() : PageRequest.of(body.getPageNumber(), body.getPageSize());
        PageResponse<List<PurchaseOrderProgressDTO>> result = monitoringService.findPurchaseOrderProgress(body, pageable);
        // Trả về ResponseEntity với PageResponse
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create-po")
    @PreAuthorize("hasAnyAuthority('GSTD-Create')")
    public ResponseEntity<CommonResponse> createPo(@RequestBody CreatePurchaseOrderDTO createPoRequest) {
        PageResponse<List<PurchaseOrderEntity>> data = monitoringService.createPurchaseOrder(createPoRequest);
        return ResponseEntity.ok(data);
    }
    @PostMapping("/update-po/{prId}")
    public ResponseEntity<CommonResponse> updatePo(@PathVariable Long prId, @RequestBody CreatePurchaseOrderDTO updatePoRequest) {
        CommonResponse<PurchaseOrderEntity> response = monitoringService.updatePurchaseOrder(prId, updatePoRequest);
        if (response.getResult().isOk()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/get-po/{id}")
    @PreAuthorize("hasAnyAuthority('GSTD-View', 'PROGRESSPR', 'VIEW')")
    public ResponseEntity<CommonResponse<PurchaseOrderDTO>> getPurchaseOrderById(@PathVariable Long id) {
        CommonResponse<PurchaseOrderDTO> data = monitoringService.findPurchaseOrderById(id);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/get-progress-by-item-id/{id}")
    @PreAuthorize("hasAnyAuthority('GSTD-View', 'PROGRESSPR', 'VIEW')")
    public ResponseEntity<CommonResponse<List<PurchaseOrderDTO.PurchaseOrderItemProgressDTO>>> getPurchaseOrderItemProgressByItemId(@PathVariable Long id) {
        CommonResponse<List<PurchaseOrderDTO.PurchaseOrderItemProgressDTO>> data = monitoringService.findPurchaseOrderItemProgressByItemId(id);
        return ResponseEntity.ok(data);
    }


    @PostMapping("/list-all-pr")
    @PreAuthorize("hasAnyAuthority('GSTD-View', 'PROGRESSPR', 'VIEW')")
    public ResponseEntity listAllPr(@RequestBody PageFilterInput<prMonitorFilter> bodyRequest){
        PageResponse<List<MonitorListPrDTO>> data = monitoringService.prListMonitoring(bodyRequest);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/list-all-detail-in-pr/{prCode}")
    @PreAuthorize("hasAnyAuthority('GSTD-View','PROGRESSPR', 'VIEW')")
    public ResponseEntity listAllDetailInPr(@RequestBody PageFilterInput<prMonitorFilter> bodyRequest, @PathVariable String prCode){
        PageResponse<List<ItemListInPrDTO>> data = monitoringService.prListDetailMonitoring(bodyRequest,prCode);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/list-all-detail")
    @PreAuthorize("hasAnyAuthority('GSTD-View', 'VIEW','PROGRESSPO')")
    public ResponseEntity listAllDetail(@RequestBody PageFilterInput<MonitoringFilter> bodyRequest){
        PageResponse<List<OnOrderWithDurationDetailDTO>> data = monitoringService.monitoringDTOWithDetail(bodyRequest);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/insert-duration")
    @PreAuthorize("hasAnyAuthority('GSTD-Create')")
    public ResponseEntity insertDuration(@RequestBody AddMonitoringRequest request){
        monitoringService.addMonitoringDueDate(request);
        return ResponseEntity.ok(new CommonResponse<>().success());
    }

    @PostMapping("/insert-list-duration")
    @PreAuthorize("hasAnyAuthority('GSTD-Create')")
    public CommonResponse insertListDuration(@RequestBody ListMonitoringRequest request){
        String result = monitoringService.addListMonitoringDueDate(request);
        if (result.equals("OK")){
            return new CommonResponse<>().result("00","cập nhật thành công!",true);
        }
        return new CommonResponse<>().result("400","ít nhất một bản ghi có số cột lớn hơn 15!",false);
    }

    @PostMapping("/insert-multiple-duration")
    @PreAuthorize("hasAnyAuthority('GSTD-Create')")
    public ResponseEntity insertListDuration(@RequestBody List<OnOrderWithDurationDetailDTO> request) throws ParseException {
        String result = monitoringService.addMultipleMonitoringDueDate(request);
        if(result.equals("Không được để ngày các mốc trùng nhau")){
            return ResponseEntity.ok(
                new CommonResponse<>()
                    .isOk(true)
                    .errorCode("400")
                    .message(result)
            );
        }
        return ResponseEntity.ok(
            new CommonResponse<>()
                .isOk(true)
                .success("00")
                .message(result)
        );
    }

    @PostMapping("/get-list-item-with-pr")
    @PreAuthorize("hasAnyAuthority('GSTD-View','VIEW','PROGRESSITEM')")
    public PageResponse getListItemWithPr(@RequestBody String input){
        return monitoringService.getAllItemDurationPlan(input);
    }

    @PostMapping("/get-list-item-with-po")
    @PreAuthorize("hasAnyAuthority('GSTD-View')")
    public PageResponse getListItemWithPo(@RequestBody List<ItemInPlanDurationDTO> input){
        return monitoringService.getAllItemDurationPlanWithPo(input);
    }

    @PostMapping("/insert-item-duration-plan/{planDuration}")
    @PreAuthorize("hasAnyAuthority('GSTD-Create')")
    public CommonResponse insertListItemDuration(@PathVariable String planDuration,@RequestBody List<ItemInPlanDurationWithPoDTO> request) throws ParseException {
        String result = monitoringService.addMultipleMonitoringByItem(planDuration,request);
        if (result.equals("thành công!")){
            return new CommonResponse<>().result("00","cập nhật thành công!",true);
        }
        return new CommonResponse<>().result("400","Không được để ngày các mốc trùng nhau",false);
    }

    @PostMapping("/update-item-duration-plan/{planDuration}")
    @PreAuthorize("hasAnyAuthority('GSTD-Create')")
    public CommonResponse updateListItemDuration(@PathVariable String planDuration,@RequestBody AddMonitoringItemRequest request) {
        monitoringService.addListMonitoringDueDateByItem(planDuration,request);
        return new CommonResponse<>().result("00","cập nhật thành công!",true);
    }


    @PostMapping("/list-duration-plan")
    @PreAuthorize("hasAnyAuthority('GSTD-View','PROGRESSITEM','VIEW')")
    public PageResponse insertListItemDuration(@RequestBody PageFilterInput<DurationPlanDTO> input){
        PageResponse result = monitoringService.getAllDurationPlan(input);
        return result;
    }


    @PostMapping("/new-duration-plan")
    @PreAuthorize("hasAnyAuthority('GSTD-Create')")
    public PageResponse genDurationPlanCode(@RequestBody NewDurationPlanDTO request){
        PageResponse result = monitoringService.newDurationPlan(request);
        return result;
    }


    @DeleteMapping("/delete-duration-plan/{planCode}")
    public PageResponse deleteDurationPlanCode(@PathVariable String planCode){
        PageResponse result = monitoringService.deleteDurationPlan(planCode);
        return result;
    }

    @PostMapping("/to-excel")
    public ResponseEntity<?> exportToExcel(@RequestBody CreatePurchaseOrderDTO input) {
        try {
            return monitoringService.exportToExcel(input);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during export: " + e.getMessage());
        }
    }

    @GetMapping(value = "/to-excel-pr", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportToExcelPr() {
        try {
            byte[] excelData = monitoringService.exportToExcelPr();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            String filename = "Danh_sách_tiến_độ_mua_hàng_theo_PR.xlsx";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(excelData, headers, HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(("Error occurred during export: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping(value = "/to-excel-po", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportToExcelPO() {
        try {
            byte[] excelData = monitoringService.exportToExcelPO();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            String filename = "Danh_sách_tiến_độ_mua_hàng_theo_PO.xlsx";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(excelData, headers, HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(("Error occurred during export: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping(value = "/to-excel-duration", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportToExcelDuration() {
        try {
            byte[] excelData = monitoringService.exportToExcelDuration();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            String filename = "Danh_sách_kế_hoạch_tiến_độ_vật_tư.xlsx";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(excelData, headers, HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(("Error occurred during export: " + e.getMessage()).getBytes());
        }
    }
}
