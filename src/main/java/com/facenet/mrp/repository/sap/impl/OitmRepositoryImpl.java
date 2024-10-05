package com.facenet.mrp.repository.sap.impl;

import com.facenet.mrp.domain.mrp.QItemEntity;
import com.facenet.mrp.domain.mrp.QVendorEntity;
import com.facenet.mrp.domain.mrp.QVendorItemEntity;
import com.facenet.mrp.domain.mrp.VendorItemEntity;
import com.facenet.mrp.domain.sap.*;
import com.facenet.mrp.repository.mrp.VendorItemRepository;
import com.facenet.mrp.repository.sap.OcrdRepository;
import com.facenet.mrp.repository.sap.OitmCustomRepository;
import com.facenet.mrp.repository.sap.OitmEntityRepository;
import com.facenet.mrp.service.WarehouseChosenService;
import com.facenet.mrp.service.dto.ItemWithTypeDTO;
import com.facenet.mrp.service.dto.ListOfUnitPricesDTO;
import com.facenet.mrp.service.dto.QListOfUnitPricesDTO;
import com.facenet.mrp.service.model.ListOfUnitPricesFilter;
import com.facenet.mrp.service.model.OitmFilter;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class OitmRepositoryImpl implements OitmCustomRepository {
    @Autowired
    @Qualifier("mrpEntityManager")
    private EntityManager mrpEntityManager;

    @Autowired
    @Qualifier("sapEntityManager")
    private EntityManager sapEntityManager;

    @Autowired
    private VendorItemRepository vendorItemRepository;
    @Autowired
    private OcrdRepository ocrdRepository;
    @Autowired
    private OitmEntityRepository oitmEntityRepository;
    @Autowired
    private WarehouseChosenService warehouseChosenService;

    @Override
    public Page<OitmEntity> getOitmList(Pageable pageable, OitmFilter oitmFilter) {
        CriteriaBuilder cb = sapEntityManager.getCriteriaBuilder();
        CriteriaQuery<OitmEntity> cq = cb.createQuery(OitmEntity.class);

        List<Predicate> predicates ;
        Root<OitmEntity> oitm = cq.from(OitmEntity.class);
        predicates = getPredicates(oitm,cb,oitmFilter);

        List<String> warehouse = warehouseChosenService.getWarehouseCodes(1);

        // Thêm điều kiện WhsCode phải thuộc list<String> warehouse
        if (warehouse != null && !warehouse.isEmpty()) {
            Join<OitmEntity, OitwEntity> oitwJoin = oitm.join(OitmEntity_.OITW_ENTITIES);
            predicates.add(oitwJoin.get(OitwEntity_.WHS_CODE).in(warehouse));
        }

        cq.multiselect(oitm
            ,cb.sum(oitm.join(OitmEntity_.OITW_ENTITIES).get(OitwEntity_.ON_HAND)));

        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        cq.groupBy(oitm);
        TypedQuery<OitmEntity> query = sapEntityManager.createQuery(cq);

        //get list
        List<OitmEntity> resultList = query
            .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
            .setMaxResults(pageable.getPageSize())
            .getResultList();

        //get count
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<OitmEntity> countRoot = criteriaQuery.from(OitmEntity.class);
        criteriaQuery.select(cb.count(countRoot));
        predicates = getPredicates(countRoot,cb,oitmFilter);
        criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
        Long count = sapEntityManager.createQuery(criteriaQuery).getSingleResult();

        return new PageImpl<>(resultList, pageable, count);
    }

    private List<Predicate> getPredicates(Root<OitmEntity> oitm,CriteriaBuilder cb, OitmFilter oitmFilter){
        List<Predicate> predicates = new ArrayList<>();
//        if (!StringUtils.isEmpty(oitmFilter.getPartNumber())) {
//            predicates.add(cb.like(
//                cb.lower(oitm.get(OitmEntity_.U_PART_NUMBER)), "%" + oitmFilter.getPartNumber().toUpperCase() + "%"));
//        }
        if (!StringUtils.isEmpty(oitmFilter.getProductId())) {
            predicates.add(cb.like(
                cb.lower(oitm.get(OitmEntity_.ITEM_CODE)), "%" + oitmFilter.getProductId().toLowerCase() + "%"));
        }
        if (!StringUtils.isEmpty(oitmFilter.getName())) {
            predicates.add(cb.like(
                cb.lower(oitm.get(OitmEntity_.ITEM_NAME)), "%" + oitmFilter.getName().toLowerCase() + "%"));
        }
        if (!StringUtils.isEmpty(oitmFilter.getSapType())) {
            Join<OitmEntity, OitbEntity> join = oitm.join(OitmEntity_.ITMS_GRP_COD);
            if ("S".equalsIgnoreCase(oitmFilter.getSapType())) {
                predicates.add(cb.equal(join.get(OitbEntity_.ITMS_GRP_CODE), 104));
            } else if("P".equalsIgnoreCase(oitmFilter.getSapType())){
                predicates.add(cb.notEqual(join.get(OitbEntity_.ITMS_GRP_CODE), 104));
            }
        }
        if (!StringUtils.isEmpty(oitmFilter.getGroupName())) {
            predicates.add(cb.like(
                cb.lower(oitm.get(OitmEntity_.U_IGROUP_NAME)), "%" + oitmFilter.getGroupName().toLowerCase() + "%"));
        }
        if (!StringUtils.isEmpty(oitmFilter.getGroup())) {
            Join<OitmEntity, OitbEntity> join = oitm.join(OitmEntity_.ITMS_GRP_COD);
            predicates.add(cb.like(
                cb.lower(join.get(OitbEntity_.ITMS_GRP_NAME)), "%" + oitmFilter.getGroup().toLowerCase() + "%")
            );
        }
        if (!StringUtils.isEmpty(oitmFilter.getUnit())) {
            predicates.add(cb.like(
                cb.lower(oitm.get(OitmEntity_.BUY_UNIT_MSR)), "%" + oitmFilter.getUnit().toLowerCase() + "%"));
        }
        if (!StringUtils.isEmpty(oitmFilter.getTechnicalName())) {
            predicates.add(cb.like(
                (oitm.get(OitmEntity_.U_TECH_NAME)), "%" + oitmFilter.getTechnicalName() + "%"));
        }
        return predicates;
    }

    @Override
    public Page<ListOfUnitPricesDTO> getVendorItemFromSap(Pageable pageable, ListOfUnitPricesFilter filter) {
        QVendorItemEntity qVendorItemEntity = QVendorItemEntity.vendorItemEntity;
        QItemEntity qItemEntity = QItemEntity.itemEntity;
        QVendorEntity qVendorEntity = QVendorEntity.vendorEntity;
        JPAQuery<ListOfUnitPricesDTO> vendorItemQuery = new JPAQueryFactory(mrpEntityManager)
            .select(new QListOfUnitPricesDTO(
                qVendorItemEntity.itemCode,
                qItemEntity.itemName,
                qVendorItemEntity.vendorCode,
                qVendorEntity.vendorName
            )).from(qVendorItemEntity)
            .leftJoin(qItemEntity).on(qVendorItemEntity.itemCode.eq(qItemEntity.itemCode))
            .leftJoin(qVendorEntity).on(qVendorItemEntity.vendorCode.eq(qVendorEntity.vendorCode))
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset());
        BooleanBuilder vendorItemWhere = new BooleanBuilder();
        if (!StringUtils.isEmpty(filter.getVendorCode())) {
            vendorItemWhere.and(qVendorItemEntity.vendorCode.containsIgnoreCase(filter.getVendorCode()));
        }
        if (!StringUtils.isEmpty(filter.getProductCode())) {
            vendorItemWhere.and(qVendorItemEntity.itemCode.containsIgnoreCase(filter.getProductCode()));
        }
        if (!StringUtils.isEmpty(filter.getProductDescription())) {
            vendorItemWhere.and(qItemEntity.itemName.containsIgnoreCase(filter.getProductDescription()));
        }
        if (!StringUtils.isEmpty(filter.getVendorName())) {
            vendorItemWhere.and(qVendorEntity.vendorName.containsIgnoreCase(filter.getVendorName()));
        }
        vendorItemQuery.where(vendorItemWhere);
        vendorItemQuery.limit(pageable.getPageSize());
        vendorItemQuery.offset(pageable.getOffset());
        List<ListOfUnitPricesDTO> result = vendorItemQuery.fetch();
        long count = vendorItemQuery.fetchCount();

        List<String> itemList = new ArrayList<>();
        for (ListOfUnitPricesDTO vendorItemEntity : result) {
            itemList.add(vendorItemEntity.getProductCode());
        }

        Map<String, ItemWithTypeDTO> itemNameMap = oitmEntityRepository.itemMap(itemList);
        for (ListOfUnitPricesDTO item : result) {
            if (itemNameMap.containsKey(item.getProductCode())) {
                ItemWithTypeDTO itemDetail = itemNameMap.get(item.getProductCode());
                item.setGroup(itemDetail.getGroupItem());
                item.setUnit(itemDetail.getUnit());
            }
        }

//        JPAQuery<ListOfUnitPricesDTO> query = new JPAQueryFactory(em)
//            .select(new QListOfUnitPricesDTO(
//                qOitm.itemCode,
//                qOitm.itemName,
//                qOitm.itmsGrpCod.itmsGrpName,
//                qOcrd.cardCode,
//                qOcrd.cardName,
//                qOitm.buyUnitMsr,
//                qOcrd.currency
//            ))
////            .from(qOcrd, qOitm, qItm2)
//            .from(qOcrd, qOitm)
//            .limit(pageable.getPageSize())
//            .offset(pageable.getOffset());
//
//        builder.and(qOitm.itemCode.eq(qItm2.itemCode))
//            .and(qItm2.vendorCode.eq(qOcrd.cardCode));
//        BooleanBuilder builder = new BooleanBuilder();
//        if (!StringUtils.isEmpty(filter.getVendorCode())){
//            builder.and(qOcrd.cardCode.containsIgnoreCase(filter.getVendorCode()));
//        }
//        if (!StringUtils.isEmpty(filter.getVendorName())){
//            builder.and(qOcrd.cardName.containsIgnoreCase(filter.getVendorName()));
//        }
//        if (!StringUtils.isEmpty(filter.getProductCode())){
//            builder.and(qOitm.itemCode.containsIgnoreCase(filter.getProductCode()));
//        }
//        if (!StringUtils.isEmpty(filter.getProductDescription())){
//            builder.and(qOitm.itemName.containsIgnoreCase(filter.getProductDescription()));
//        }
//
//        query.where(builder);
//
//        List<ListOfUnitPricesDTO> result = query.fetch();
//        long count = query.fetchCount();

        return new PageImpl<>(result, pageable, count);
    }
}
