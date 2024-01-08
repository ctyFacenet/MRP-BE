package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.OitwService;
import com.facenet.mrp.service.dto.OitwDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/oitw")
public class OitwResource {

    @Autowired
    private OitwService oitwService;

    @GetMapping("/list-by-item")
    public ResponseEntity listByItem(@RequestParam("itemCode") String itemCode){
        List<OitwDTO> list = oitwService.listByItemCode(itemCode);
        return ResponseEntity.ok(
            new CommonResponse<>().success().data(list)
        );
    }
}
