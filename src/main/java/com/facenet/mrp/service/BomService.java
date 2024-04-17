package com.facenet.mrp.service;

import com.facenet.mrp.domain.sap.CoittEntity;
import com.facenet.mrp.domain.sap.QCoittEntity;
import com.facenet.mrp.domain.sap.QOitmEntity;
import com.facenet.mrp.repository.sap.CoittRepository;
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

    public BomService(@Qualifier("sapEntityManager") EntityManager entityManager, CoittRepository coittRepository) {
        this.entityManager = entityManager;
        this.coittRepository = coittRepository;
    }

    /**
     * Lấy danh sách các BOM
     * @param bomDTO
     * @return
     */
    public PageResponse<List<BomDTO>> getAllBom(PageFilterInput<BomFilterInput> bomDTO) {
        Pageable pageable = PageRequest.of(bomDTO.getPageNumber(), bomDTO.getPageSize());
        BomFilterInput filter = bomDTO.getFilter();
        QCoittEntity qCoittEntity = QCoittEntity.coittEntity;
        QOitmEntity qOitmEntity = QOitmEntity.oitmEntity;
        JPAQuery<BomDTO> query = new JPAQueryFactory(entityManager)
            .select(new QBomDTO(
                qCoittEntity.uQuantity,
                qCoittEntity.uProNo,
                qCoittEntity.uProNam,
                qCoittEntity.uVersions,
                qCoittEntity.uSpec,
                qCoittEntity.uRemark,
                qCoittEntity.uDocUrl,
                qCoittEntity.uFromDate,
                qCoittEntity.uToDate,
                qCoittEntity.createDate,
                qCoittEntity.uActive,
                qOitmEntity.onHand,
                qCoittEntity.uWhsCod
            ))
            .from(qCoittEntity)
            .join(qOitmEntity).on(qCoittEntity.uProNo.eq(qOitmEntity.itemCode))
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset());
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qCoittEntity.uStatus.eq("A"));
        if (!StringUtils.isEmpty(filter.getProductCode())) {
            booleanBuilder.and(qCoittEntity.uProNo.containsIgnoreCase(filter.getProductCode()));
        }
        if (!StringUtils.isEmpty(filter.getDescription())) {
            booleanBuilder.and(qCoittEntity.uProNam.containsIgnoreCase(filter.getDescription()));
        }
        if (filter.getFromDate() != null) {
            booleanBuilder.and(qCoittEntity.uFromDate.eq(filter.getFromDate()));
        }
        if (filter.getToDate() != null) {
            booleanBuilder.and(qCoittEntity.uToDate.eq(filter.getToDate()));
        }
        if (filter.getCreateTime() != null) {
            booleanBuilder.and(qCoittEntity.createDate.eq(filter.getCreateTime()));
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

    public CommonResponse<List<BomItemDetailDTO>> getBomDetail(String productCode, String version) {
        List<BomItemDetailDTO> result = coittRepository.getAllItemsOfBom(productCode, version);

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

    public CommonResponse<List<BomItemDetailDTO>> getBomDetailV2(String productCode, String version) {
        List<BomItemDetailDTO> list = new ArrayList<>();
        dequy(new BomItemDetailDTO(),list,1,productCode, version);
        if (list == null || list.isEmpty()) throw new CustomException("record.notfound");
        return new CommonResponse<List<BomItemDetailDTO>>()
            .result("00", "Thành công", true)
            .data(list);
    }

    private List<BomItemDetailDTO> dequy(BomItemDetailDTO bomItemDetailDTO,List<BomItemDetailDTO> resultList,Integer count,String productCode, String version){
        List<String> itemList = new ArrayList<>();
        List<BomItemDetailDTO> list = new ArrayList<>();
        List<BomItemDetailDTO> result = coittRepository.getAllItemsOfBom(productCode, version);
        for(BomItemDetailDTO item: result){
            if(!itemList.contains(item.getMaterialCode())){
                itemList.add(item.getMaterialCode());
                item.setLevel(count);
                if(bomItemDetailDTO.getMaterialCode() != null){
                    item.setRootMaterial(bomItemDetailDTO.getMaterialCode());
                }
                list.add(item);
            }
        }
        resultList.addAll(list);
        for(BomItemDetailDTO item: list){
            if(item.getMaterialGroup().equals("BTP")){
                count ++;
                dequy(item,resultList,count,item.getMaterialCode(), item.getVersion());
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
