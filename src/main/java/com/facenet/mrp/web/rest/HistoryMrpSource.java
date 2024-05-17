package com.facenet.mrp.web.rest;

import com.facenet.mrp.domain.mrp.ItemHoldEntity;
import com.facenet.mrp.service.HistoryMrpService;
import com.facenet.mrp.service.HoldItemService;
import com.facenet.mrp.service.ListSaleService;
import com.facenet.mrp.repository.MrpAnalysisCache;
import com.facenet.mrp.service.dto.AdvancedMrpDTO;
import com.facenet.mrp.service.dto.HoldRequest;
import com.facenet.mrp.service.dto.SyntheticMrpDTO;
import com.facenet.mrp.service.dto.mrp.DetailHoldInMrpDTO;
import com.facenet.mrp.service.dto.mrp.MrpDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springdoc.core.converters.FileSupportConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping(value = "/api")
public class HistoryMrpSource {
    private final Logger log = LogManager.getLogger(ListSaleService.class);

    private final HoldItemService holdItemService;
    private final HistoryMrpService historyMrpService;
    private final MrpAnalysisCache mrpAnalysisCache;

    public HistoryMrpSource(MrpAnalysisCache mrpAnalysisCache, HistoryMrpService historyMrpService, HoldItemService holdItemService) {
        this.mrpAnalysisCache = mrpAnalysisCache;
        this.historyMrpService = historyMrpService;
        this.holdItemService = holdItemService;
    }


    @GetMapping(value = "/order-analytics/list-scripts-mrp")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'TK', 'HT', 'QLSX','DETAILSCRIPT','VIEW')")
    public PageResponse<?> getAllMrp(Pageable pageable){
        try{
            return historyMrpService.getAllMrp(pageable);
        }catch (Exception e){
            return new PageResponse<>()
                .errorCode("500")
                .message("có lỗi xảy ra")
                .isOk(false);
        }
    }

    @GetMapping(value = "/order-analytics/detail-script-mrp/{mrpCode}")
    public PageResponse<?> getMrpSub(@PathVariable String mrpCode,Pageable pageable){
        return historyMrpService.getMrpSub(mrpCode,pageable);
    }

    @GetMapping(value = "/order-analytics/view-result-script-mrp/{mrpSubCode}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'QLSX','DETAILSCRIPT','VIEW')")
    public ResponseEntity<FileSystemResource> getResultMrp(@PathVariable String mrpSubCode) throws JsonProcessingException {
        return historyMrpService.getMrpResult(mrpSubCode);
    }

    @PostMapping(value = "/order-analytics/mrp-analytics/new-mrp-script")
    public CommonResponse saveScriptMrp(@RequestBody AdvancedMrpDTO mrpDTO) throws JsonProcessingException, ParseException {
        historyMrpService.saveMrpResult(mrpDTO, mrpDTO.isHold());
        // synthetic then save hold
        if (mrpDTO.isHold()) holdItemService.saveHoldItem(viewSyntheticScriptMrp(mrpDTO).getData());
        return new CommonResponse<>().success();
    }

    @PostMapping(value = "/order-analytics/mrp-analytics/new-mrp-script/{sessionId}")
    public CommonResponse saveScriptMrpOfSession(@PathVariable String sessionId, @RequestBody Map<String, Boolean> isHold) throws JsonProcessingException, ParseException {
        historyMrpService.saveMrpResult(mrpAnalysisCache.getMrpResult(sessionId), (isHold.containsKey("isHold") && isHold.get("isHold").equals(true)));
        if (isHold.containsKey("isHold") && isHold.get("isHold").equals(true))
            holdItemService.saveHoldItem(viewSyntheticScriptMrp(sessionId).getData());
        mrpAnalysisCache.clearCache(sessionId);
        return new CommonResponse<>().success();
    }

    @PostMapping(value = "/order-analytics/mrp-analytics/new-mrp-script/v2/{sessionId}")
    public CommonResponse saveScriptMrpOfSession(@PathVariable String sessionId, @RequestBody HoldRequest holdRequest) throws JsonProcessingException, ParseException {


        AdvancedMrpDTO advancedMrpDTO1 = mrpAnalysisCache.getMrpResult(sessionId);
//        historyMrpService.saveMrpResult(advancedMrpDTO1, false);

        AdvancedMrpDTO advancedMrpDTO = mrpAnalysisCache.getMrpResult(sessionId);
        historyMrpService.saveMrpResult( advancedMrpDTO,(holdRequest.getIsHold().containsKey("isHold") && holdRequest.getIsHold().get("isHold").equals(true)));
        if (holdRequest.getIsHold().containsKey("isHold") && holdRequest.getIsHold().get("isHold").equals(true))
            holdItemService.saveHoldItemV2(viewSyntheticScriptMrp(sessionId).getData(),holdRequest.getListHold());
        mrpAnalysisCache.clearCache(sessionId);
        return new CommonResponse<>().success();
    }

    @DeleteMapping(value = "/order-analytics/{mrpCode}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public CommonResponse deleteScriptMrp(@PathVariable String mrpCode) {
        return historyMrpService.deleteHistoryMrp(mrpCode);
    }

    @DeleteMapping(value = "/order-analytics/{mrpCode}/{mrpSubCode}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public CommonResponse deleteSubScriptMrp(@PathVariable String mrpCode,@PathVariable String mrpSubCode) {
        return historyMrpService.deleteHistorySubMrp(mrpCode, mrpSubCode);
    }

    @PostMapping(value = "/order-analytics/detail-hold-1")
    public PageResponse viewDetailHold1(@RequestBody SyntheticMrpDTO syntheticMrpDTO){
        return historyMrpService.getDetailHold(syntheticMrpDTO);
        //TODO: phần phân tích cơ bản đang sai, khi sửa lại thì phần hold này phải sửa như dưới
        //    public PageResponse viewDetailHold1(@RequestBody AdvancedMrpDTO advancedMrpDTO,){
        //        return historyMrpService.getDetailHold(syntheticMrpDTO,advancedMrpDTO);
        //
        //    }
    }

    @PostMapping(value = "/order-analytics/detail-hold-2")
    public PageResponse viewDetailHold2(@RequestBody MrpDTO mrpDTO){
        PageResponse<SyntheticMrpDTO> response;
        response = this.viewSyntheticScriptMrp(mrpAnalysisCache.getMrpResult(mrpDTO.getSessionId()));
        return historyMrpService.getDetailHold(response.getData());
    }

    @PostMapping(value = "/order-analytics/detail-hold-2/v2")
    public PageResponse viewDetailHold2V2(@RequestBody AdvancedMrpDTO advancedMrpDTO){
        PageResponse<SyntheticMrpDTO> response;
        response = this.viewSyntheticScriptMrp(mrpAnalysisCache.getMrpResult(advancedMrpDTO.getSessionId()));
        return historyMrpService.getDetailHoldV2(response.getData(),advancedMrpDTO);
    }


    //TODO here phân tích cơ bản
    @PostMapping(value = "/order-analytics/synthetic-mrp-analytics")
    public PageResponse<SyntheticMrpDTO> viewSyntheticScriptMrp(@RequestBody MrpDTO mrpDTO){
        return historyMrpService.viewSynthetic(mrpDTO);
    }


    /**
     * Adapter for old method
     * @param sessionId sessionId (UUID)
     * @return
     */
    //TODO here phân tích nâng cao
    @GetMapping(value = "/order-analytics/synthetic-mrp-analytics/{sessionId}")
    public PageResponse<SyntheticMrpDTO> viewSyntheticScriptMrp(@PathVariable String sessionId){
        PageResponse<SyntheticMrpDTO> result = this.viewSyntheticScriptMrp(mrpAnalysisCache.getMrpResult(sessionId));
        mrpAnalysisCache.putSyntheticResult(sessionId, result.getData());
        result.getData().setSessionId(sessionId);
        return result;
    }

    @GetMapping(value = "/order-analytics/get-result-script-mrp/{mrpSubCode}")
    public PageResponse<?> getResultMrpByCode(@PathVariable String mrpSubCode) throws JsonProcessingException {
        return historyMrpService.getMrpResultByCode(mrpSubCode);
    }

    @GetMapping("/order-analytics/{productOrderCode}/analyzed")
    public CommonResponse getAnalyzedMRPOfOrder(@PathVariable String productOrderCode) {
        return historyMrpService.getAnalyzedMRPOfOrder(productOrderCode);
    }
}
