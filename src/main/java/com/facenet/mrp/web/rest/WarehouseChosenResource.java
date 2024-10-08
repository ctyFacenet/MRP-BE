package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.WarehouseChosenService;
import com.facenet.mrp.service.dto.response.CommonResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse-chosen")
public class WarehouseChosenResource
{
    private final WarehouseChosenService warehouseChosenService;

    public WarehouseChosenResource(WarehouseChosenService warehouseChosenService) {
        this.warehouseChosenService = warehouseChosenService;
    }

    @PostMapping("/add")
    public CommonResponse<?> addWarehouseChosen(@RequestBody List<String> wareHouseChosenCode)
    {
        warehouseChosenService.addWarehouseChosen(wareHouseChosenCode);
        return new CommonResponse<>().success();
    }

    @GetMapping("/get/{warehouseChosenId}")
    public CommonResponse<List<String>> getWareHouseChosen(@PathVariable int warehouseChosenId)
    {
        return new CommonResponse<>().success().data(warehouseChosenService.getWarehouseCodes(warehouseChosenId));
    }
}
