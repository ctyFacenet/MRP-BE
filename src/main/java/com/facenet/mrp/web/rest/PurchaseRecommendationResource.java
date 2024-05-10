package com.facenet.mrp.web.rest;

import com.facenet.mrp.repository.MrpAnalysisCache;
import com.facenet.mrp.service.HistoryMrpService;
import com.facenet.mrp.service.HoldItemService;
import com.facenet.mrp.service.PurchaseRecommendationService;
import com.facenet.mrp.service.dto.*;
import com.facenet.mrp.service.dto.mrp.ForecastMaterialDTO;
import com.facenet.mrp.service.dto.mrp.OrderItemForm;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.model.PurchaseRecommendationFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/purchase-recommendations")
public class PurchaseRecommendationResource {

    private final PurchaseRecommendationService purchaseRecommendationService;
    private final MrpAnalysisCache mrpAnalysisCache;
    private final HistoryMrpService historyMrpService;
    private final HoldItemService holdItemService;

    public PurchaseRecommendationResource(HoldItemService holdItemService,PurchaseRecommendationService purchaseRecommendationService, MrpAnalysisCache mrpAnalysisCache, HistoryMrpService historyMrpService) {
        this.holdItemService = holdItemService;
        this.purchaseRecommendationService = purchaseRecommendationService;
        this.mrpAnalysisCache = mrpAnalysisCache;
        this.historyMrpService = historyMrpService;
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'MH', 'HT', 'RECOMMENDATION','VIEW')")
    public PageResponse<List<PurchaseRecommendationDTO>> getAllPurchaseRecommendation(@RequestBody @Valid PageFilterInput<PurchaseRecommendationFilter> input) {
        return purchaseRecommendationService.getAllPurchaseRecommendation(input);
    }

    @PostMapping("/get-all-purchase")
    public PageResponse<List<PurchaseHasRecommendationDTO>> getAllPurchaseHasRecommendation(@RequestBody @Valid PageFilterInput<PurchaseRecommendationFilter> input) {
        return purchaseRecommendationService.getAllPurchaseHasRecommendation(input);
    }

    @DeleteMapping("/{purchaseRecommendationId}")
    public CommonResponse deletePurchaseRecommendation(@PathVariable Integer purchaseRecommendationId) {
        return purchaseRecommendationService.deletePurchaseRecommendation(purchaseRecommendationId);
    }

    @DeleteMapping("/{purchaseRecommendationId}/{batch}/delete-in-batch")
    public CommonResponse deletePurchaseBatchRecommendation(@PathVariable Integer purchaseRecommendationId,@PathVariable Integer batch) {
        return purchaseRecommendationService.deletePurchaseBatchRecommendation(purchaseRecommendationId,batch);
    }

    @PostMapping("/mrp-analytics/save-synthetic-mrp-analytics")
    public CommonResponse saveSyntheticMrp(@RequestBody SyntheticMrpDTO syntheticMrpDTO) throws JsonProcessingException, ParseException {
        return purchaseRecommendationService.saveSyntheticMrp(syntheticMrpDTO, null);
    }


    @PostMapping("/mrp-analytics/save-synthetic-mrp-analytics/{sessionId}")
    public CommonResponse saveSyntheticMrpOfSession(@PathVariable String sessionId, @RequestBody Set<String> items) throws JsonProcessingException, ParseException {
        AdvancedMrpDTO advancedMrpDTO = mrpAnalysisCache.getMrpResult(sessionId);
        historyMrpService.saveMrpResult(advancedMrpDTO, false);
        return purchaseRecommendationService.saveSyntheticMrp(mrpAnalysisCache.getSyntheticResult(sessionId), items);
    }

    @PostMapping("/v2/mrp-analytics/save-synthetic-mrp-analytics/{sessionId}")
    public CommonResponse saveSyntheticMrpOfSessionV2(@PathVariable String sessionId, @RequestParam Boolean isHold, @RequestBody Set<String> items) throws JsonProcessingException, ParseException {
        historyMrpService.saveMrpResult(mrpAnalysisCache.getMrpResult(sessionId), isHold);
        return purchaseRecommendationService.saveSyntheticMrp(mrpAnalysisCache.getSyntheticResult(sessionId), items);
    }


    @PutMapping("/{mrpSubCode}/{status}")
    public CommonResponse updatePurchaseRecommendationStatus(@PathVariable String mrpSubCode, @PathVariable Integer status) {
        purchaseRecommendationService.updateStatus(mrpSubCode, status);
        return new CommonResponse<>().success();
    }

    @PostMapping("/forecast-orders")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public CommonResponse saveForecastOrder(@RequestBody ForecastMaterialDTO input) throws JsonProcessingException, ParseException {
        return purchaseRecommendationService.saveForecastOrder(input);
    }

    @GetMapping("/{purchaseRecommendationId}/items/{itemCode}/mrp-detail")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'MH', 'HT','VIEW','RECOMMENDATION')")
    public PageResponse<?> getSyntheticByCode(@PathVariable Integer purchaseRecommendationId,@PathVariable String itemCode) throws JsonProcessingException {
        return purchaseRecommendationService.getSyntheticByItemCode(purchaseRecommendationId,itemCode);
    }

    @PostMapping("/ordered-items")
    public PageResponse<?> getItemOnPrPo(@RequestBody PageFilterInput<OrderItemForm> formPageFilterInput) {
        return purchaseRecommendationService.getItemOnPrPo(formPageFilterInput);
    }

//    @PostMapping(value = "/order-analytics/mrp-analytics/new-mrp-script/v2/{sessionId}")
//    public CommonResponse saveScriptMrpOfSession(@PathVariable String sessionId, @RequestBody HoldRequest holdRequest) throws JsonProcessingException, ParseException {
//        AdvancedMrpDTO advancedMrpDTO = mrpAnalysisCache.getMrpResult(sessionId);
//        historyMrpService.saveMrpResult( advancedMrpDTO,(holdRequest.getIsHold().containsKey("isHold") && holdRequest.getIsHold().get("isHold").equals(true)));
//        if (holdRequest.getIsHold().containsKey("isHold") && holdRequest.getIsHold().get("isHold").equals(true))
//            holdItemService.saveHoldItemV2(
//                viewSyntheticScriptMrp(sessionId).getData(),holdRequest.getListHold());
//        mrpAnalysisCache.clearCache(sessionId);
//        return new CommonResponse<>().success();
//    }
}
