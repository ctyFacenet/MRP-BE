package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.CoittServiceImpl;
import com.facenet.mrp.service.dto.DetailBomVersionDTO;
import com.facenet.mrp.service.dto.ListBomDTO;
import com.facenet.mrp.service.dto.ViewBomDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.BomFilterInput;
import com.facenet.mrp.service.model.RequestInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author: trần đình thành
 * Controller trả api liên quan đến bom version trong sap
 */
@RestController
@RequestMapping(value = "/api")
public class CoittResource {
    private final Logger log = LogManager.getLogger(CoittServiceImpl.class);

    @Autowired
    private CoittServiceImpl coittService;

    /**
     * api lấy danh sách bom verion có trạng thái active
     */
    @GetMapping(value = "/list-bom-version/{productCode}")
    public CommonResponse getListBom(@PathVariable String productCode){
        return new CommonResponse().success().data(coittService.getListBomAndActive(productCode.trim()));
    }

    /**
     * api lấy danh sách chi tiết nvl bom verion cho mã sản phẩm tương ứng
     * @return
     */
    @GetMapping(value = "/products-order-detail/{productCode}/{version}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'TKKT', 'HT', 'MH')")
    public CommonResponse getDetailBomVersionWithProduct(@PathVariable String productCode, @PathVariable String version){
        CommonResponse commonResponse = new CommonResponse();
        try {
            ViewBomDTO list = coittService.getDetailBomVersionWithProduct(productCode,version);
            if(list == null){
                return commonResponse.result("404","Không tìm thấy dữ liệu!",false);
            }
            return commonResponse.data(list).result("00","Thành Công!",true);
        }catch (Exception e){
            return commonResponse.result("500","Có lỗi server!",false);
        }
    }

    @PostMapping("/order-analytics/detail/list-boms")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'MH', 'QLSX','LISTSCRIPT','VIEW')")
    public ResponseEntity getDetailBomVersionByProduct(@RequestBody RequestInput<BomFilterInput> requestInput){
        Page<DetailBomVersionDTO> list = coittService.listBomDetail(requestInput);
        return ResponseEntity.ok(
            new PageResponse<DetailBomVersionDTO>()
                .dataCount(list.getTotalElements())
                .success()
                .data(list.getContent())
        );
    }
}
