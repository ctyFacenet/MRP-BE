package com.facenet.mrp.web.rest;

import com.facenet.mrp.domain.sap.Citt1Entity;
import com.facenet.mrp.domain.sap.CoittEntity;
import com.facenet.mrp.domain.sap.OittEntity;
import com.facenet.mrp.repository.sap.CoittRepository;
import com.facenet.mrp.repository.sap.OitmRepository;
import com.facenet.mrp.repository.sap.OittRepository;
import com.facenet.mrp.service.BomService;
import com.facenet.mrp.service.dto.BomDTO;
import com.facenet.mrp.service.dto.BomItemDetailDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.dto.sap.CoittCitt1DTO;
import com.facenet.mrp.service.model.BomFilterInput;
import com.facenet.mrp.service.model.PageFilterInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/api/boms")
@RestController
public class BomResource {
    private final BomService bomService;

    public BomResource(BomService bomService,
                       OittRepository oittRepository,
                       OitmRepository oitmRepository) {
        this.bomService = bomService;
        this.oittRepository = oittRepository;
        this.oitmRepository = oitmRepository;
    }

    @Autowired
    private CoittRepository repository;
    private final OittRepository oittRepository;
    private final OitmRepository oitmRepository;

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
        List<CoittCitt1DTO> coittCitt1DTOS = oittRepository.getAllDistinct();
        List<OittEntity> coittEntities = oittRepository.getAllOitt();

        HashMap<String,List<CoittCitt1DTO>> coittCitt1HashMap = new HashMap<>();
        ArrayList<CoittCitt1DTO> listChild;
        List<BomDTO> results = new ArrayList<>();

        for (CoittCitt1DTO dto: coittCitt1DTOS){
            if(coittCitt1HashMap.containsKey(dto.getuProNo()+"_"+dto.getuVersions())){
                coittCitt1HashMap.get(dto.getuProNo()+"_"+dto.getuVersions()).add(dto);
            }else{
                listChild = new ArrayList<>();
                listChild.add(dto);
                coittCitt1HashMap.put(dto.getuProNo()+"_"+dto.getuVersions(),listChild);
            }
        }

        for (BomDTO bomDTO: pageResponse.getData()){
            bomDTO.setLevel(1);
            results.add(bomDTO);
            for(String key: coittCitt1HashMap.keySet()){
                if((bomDTO.getProductCode()+ "_" + bomDTO.getVersion()).equals(key)){
                    List<CoittCitt1DTO> dtoList = coittCitt1HashMap.get(key);

                    for (CoittCitt1DTO coittCitt1DTO: dtoList){
                        for (OittEntity coittEntity: coittEntities){
                            if((coittCitt1DTO.getuItemCode()+"_"+coittCitt1DTO.getuVersionsCitt1())
                                .equals(coittEntity.getCode()+"_1.0")){
                                BomDTO newBom = new BomDTO();
                                newBom.setProductCode(coittEntity.getCode());
                                newBom.setDescription(oitmRepository.getItemName(coittEntity.getCode()));
                                newBom.setWarehouse(coittEntity.getToWH());
//                                newBom.setDocUrl(coittEntity.getuDocUrl());
                                newBom.setLevel(2);
                                newBom.setRoot(bomDTO.getProductCode()+"_"+bomDTO.getVersion());
                                newBom.setCreateTime(coittEntity.getCreateDate());
//                                newBom.setFromDate(coittEntity.getuFromDate());
                                newBom.setGroupItem(101);
                                newBom.setQuota(coittEntity.getQauntity());
//                                newBom.setSpeciality(coittEntity.getuSpec());
//                                newBom.setRemark(coittEntity.getRemark());
//                                newBom.setToDate(coittEntity.getuToDate());
                                newBom.setStatus(coittEntity.getuStatus());
                                newBom.setVersion("1.0");
                                results.add(newBom);
                            }
                        }
                    }
                }
            }
        }

//        List<BomDTO> results = new ArrayList<>();
//        for (BomDTO bomDTO: pageResponse.getData()){
//            bomDTO.setLevel(1);
//            results.add(bomDTO);
//            List<Citt1Entity> entityList = repository.getAll(bomDTO.getProductCode(),bomDTO.getVersion());
//            for (Citt1Entity citt1Entity: entityList){
//                System.out.println("---------------"+citt1Entity.getuItemCode()+"-"+citt1Entity.getuVersions());
//                List<CoittEntity> coittEntity = repository.getListBTP(citt1Entity.getuItemCode(),citt1Entity.getuVersions());
//                if(!coittEntity.isEmpty()){
//                    BomDTO newBom = new BomDTO();
//                    newBom.setProductCode(coittEntity.get(0).getuProNo());
//                    newBom.setDescription(coittEntity.get(0).getuProNam());
//                    newBom.setWarehouse(coittEntity.get(0).getuWhsCod());
//                    newBom.setDocUrl(coittEntity.get(0).getuDocUrl());
//                    newBom.setLevel(2);
//                    newBom.setRoot(bomDTO.getProductCode()+"_"+bomDTO.getVersion());
//                    newBom.setCreateTime(coittEntity.get(0).getCreateDate());
//                    newBom.setFromDate(coittEntity.get(0).getuFromDate());
//                    newBom.setGroupItem(101);
//                    newBom.setQuota(coittEntity.get(0).getuQuantity());
//                    newBom.setSpeciality(coittEntity.get(0).getuSpec());
//                    newBom.setRemark(coittEntity.get(0).getRemark());
//                    newBom.setToDate(coittEntity.get(0).getuToDate());
//                    newBom.setStatus(coittEntity.get(0).getuActive());
//                    newBom.setVersion(coittEntity.get(0).getuVersions());
//                    results.add(newBom);
//                }
//            }
//        }

        return new PageResponse<List<BomDTO>>()
            .result("00", "Thành công", true)
            .data(results);
    }

    @GetMapping("/get-bom/{productCode}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'MH', 'QLSX', 'VIEW','BOM')")
    public CommonResponse<List<BomItemDetailDTO>> getBomDetailV2(@PathVariable String productCode) {
        return bomService.getBomDetailV2(productCode);
    }
    /**
     * Lấy thông tin chi tiết BOM
     * @param productCode mã hàng hóa
     * @param version BOM Version
     * @return
     */
//    @GetMapping("/{productCode}/{version}")
//    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'MH', 'QLSX', 'VIEW','BOM')")
//    public CommonResponse<List<BomItemDetailDTO>> getBomDetail(@PathVariable String productCode, @PathVariable String version) {
//        return bomService.getBomDetail(productCode, version);
//    }

    @GetMapping("/{productCode}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'K', 'TK', 'HT', 'MH', 'QLSX', 'VIEW','BOM')")
    public CommonResponse<List<BomItemDetailDTO>> getBomDetail(@PathVariable String productCode) {
        return bomService.getBomDetail(productCode);
    }

    @GetMapping("/get-list-bom/{productCode}")
    public CommonResponse<List<String>> getListBom(@PathVariable String productCode) {
        return bomService.getListBom(productCode);
    }
}
