package com.facenet.mrp.repository.sap.impl;

import com.facenet.mrp.domain.sap.*;
import com.facenet.mrp.repository.sap.OporRepository;
import com.facenet.mrp.repository.sap.PoCustomRepository;
import com.facenet.mrp.service.dto.PoDto;
import com.facenet.mrp.service.dto.PurchaseRequestDTO;
import com.facenet.mrp.service.dto.QPoDto;
import com.facenet.mrp.service.model.PoFilter;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class PoRepositoryImpl implements PoCustomRepository {

    private static final Logger logger = LoggerFactory.getLogger(PoRepositoryImpl.class);
    @Autowired
    private OporRepository oporRepository;

    @Autowired
    @Qualifier("sapEntityManager")
    private EntityManager em;

    @Override
    public Page<PoDto> getAllPoList(Pageable pageable, PoFilter poFilter) {

        QPdn1Entity qPdn1Entity = QPdn1Entity.pdn1Entity;
        QOporEntity qOporEntity = QOporEntity.oporEntity;
        QPor1Entity qPor1Entity = QPor1Entity.por1Entity;
        QPrq1Entity qPrq1Entity = QPrq1Entity.prq1Entity;
        QOusrEntity qOusrEntity = QOusrEntity.ousrEntity;

        JPAQuery<PoDto> query = new JPAQueryFactory(em)
            .select(new QPoDto(
                qPrq1Entity.docEntry.stringValue().trim(),
                qPor1Entity.docEntry.stringValue().trim(),
                qPdn1Entity.docEntry.stringValue().trim(),
                qPor1Entity.uSo,
                qPor1Entity.uMcode,
                qOporEntity.uCodeInv,
                qOporEntity.uContractDate,
                qPor1Entity.itemCode,
                qPor1Entity.dscription,
                qPor1Entity.uTenkythuat,
                qOporEntity.cardCode,
                qOporEntity.cardName,
                qOusrEntity.uName,
                qOporEntity.docDate,
                qOporEntity.docDueDate,
                qPor1Entity.quantity,
                qPor1Entity.price,
                qPor1Entity.currency,
                qPor1Entity.lineStatus,
                qPor1Entity.openQty,
                qPor1Entity.lineNum
            ))
            .from(qPor1Entity)
            .leftJoin(qOporEntity).on(qPor1Entity.docEntry.eq(qOporEntity.docEntry))
            .leftJoin(qPdn1Entity).on(
                qPdn1Entity.baseEntry.eq(qPor1Entity.docEntry)
                    .and(qPdn1Entity.baseType.eq(22))
                    .and(qPdn1Entity.baseLine.eq(qPor1Entity.lineNum))
            )
//            .leftJoin(qPrq1Entity).on(
//                qPrq1Entity.docEntry.eq(qPor1Entity.baseEntry)
//                    .and(qPrq1Entity.lineNum.eq(qPor1Entity.baseLine))
//            );
            .leftJoin(qPrq1Entity).on(
                qPor1Entity.baseEntry.eq(qPrq1Entity.docEntry)
                    .and(qPor1Entity.baseType.eq(1470000113))
                    .and(qPor1Entity.baseLine.eq(qPor1Entity.baseLine))
            ).join(qOusrEntity).on(qOporEntity.userSign.eq(qOusrEntity.userid));
        if (pageable.isPaged()) {
            query = query.limit(pageable.getPageSize()).offset(pageable.getOffset());
        }
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(Expressions.TRUE.isTrue());

        if (!StringUtils.isEmpty(poFilter.getPoCode())) {
            builder.and(qPor1Entity.docEntry.eq(Integer.valueOf(poFilter.getPoCode())));
        }
        if (!StringUtils.isEmpty(poFilter.getTechnicalName())) {
            builder.and(qPor1Entity.uTenkythuat.containsIgnoreCase(poFilter.getTechnicalName()));
        }
        if (!StringUtils.isEmpty(poFilter.getProductCode())) {
            builder.and(qPor1Entity.itemCode.containsIgnoreCase(poFilter.getProductCode()));
        }
        if (!StringUtils.isEmpty(poFilter.getProductName())) {
            builder.and(qPor1Entity.dscription.containsIgnoreCase(poFilter.getProductName()));
        }
        if (!StringUtils.isEmpty(poFilter.getGrpoCode())) {
            builder.and(qPdn1Entity.docEntry.eq(Integer.valueOf(poFilter.getGrpoCode())));
        }
        if (!StringUtils.isEmpty(poFilter.getContractCode())) {
            builder.and(qOporEntity.uCodeInv.containsIgnoreCase(poFilter.getContractCode()));
        }
        if (!StringUtils.isEmpty(poFilter.getVendor())) {
            builder.and(qOporEntity.cardName.containsIgnoreCase(poFilter.getVendor()));
        }
        if (poFilter.getCreatedAt() != null) {
            builder.and((qOporEntity.docDate.eq(poFilter.getCreatedAt())).or(qOporEntity.docDate.after(poFilter.getCreatedAt())));
        }
        if (!StringUtils.isEmpty(poFilter.getSoCode())) {
            builder.and(qPor1Entity.uSo.containsIgnoreCase(poFilter.getSoCode()));
        }
        if (poFilter.getReceivedDate() != null) {
            builder.and((qOporEntity.docDueDate.eq(poFilter.getReceivedDate())).or(qOporEntity.docDueDate.before(poFilter.getReceivedDate())));
        }
        if (!StringUtils.isEmpty(poFilter.getStatus())) {
            builder.and(qPor1Entity.lineStatus.eq(poFilter.getStatus()));
        }
        if (poFilter.getSapUpdatedAt() != null) {
            builder.and(qOporEntity.updateDate.eq(poFilter.getSapUpdatedAt()));
        }

        query.where(builder);
        query.orderBy(qPor1Entity.docEntry.desc());

        List<PoDto> poDtoList = query.fetch();

        return new PageImpl<>(poDtoList, pageable,  query.fetchCount());
    }
}
