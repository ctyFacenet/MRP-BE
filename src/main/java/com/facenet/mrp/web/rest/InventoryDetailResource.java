package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.InventoryDetailService;
import com.facenet.mrp.service.dto.InventoryDetailDTO;
import com.facenet.mrp.service.dto.InventorySupplierDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * @author: trần đình thành
 * Controller trả api liên quan đến nghiệp vụ tồn kho
 */
@RestController
@RequestMapping(value = "/api")
public class InventoryDetailResource {
    @Autowired
    private InventoryDetailService inventoryDetailService;
    /**
     * api lấy thông tin chi tiết tồn kho theo mã hàng hóa
     * @param itemCode
     * @return
     */
    @GetMapping(value = "/in-stock-products/{itemCode}")
    public CommonResponse getInventoryDetail(@PathVariable String itemCode){
        CommonResponse commonResponse = new CommonResponse();
        try{
            List<InventoryDetailDTO> list = inventoryDetailService.getInventoryDetail(itemCode);
            if(list == null){
                return commonResponse.result("400","Yêu cầu không hợp lệ!",false);
            }
            return commonResponse.data(list).result("00","Thành Công!",true);
        }catch (Exception e){
            return commonResponse.result("500","Có lỗi server!",false);
        }
    }
    /**
     * api lấy thông tin nhà cung cấp theo mã hàng hóa
     * @param itemCode
     * @return
     */
    @GetMapping(value = "/in-stock-products/{itemCode}/vendors")
    public CommonResponse getInventorySupplier(@PathVariable String itemCode){

        CommonResponse commonResponse = new CommonResponse();
        try{
            List<InventorySupplierDTO> list = inventoryDetailService.getInventorySupplier(itemCode);
            if(list == null){
                return commonResponse.result("400","Yêu cầu không hợp lệ!",false);
            }
            return commonResponse.data(list).result("00","Thành Công!",true);
        }catch (Exception e){
            return commonResponse.result("500","Có lỗi server!",false);
        }
    }
}
