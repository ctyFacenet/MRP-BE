package com.facenet.mrp.service;

import com.facenet.mrp.domain.sap.*;
import com.facenet.mrp.repository.sap.OporRepository;
import com.facenet.mrp.service.dto.PurchaseRequestDTO;
import com.facenet.mrp.service.dto.QPurchaseRequestDTO;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.dto.sap.PrByItemSapDTO;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.PageFilterInput;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class PurchaseRequestService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseRequestService.class);

    private final EntityManager entityManager;

    public PurchaseRequestService(@Qualifier("sapEntityManager") EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public PageResponse<List<PurchaseRequestDTO>> getPurchaseRequestsWithPaging(PageFilterInput<PurchaseRequestDTO> input) {
        if (input == null || input.getPageNumber() == null || input.getPageSize() == null || input.getFilter() == null)
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.param");
        logger.info("Enter get purchase request function with [params={}]", input.getFilter());

        Pageable pageable;
        if (input.getPageSize() == 0) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
        }
        PurchaseRequestDTO filter = input.getFilter();
        QPdn1Entity qPdn1Entity = QPdn1Entity.pdn1Entity;
        QPor1Entity qPor1Entity = QPor1Entity.por1Entity;
        QOprqEntity qOprqEntity = QOprqEntity.oprqEntity;
        QPrq1Entity qPrq1Entity = QPrq1Entity.prq1Entity;
        QOcrdEntity qOcrdEntity = QOcrdEntity.ocrdEntity;
        JPAQuery<PurchaseRequestDTO> query = new JPAQueryFactory(entityManager)
            .select(
                new QPurchaseRequestDTO(
                    qPrq1Entity.uSo,
                    qPrq1Entity.uMcode,
                    qPrq1Entity.docEntry.stringValue().trim(),
                    qPor1Entity.docEntry.stringValue().trim(),
                    qPdn1Entity.docEntry.stringValue().trim(),
                    qPrq1Entity.itemCode,
                    qPrq1Entity.dscription,
                    qPrq1Entity.uTenkythuat,
                    qOcrdEntity.cardCode,
                    qOcrdEntity.cardName,
                    qOprqEntity.docDate,
                    qPrq1Entity.pqtReqDate,
                    qPrq1Entity.quantity,
                    qOprqEntity.reqName,
                    qPrq1Entity.lineStatus,
                    qPrq1Entity.openQty,
                    qPrq1Entity.lineNum
                )
            )
            .from(qPrq1Entity)
            .leftJoin(qOprqEntity).on(qOprqEntity.docEntry.eq(qPrq1Entity.docEntry))
            .leftJoin(qPor1Entity).on(
                qPor1Entity.baseEntry.eq(qPrq1Entity.docEntry)
                    .and(qPor1Entity.baseType.eq(1470000113))
                    .and(qPor1Entity.baseLine.eq(qPrq1Entity.lineNum))
            )
            .leftJoin(qPdn1Entity).on(
                qPdn1Entity.baseEntry.eq(qPor1Entity.docEntry)
                    .and(qPdn1Entity.baseType.eq(22))
                    .and(qPdn1Entity.baseLine.eq(qPor1Entity.lineNum))
            )
            .leftJoin(qOcrdEntity).on(qPrq1Entity.lineVendor.eq(qOcrdEntity.cardCode));
        if (pageable.isPaged()) {
            query = query.limit(pageable.getPageSize())
                .offset(pageable.getOffset());
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
//            booleanBuilder.and(qPrq1Entity.targetType.eq(22));
        if (!StringUtils.isEmpty(filter.getPrId())) {
            booleanBuilder.and(qPrq1Entity.docEntry.eq(Integer.valueOf(filter.getPrId())));
        }
        if (!StringUtils.isEmpty(filter.getPoId())) {
            booleanBuilder.and(qPor1Entity.docEntry.eq(Integer.valueOf(filter.getPoId())));
        }
        if (!StringUtils.isEmpty(filter.getGrpoId())) {
            booleanBuilder.and(qPdn1Entity.docEntry.eq(Integer.valueOf(filter.getGrpoId())));
        }
        if (!StringUtils.isEmpty(filter.getProductId())) {
            booleanBuilder.and(qPrq1Entity.itemCode.containsIgnoreCase(filter.getProductId()));
        }
        if (!StringUtils.isEmpty(filter.getVendor())) {
            booleanBuilder.and(qPrq1Entity.dscription.containsIgnoreCase(filter.getVendor()));
        }
        if (!StringUtils.isEmpty(filter.getTechnicalName())) {
            booleanBuilder.and(qPrq1Entity.uTenkythuat.containsIgnoreCase(filter.getTechnicalName()));
        }
        if (filter.getCreatedAt() != null) {
            booleanBuilder.and(qOprqEntity.docDate.eq(filter.getCreatedAt()).or(qOprqEntity.docDate.after(filter.getCreatedAt())));
        }
        if (!StringUtils.isEmpty(filter.getMrpId())) {
            booleanBuilder.and(qPrq1Entity.uMcode.containsIgnoreCase(filter.getMrpId()));
        }
        if (!StringUtils.isEmpty(filter.getSoCode())) {
            booleanBuilder.and(qPrq1Entity.uSo.containsIgnoreCase(filter.getSoCode()));
        }
        if (filter.getDeliverAt() != null) {
            booleanBuilder.and(qPrq1Entity.pqtReqDate.eq(filter.getDeliverAt()).or(qPrq1Entity.pqtReqDate.before(filter.getDeliverAt())));
        }
        if (!StringUtils.isEmpty(filter.getCreatedBy())) {
            booleanBuilder.and(qOprqEntity.reqName.containsIgnoreCase(filter.getCreatedBy()));
        }
        if (!StringUtils.isEmpty(filter.getStatus())) {
            booleanBuilder.and(qPrq1Entity.lineStatus.eq(filter.getStatus()));
        }
        if (filter.getSapUpdatedAt() != null) {
            booleanBuilder.and(qOprqEntity.updateDate.eq(filter.getSapUpdatedAt()));
        }
        query.where(booleanBuilder);
        query.orderBy(qPrq1Entity.docEntry.desc());
//            query.orderBy(qOprqEntity.docEntry.desc());
        List<PurchaseRequestDTO> result = query.fetch();
        return new PageResponse<List<PurchaseRequestDTO>>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(query.fetchCount())
            .data(result);
    }
}
