package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.PurchaseRecommendationDetailService;
import com.facenet.mrp.service.dto.*;
import com.facenet.mrp.service.dto.mrp.SendApprovalRequest;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.ApproveInput;
import com.facenet.mrp.service.model.ItemFilter;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.model.UpdatePurchaseRecommendationForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/purchase-recommendations")
public class PurchaseRecommendationDetailResource {
    private final PurchaseRecommendationDetailService purchaseRecommendationDetailService;

    public PurchaseRecommendationDetailResource(PurchaseRecommendationDetailService purchaseRecommendationDetailService) {
        this.purchaseRecommendationDetailService = purchaseRecommendationDetailService;
    }

    @PostMapping("/{purchaseRecommendationId}")
    public PageResponse<List<PurchaseRecommendationDetailDTO>> getAllItemsOfPurchaseRecommendation(@PathVariable Integer purchaseRecommendationId, @RequestBody PageFilterInput<ItemFilter> input) {
        return purchaseRecommendationDetailService.getAllItems(purchaseRecommendationId, input);
    }

    @PostMapping("/get-all-items/{purchaseRecommendationId}/{batch}")
    public PageResponse<List<PurchaseRecommendationDetailDTO>> getAllItemsOfPurchaseHasRecommendation(@PathVariable Integer purchaseRecommendationId,@PathVariable Integer batch, @RequestBody PageFilterInput<ItemFilter> input) {
        return purchaseRecommendationDetailService.getAllItemsHasRecommendation(purchaseRecommendationId,batch, input);
    }

    @GetMapping("/get-all-recommand/{purchaseRecommendationId}")
    public PageResponse<List<String>> getItemsOfPurchaseHasRecommendation(@PathVariable String purchaseRecommendationId) {
        return purchaseRecommendationDetailService.getItemsHasRecommendation(purchaseRecommendationId);
    }

    @PutMapping("/{purchaseRecommendationId}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public CommonResponse updatePurchaseRecommendation(@RequestBody Map<String,List<UpdatePurchaseRecommendationForm>> updateForm, @PathVariable Integer purchaseRecommendationId) {
        if (updateForm.get("items") == null || updateForm.get("items").size() == 0) throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.param");
        return purchaseRecommendationDetailService.updatePurchaseRecommendation(
            purchaseRecommendationId,
            updateForm.get("items")
        );
    }

    @PostMapping("/{purchaseRecommendationId}/items/{itemCode}/price")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'MH', 'HT','VIEW','RECOMMENDATION')")
    public PageResponse<ItemPriceOfVendorListDTO> getAllPriceOfItem(@PathVariable String itemCode, @PathVariable Integer purchaseRecommendationId, @RequestBody PageFilterInput<Double> input) throws JsonProcessingException {
        return purchaseRecommendationDetailService.getAllPriceOfItem(
            itemCode,
            purchaseRecommendationId,
            input
        );
    }

    @PutMapping("/{purchaseRecommendationId}/items/{itemCode}/moqPriceId/{moqPriceId}")
    public CommonResponse updateMoqPriceId(@PathVariable String itemCode, @PathVariable Integer purchaseRecommendationId, @PathVariable Integer moqPriceId) {
        return purchaseRecommendationDetailService.updateMoqPriceId(
            itemCode,
            purchaseRecommendationId,
            moqPriceId
        );
    }

    @PutMapping("/{purchaseRecommendationId}/send-approval")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public CommonResponse sendApprovalRecommendation(@RequestBody SendApprovalRequest approvalRequest, @PathVariable Integer purchaseRecommendationId) {
        if (approvalRequest.getItems() == null || approvalRequest.getItems().size() == 0) throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.param");
        return purchaseRecommendationDetailService.sendApproval(
            purchaseRecommendationId,
            approvalRequest
        );
    }

    @PostMapping("/{purchaseRecommendationId}/{batch}/approval")
    public CommonResponse approveRecommendation(@RequestBody @Valid ApproveInput input, @PathVariable Integer purchaseRecommendationId,@PathVariable Integer batch) {
        return purchaseRecommendationDetailService.approveRecommendation(
            purchaseRecommendationId,
            batch,
            input
        );
    }

    @GetMapping("/{purchaseRecommendationId}/export")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'HT')")
    public CommonResponse getAllItemsForExport(@PathVariable Integer purchaseRecommendationId) {
        return purchaseRecommendationDetailService.getAllItemsForExport(purchaseRecommendationId);
    }

    @PostMapping("/purchase-recommendation-plan/new-plan/{soCode}/{mrpSubCode}/{itemCode}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public ResponseEntity createNewRecommendationPlan(
        @PathVariable String soCode,
        @PathVariable String itemCode,
        @PathVariable String mrpSubCode,
        @RequestBody RecommendationPlanNoteDTO input
        ){
        purchaseRecommendationDetailService.createNewRecommendationPlan(soCode, mrpSubCode, itemCode, input);
        return ResponseEntity.ok(new CommonResponse<>()
            .isOk(true)
                .errorCode("00")
            .message("Thành công"));
    }

    @PutMapping("/purchase-recommendation-plan/plan-deletion")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public ResponseEntity deleteRecommendationPlan(
        @RequestBody RecommendationPlanDto recommendationPlan
    ){
        purchaseRecommendationDetailService.deleteRecommendationPlan(recommendationPlan);
        return ResponseEntity.ok(new CommonResponse<>()
            .isOk(true)
            .errorCode("00")
            .message("Thành công"));
    }

    @GetMapping("{batch}/{soCode}/{mrpSubCode}/{itemCode}/purchase-recommendation-plan/all-plans")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public ResponseEntity getAllRecommendationPlan(
        @PathVariable Integer batch,
        @PathVariable String soCode,
        @PathVariable String itemCode,
        @PathVariable String mrpSubCode
    ){
        RecommendationPlanNoteDTO plan = purchaseRecommendationDetailService.getAllRecommendationPlanBatch(batch, soCode, mrpSubCode, itemCode);
        return ResponseEntity.ok(new CommonResponse<>()
            .isOk(true)
            .errorCode("00")
            .message("Thành công")
            .data(plan));
    }

    @PutMapping("{purchaseRecommendationId}/{itemCode}/{status}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public CommonResponse changeItemStatus(@PathVariable String itemCode, @PathVariable Integer purchaseRecommendationId, @PathVariable Integer status) {
        purchaseRecommendationDetailService.closePurchaseRequest(purchaseRecommendationId, itemCode, status);
        return new CommonResponse().success();
    }
    @GetMapping("/purchase-recommendation-plan/all-plans/{soCode}/{mrpSubCode}/{itemCode}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH','VIEW','RECOMMENDATION')")
    public ResponseEntity getAllRecommendationPlan(
        @PathVariable String soCode,
        @PathVariable String itemCode,
        @PathVariable String mrpSubCode
    ){
        RecommendationPlanNoteDTO plan = purchaseRecommendationDetailService.getAllRecommendationPlan(soCode, mrpSubCode, itemCode);
        return ResponseEntity.ok(new CommonResponse<>()
            .isOk(true)
            .errorCode("00")
            .message("Thành công")
            .data(plan));
    }

    @PostMapping(value="/to-excel-pr/{purchaseRecommendationId}/{batch}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportToExcelPr(@PathVariable Integer purchaseRecommendationId,@PathVariable Integer batch, @RequestBody PageFilterInput<ItemFilter> input) {
        try {
            byte[] excelData = purchaseRecommendationDetailService.exportPurchaseRecommendationDetailToExcel(purchaseRecommendationId, batch, input);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            String filename = "Phiếu_yêu_cầu_mua_vật_tư.xlsx";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(excelData, headers, HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(("Error occurred during export: " + e.getMessage()).getBytes());
        }
    }
}
