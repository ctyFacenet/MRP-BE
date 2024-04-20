package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.BomService;
import com.facenet.mrp.service.dto.BomDTO;
import com.facenet.mrp.service.dto.BomItemDetailDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.BomFilterInput;
import com.facenet.mrp.service.model.PageFilterInput;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/boms")
@RestController
public class BomResource {
    private final BomService bomService;

    public BomResource(BomService bomService) {
        this.bomService = bomService;
    }

    /**
     * Lấy danh sách BOM
     * @param input
     * @return
     */
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'MH', 'QLSX','BOM','VIEW')")
    public PageResponse<List<BomDTO>> getAllBom(@RequestBody @Valid PageFilterInput<BomFilterInput> input) {
        return getLevel(input);
    }

    @GetMapping("/get")
    public List<String> get(){
        List<String> btps = bomService.getListBTP();
        return btps;
    }

    private PageResponse<List<BomDTO>> getLevel(PageFilterInput<BomFilterInput> input){
        PageResponse<List<BomDTO>> pageResponse = bomService.getAllBom(input);
        List<String> btps = bomService.getListBTP();
        for (BomDTO bomDTO: pageResponse.getData()){
            if(bomDTO.getGroupItem() == 104){
                bomDTO.setLevel(1);
            }else if(bomDTO.getGroupItem() == 101 && btps.contains(bomDTO.getProductCode())){
                bomDTO.setLevel(2);
            }
        }
        return pageResponse;
    }

    @GetMapping("/get-bom/{productCode}/{version}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'MH', 'QLSX', 'VIEW','BOM')")
    public CommonResponse<List<BomItemDetailDTO>> getBomDetailV2(@PathVariable String productCode, @PathVariable String version) {
        return bomService.getBomDetailV2(productCode, version);
    }
    /**
     * Lấy thông tin chi tiết BOM
     * @param productCode mã hàng hóa
     * @param version BOM Version
     * @return
     */
    @GetMapping("/{productCode}/{version}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'MH', 'QLSX', 'VIEW','BOM')")
    public CommonResponse<List<BomItemDetailDTO>> getBomDetail(@PathVariable String productCode, @PathVariable String version) {
        return bomService.getBomDetail(productCode, version);
    }

    @GetMapping("/get-list-bom/{productCode}")
    public CommonResponse<List<String>> getListBom(@PathVariable String productCode) {
        return bomService.getListBom(productCode);
    }
}
