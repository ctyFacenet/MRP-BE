package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.HoldItemService;
import com.facenet.mrp.service.dto.ItemHoldDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.ItemHoldInput;
import com.facenet.mrp.thread.SyncDataFromSap;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/holding-items")
public class HoldItemResource {
    private final HoldItemService holdItemService;

    private final SyncDataFromSap syncDataFromSap;

    public HoldItemResource(HoldItemService holdItemService,SyncDataFromSap syncDataFromSap) {
        this.holdItemService = holdItemService;
        this.syncDataFromSap = syncDataFromSap;
    }

    @GetMapping("/{itemCode}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'TK', 'QLSX', 'HT', 'K')")
    public CommonResponse<List<ItemHoldDTO>> getItemHold(@PathVariable String itemCode) {
        return new CommonResponse<>().success().data(holdItemService.getItemHold(itemCode));
    }

    @PostMapping("/{itemCode}")
    public CommonResponse<List<ItemHoldDTO>> getItemHoldWithWarehouse(@PathVariable String itemCode, @RequestBody ItemHoldInput input) {
        return new CommonResponse<>().success().data(holdItemService.getItemHold(itemCode, input));
    }
}
