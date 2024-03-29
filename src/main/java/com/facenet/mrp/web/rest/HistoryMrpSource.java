package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.HistoryMrpService;
import com.facenet.mrp.service.HoldItemService;
import com.facenet.mrp.service.ListSaleService;
import com.facenet.mrp.repository.MrpAnalysisCache;
import com.facenet.mrp.service.dto.AdvancedMrpDTO;
import com.facenet.mrp.service.dto.SyntheticMrpDTO;
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
import java.util.Map;


@RestController
@RequestMapping(value = "/api")
public class HistoryMrpSource {
    private final Logger log = LogManager.getLogger(ListSaleService.class);

    @Autowired
    private HoldItemService holdItemService;

    @Autowired
    private HistoryMrpService historyMrpService;

    @Autowired
    private MrpAnalysisCache mrpAnalysisCache;

    @GetMapping(value = "/order-analytics/list-scripts-mrp")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'TK', 'HT', 'QLSX')")
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
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'QLSX')")
    public ResponseEntity<FileSystemResource> getResultMrp(@PathVariable String mrpSubCode) throws JsonProcessingException {
        return historyMrpService.getMrpResult(mrpSubCode);
    }

    @PostMapping(value = "/order-analytics/mrp-analytics/new-mrp-script")
    public CommonResponse saveScriptMrp(@RequestBody AdvancedMrpDTO mrpDTO) throws JsonProcessingException, ParseException {
        historyMrpService.saveMrpResult(mrpDTO);
        // synthetic then save hold
        if (mrpDTO.isHold()) holdItemService.saveHoldItem(viewSyntheticScriptMrp(mrpDTO).getData());
        return new CommonResponse<>().success();
    }

    @PostMapping(value = "/order-analytics/mrp-analytics/new-mrp-script/{sessionId}")
    public CommonResponse saveScriptMrpOfSession(@PathVariable String sessionId, Map<String, Boolean> isHold) throws JsonProcessingException, ParseException {
        historyMrpService.saveMrpResult(mrpAnalysisCache.getMrpResult(sessionId));
        if (isHold.containsKey("isHold") && isHold.get("isHold").equals(true))
            holdItemService.saveHoldItem(viewSyntheticScriptMrp(sessionId).getData());
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

    @PostMapping(value = "/order-analytics/synthetic-mrp-analytics")
    public PageResponse<SyntheticMrpDTO> viewSyntheticScriptMrp(@RequestBody MrpDTO mrpDTO){
        return historyMrpService.viewSynthetic(mrpDTO);
    }

    /**
     * Adapter for old method
     * @param sessionId sessionId (UUID)
     * @return
     */
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
