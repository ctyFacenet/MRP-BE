package com.facenet.mrp.service;

import com.facenet.mrp.domain.sap.*;
import com.facenet.mrp.repository.sap.OporRepository;
import com.facenet.mrp.service.dto.GrpoDTO;
import com.facenet.mrp.service.dto.QGrpoDTO;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.PageFilterInput;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class GrpoService {
    private final Logger log = LogManager.getLogger(CoittServiceImpl.class);

    @Value("${query.limit.month-ago}")
    private int limitMonth;

    @Autowired
    private OporRepository oporRepository;

    @Autowired
    @Qualifier("sapEntityManager")
    private EntityManager entityManager;

    public PageResponse<List<GrpoDTO>> getAllGrpo(PageFilterInput<GrpoDTO> input, Pageable pageable) {
        GrpoDTO filter = input.getFilter();

        QPdn1Entity qPdn1Entity = QPdn1Entity.pdn1Entity;
        QOpdnEntity qOpdnEntity = QOpdnEntity.opdnEntity;
        QPor1Entity qPor1Entity = QPor1Entity.por1Entity;
        QPrq1Entity qPrq1Entity = QPrq1Entity.prq1Entity;
        JPAQuery<GrpoDTO> query = new JPAQueryFactory(entityManager)
            .select(
                new QGrpoDTO(
                    qPdn1Entity.currency,
                    qOpdnEntity.uInvCode,
//                    qPdn1Entity.uSo,
                    qPor1Entity.docEntry.stringValue().trim(),
                    qPrq1Entity.docEntry.stringValue().trim(),
                    qPdn1Entity.docEntry.stringValue().trim(),
                    qPdn1Entity.itemCode,
//                    qPdn1Entity.uMCode,
                    qPdn1Entity.dscription,
                    qOpdnEntity.cardCode,
                    qOpdnEntity.cardName,
                    qPdn1Entity.lineStatus,
                    qPdn1Entity.uTenkythuat,
                    qOpdnEntity.taxDate,
                    qPdn1Entity.taxType,
                    qPdn1Entity.taxCode,
                    qPdn1Entity.quantity,
                    qPdn1Entity.lineTotal,
                    qPdn1Entity.price,
                    qPdn1Entity.lineNum
                )
            )
            .from(qPdn1Entity)
            .leftJoin(qOpdnEntity).on(qOpdnEntity.docEntry.eq(qPdn1Entity.docEntry))
            .leftJoin(qPor1Entity).on(
                qPdn1Entity.baseEntry.eq(qPor1Entity.docEntry)
                    .and(qPdn1Entity.baseType.eq(22))
                    .and(qPdn1Entity.baseLine.eq(qPor1Entity.lineNum)
                    )
            )
//            .leftJoin(qPrq1Entity).on(
//                qPrq1Entity.docEntry.eq(qPor1Entity.baseEntry)
//                    .and(qPrq1Entity.lineNum.eq(qPor1Entity.baseLine))
//            )
            .leftJoin(qPrq1Entity).on(
                qPor1Entity.baseEntry.eq(qPrq1Entity.docEntry)
                    .and(qPor1Entity.baseType.eq(1470000113))
                    .and(qPor1Entity.baseLine.eq(qPrq1Entity.lineNum))
            );
        if (pageable.isPaged()) {
            query = query.limit(pageable.getPageSize()).offset(pageable.getOffset());
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(Expressions.TRUE.isTrue());

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
            booleanBuilder.and(qPdn1Entity.itemCode.containsIgnoreCase(filter.getProductId()));
        }
        if (!StringUtils.isEmpty(filter.getDescription())) {
            booleanBuilder.and(qPdn1Entity.dscription.containsIgnoreCase(filter.getDescription()));
        }
        if (!StringUtils.isEmpty(filter.getVendor())) {
            booleanBuilder.and(qOpdnEntity.cardName.containsIgnoreCase(filter.getVendor()));
        }

//        if (!StringUtils.isEmpty(filter.getSoId())) {
//            booleanBuilder.and(qPdn1Entity.uSo.containsIgnoreCase(filter.getSoId()));
//        }
//        if (!StringUtils.isEmpty(filter.getMrpCode())) {
//            booleanBuilder.and(qPdn1Entity.uMCode.containsIgnoreCase(filter.getMrpCode()));
//        }
        if (!StringUtils.isEmpty(filter.getReceiptId())) {
            booleanBuilder.and(qOpdnEntity.uInvCode.containsIgnoreCase(filter.getReceiptId()));
        }
        if (!StringUtils.isEmpty(filter.getTechName())) {
            booleanBuilder.and(qPdn1Entity.uTenkythuat.containsIgnoreCase(filter.getTechName()));
        }
//        if (!StringUtils.isEmpty(filter.getStatus())) {
//            booleanBuilder.and(qOpdnEntity.uDeclareStat.containsIgnoreCase(filter.getStatus()));
//            check = true;
//        }
        if (filter.getCreatedAt() != null) {
            booleanBuilder.and((qOpdnEntity.docDate.eq(filter.getCreatedAt())).or(qOpdnEntity.docDate.after(filter.getCreatedAt())));
        }
        if (filter.getWareHouseDate() != null) {
            booleanBuilder.and((qOpdnEntity.docDueDate.eq(filter.getWareHouseDate())).or(qOpdnEntity.docDueDate.after(filter.getWareHouseDate())));
        }
        if (!StringUtils.isEmpty(filter.getStatus())) {
            booleanBuilder.and(qPdn1Entity.lineStatus.eq(filter.getStatus()));
        }
        if (filter.getSapUpdatedAt() != null) {
            booleanBuilder.and(qOpdnEntity.updateDate.eq(filter.getSapUpdatedAt()));
        }
        query.where(booleanBuilder);
        query.orderBy(qPdn1Entity.docEntry.desc());
        List<GrpoDTO> result = query.fetch();

        return new PageResponse<List<GrpoDTO>>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(query.fetchCount())
            .data(result);
    }
}
