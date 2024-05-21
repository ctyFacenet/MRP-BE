package com.facenet.mrp.web.rest;

import com.facenet.mrp.domain.sap.Citt1Entity;
import com.facenet.mrp.domain.sap.CoittEntity;
import com.facenet.mrp.repository.sap.CoittRepository;
import com.facenet.mrp.service.BomService;
import com.facenet.mrp.service.dto.BomDTO;
import com.facenet.mrp.service.dto.BomItemDetailDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.BomFilterInput;
import com.facenet.mrp.service.model.PageFilterInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/boms")
@RestController
public class BomResource {
    private final BomService bomService;

    public BomResource(BomService bomService) {
        this.bomService = bomService;
    }

    @Autowired
    private CoittRepository repository;

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

    @PostMapping("/export")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'MH', 'QLSX','BOM','VIEW')")
    public PageResponse<List<BomDTO>> export(@RequestBody @Valid PageFilterInput<BomFilterInput> input) {
        return search(input);
    }

    private PageResponse<List<BomDTO>> search(PageFilterInput<BomFilterInput> input){
        PageResponse<List<BomDTO>> pageResponse = bomService.getAllBom(input);
        List<BomDTO> results = new ArrayList<>();
        for (BomDTO bomDTO: pageResponse.getData()){
            bomDTO.setLevel(1);
            results.add(bomDTO);
            List<Citt1Entity> entityList = repository.getAll(bomDTO.getProductCode(),bomDTO.getVersion());
            for (Citt1Entity citt1Entity: entityList){
                System.out.println("---------------"+citt1Entity.getuItemCode()+"-"+citt1Entity.getuVersions());
                CoittEntity coittEntity = repository.getListBTP(citt1Entity.getuItemCode(),citt1Entity.getuVersions());
                if(coittEntity != null){
                    BomDTO newBom = new BomDTO();
                    newBom.setProductCode(coittEntity.getuProNo());
                    newBom.setDescription(coittEntity.getuProNam());
                    newBom.setWarehouse(coittEntity.getuWhsCod());
                    newBom.setDocUrl(coittEntity.getuDocUrl());
                    newBom.setLevel(2);
                    newBom.setRoot(bomDTO.getProductCode()+"_"+bomDTO.getVersion());
                    newBom.setCreateTime(coittEntity.getCreateDate());
                    newBom.setFromDate(coittEntity.getuFromDate());
                    newBom.setGroupItem(101);
                    newBom.setQuota(coittEntity.getuQuantity());
                    newBom.setSpeciality(coittEntity.getuSpec());
                    newBom.setRemark(coittEntity.getRemark());
                    newBom.setToDate(coittEntity.getuToDate());
                    newBom.setStatus(coittEntity.getuActive());
                    newBom.setVersion(coittEntity.getuVersions());
                    results.add(newBom);
                }
            }
        }
        return new PageResponse<List<BomDTO>>()
            .result("00", "Thành công", true)
            .data(results);
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
