package com.facenet.mrp.service;

import com.facenet.mrp.domain.sap.CoittEntity;
import com.facenet.mrp.domain.sap.QOitmEntity;
import com.facenet.mrp.domain.sap.QOittEntity;
import com.facenet.mrp.repository.sap.CoittRepository;
import com.facenet.mrp.repository.sap.Itt1Repository;
import com.facenet.mrp.repository.sap.OitmRepository;
import com.facenet.mrp.repository.sap.OittRepository;
import com.facenet.mrp.service.dto.BomDTO;
import com.facenet.mrp.service.dto.BomItemDetailDTO;
import com.facenet.mrp.service.dto.BomItemDetailDraftDTO;
import com.facenet.mrp.service.dto.QBomDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.BomFilterInput;
import com.facenet.mrp.service.model.PageFilterInput;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class BomService {

    private final EntityManager entityManager;
    private final CoittRepository coittRepository;
    private final Itt1Repository itt1Repository;
    private final OittRepository oittRepository;
    private final OitmRepository oitmRepository;

    public BomService(@Qualifier("sapEntityManager") EntityManager entityManager, CoittRepository coittRepository, Itt1Repository itt1Repository,
                      OittRepository oittRepository,
                      OitmRepository oitmRepository) {
        this.entityManager = entityManager;
        this.coittRepository = coittRepository;
        this.itt1Repository = itt1Repository;
        this.oittRepository = oittRepository;
        this.oitmRepository = oitmRepository;
    }

    /**
     * Lấy danh sách các BOM
     * @param bomDTO
     * @return
     */
    public PageResponse<List<BomDTO>> getAllBom(PageFilterInput<BomFilterInput> bomDTO) {
        Pageable pageable = PageRequest.of(bomDTO.getPageNumber(), bomDTO.getPageSize());
        BomFilterInput filter = bomDTO.getFilter();
        QOittEntity qOittEntity = QOittEntity.oittEntity;
        QOitmEntity qOitmEntity = QOitmEntity.oitmEntity;
        JPAQuery<BomDTO> query = new JPAQueryFactory(entityManager)
            .select(new QBomDTO(
                qOittEntity.qauntity,
                qOittEntity.code,
                qOitmEntity.itemName,
                qOittEntity.createDate,
                qOittEntity.uStatus,
                qOitmEntity.onHand,
                qOittEntity.toWH,
                qOitmEntity.itmsGrpCod.itmsGrpCode
            ))
            .from(qOittEntity)
            .innerJoin(qOitmEntity).on(qOittEntity.code.eq(qOitmEntity.itemCode))
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset());
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qOittEntity.uStatus.eq("0"));
        if (!StringUtils.isEmpty(filter.getProductCode())) {
            booleanBuilder.and(qOittEntity.code.containsIgnoreCase(filter.getProductCode()));
        }
        if (!StringUtils.isEmpty(filter.getDescription())) {
            booleanBuilder.and(qOitmEntity.itemName.containsIgnoreCase(filter.getDescription()));
        }
        if (filter.getCreateTime() != null) {
            booleanBuilder.and(qOittEntity.createDate.eq(filter.getCreateTime()));
        }
        if (filter.getType() != null) {
            booleanBuilder.and(qOitmEntity.itmsGrpCod.itmsGrpCode.eq(filter.getType()));
        }
        query.where(booleanBuilder);
        List<BomDTO> result = query.fetch();
        long count = query.fetchCount();
        return new PageResponse<List<BomDTO>>()
            .result("00", "Thành công", true)
            .dataCount(count)
            .data(result);
    }

    /**
     * Lấy thông tin chi tiết của BOM
     * @param productCode mã hàng hóa
     * @param version BOM Version
     * @return
     */

//    public CommonResponse<List<BomItemDetailDTO>> getBomDetail(String productCode, String version) {
//        List<BomItemDetailDTO> result = coittRepository.getAllItemsOfBom(productCode, version);
//
//        List<String> itemList = new ArrayList<>();
//        List<BomItemDetailDTO> list = new ArrayList<>();
//        for(BomItemDetailDTO item: result){
//            if(!itemList.contains(item.getMaterialCode())){
//                itemList.add(item.getMaterialCode());
//                list.add(item);
//            }
//        }
//        if (result == null || result.isEmpty()) throw new CustomException("record.notfound");
//        return new CommonResponse<List<BomItemDetailDTO>>()
//            .result("00", "Thành công", true)
//            .data(list);
//    }

    public CommonResponse<List<BomItemDetailDTO>> getBomDetail(String productCode) {
        List<BomItemDetailDTO> result = itt1Repository.getAllItemsOfBom(productCode);

        List<String> itemList = new ArrayList<>();
        List<BomItemDetailDTO> list = new ArrayList<>();
        for(BomItemDetailDTO item: result){
            if(!itemList.contains(item.getMaterialCode())){
                itemList.add(item.getMaterialCode());
                list.add(item);
            }
        }
        if (result == null || result.isEmpty()) throw new CustomException("record.notfound");
        return new CommonResponse<List<BomItemDetailDTO>>()
            .result("00", "Thành công", true)
            .data(list);
    }

    public CommonResponse<List<BomItemDetailDTO>> getBomDetailV2(String productCode) {
        List<BomItemDetailDTO> list = new ArrayList<>();
        System.out.println("---------------"+productCode);
        dequy(new BomItemDetailDTO(),list,1,productCode);
        if (list == null || list.isEmpty()) throw new CustomException("record.notfound");
        return new CommonResponse<List<BomItemDetailDTO>>()
            .result("00", "Thành công", true)
            .data(list);
    }

    private List<BomItemDetailDTO> dequy(BomItemDetailDTO bomItemDetailDTO,List<BomItemDetailDTO> resultList,Integer count,String productCode){
        List<String> itemList = new ArrayList<>();
        List<BomItemDetailDTO> list = new ArrayList<>();
        List<BomItemDetailDTO> result = oittRepository.getAllItemsOfBom(productCode);
        for(BomItemDetailDTO item: result){
            if(!itemList.contains(item.getMaterialCode())){
                itemList.add(item.getMaterialCode());
                item.setUnit(oitmRepository.getByItemCode(item.getMaterialCode()).getSalUnitMsr());
                item.setLevel(count);
                if(count == 1){
                    item.setRootMaterial(productCode);
                }else if(bomItemDetailDTO.getMaterialCode() != null){
                    item.setRootMaterial(bomItemDetailDTO.getMaterialCode());
                }
                list.add(item);
            }
        }
        resultList.addAll(list);
        for(BomItemDetailDTO item: list){
            if(item.getMaterialGroup().equals("BTP") || item.getMaterialGroup().equals("TP")){
                dequy(item,resultList,count+1,item.getMaterialCode());
            }
        }
        return resultList;
    }

    /**
     * Lấy danh sách NVL, BTP của TP
     * @param productCode mã TP
     * @param version BOM Version của TP
     * @return
     */
    public CommonResponse<List<BomItemDetailDraftDTO>> getBomDetailDraf(String productCode, String version) {
        List<BomItemDetailDraftDTO> result = coittRepository.getAllItemsOfBomDraft(productCode, version);
        if (result == null || result.isEmpty()) throw new CustomException("record.notfound");
        dfs(result);
        return new CommonResponse<List<BomItemDetailDTO>>()
            .result("00", "Thành công", true)
            .data(result);
    }

    /**
     * Tìm NVL, BTP
     * @param items
     */
    private void dfs(List<BomItemDetailDraftDTO> items) {
        for (BomItemDetailDraftDTO item : items) {
            // nếu là BTP thì query tiếp con của nó
            if (item.getMaterialGroupInt() == 101) {
                item.setChildren(coittRepository.getAllItemsOfBomDraft(
                    item.getMaterialCode(),
                    item.getVersion()
                ));
                dfs(item.getChildren());
            }
        }
    }

    public CommonResponse<List<String>> getListBom(String productCode){
        List<String> versions = new ArrayList<>();
        List<CoittEntity> coittEntityList = coittRepository.getList(productCode);
        for (CoittEntity coittEntity: coittEntityList){
            if(coittEntity.getuVersions() != null &&
            !coittEntity.getuVersions().isEmpty()){
                versions.add(coittEntity.getuVersions());
            }
        }
        return new CommonResponse<List<String>>().result("00","Success",true).data(versions);
    }
}
