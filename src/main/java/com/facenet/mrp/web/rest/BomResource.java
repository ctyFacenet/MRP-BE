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
        return bomService.getAllBom(input);
    }

    /**
     * Lấy thông tin chi tiết BOM
     * @param productCode mã hàng hóa
     * @param version BOM Version
     * @return
     */
    @GetMapping("/{productCode}/{version}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'MH', 'QLSX')")
    public CommonResponse<List<BomItemDetailDTO>> getBomDetail(@PathVariable String productCode, @PathVariable String version) {
        return bomService.getBomDetail(productCode, version);
    }
}
