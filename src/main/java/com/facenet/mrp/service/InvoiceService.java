package com.facenet.mrp.service;

import com.facenet.mrp.domain.sap.*;
import com.facenet.mrp.service.dto.InvoiceDTO;
import com.facenet.mrp.service.dto.PurchaseRequestDTO;
import com.facenet.mrp.service.dto.QInvoiceDTO;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.PageFilterInput;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class InvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseRequestService.class);

    private final EntityManager entityManager;

    public InvoiceService(@Qualifier("sapEntityManager") EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public PageResponse<List<InvoiceDTO>> getInvoiceWithPaging(PageFilterInput<InvoiceDTO> input) {
        if (input == null || input.getPageNumber() == null || input.getPageSize() == null || input.getFilter() == null)
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.param");
        logger.info("Enter get invoice function with [params={}]", input.getFilter());
        Pageable pageable;
        if (input.getPageSize() == 0) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
        }
        InvoiceDTO filter = input.getFilter();
        QOpchEntity qOpchEntity = QOpchEntity.opchEntity;
        QPch1Entity qPch1Entity = QPch1Entity.pch1Entity;
        QPor1Entity qPor1Entity = QPor1Entity.por1Entity;
        QPdn1Entity qPdn1Entity = QPdn1Entity.pdn1Entity;
        JPAQuery<InvoiceDTO> query = new JPAQueryFactory(entityManager)
            .select(
                new QInvoiceDTO(
                    qPch1Entity.uSo,
                    qPch1Entity.uMCode,
                    qPor1Entity.docEntry.stringValue(),
                    qPdn1Entity.docEntry.stringValue(),
                    qOpchEntity.docEntry.stringValue(),
                    qPch1Entity.itemCode,
                    qPch1Entity.dscription,
                    qOpchEntity.cardCode,
                    qOpchEntity.cardName,
                    qOpchEntity.uDeclarePd,
                    qOpchEntity.uInvCode,
                    qPch1Entity.quantity,
                    qPch1Entity.price,
                    qOpchEntity.uDeclareStat
                )
            )
            .from(qPch1Entity)
            .leftJoin(qOpchEntity).on(qOpchEntity.docEntry.eq(qPch1Entity.docEntry))
            .leftJoin(qPdn1Entity).on(
                qPch1Entity.baseEntry.eq(qPdn1Entity.docEntry)
                    .and(qPch1Entity.baseType.eq(20))
                    .and((qPch1Entity.baseLine).eq(qPdn1Entity.lineNum))
            )
            .leftJoin(qPor1Entity).on(
                qPdn1Entity.baseEntry.eq(qPor1Entity.docEntry)
                    .and(qPdn1Entity.baseType.eq(22))
                    .and(qPdn1Entity.baseLine.eq(qPor1Entity.lineNum))
            );

        if (pageable.isPaged()) {
            query = query.limit(pageable.getPageSize())
                .offset(pageable.getOffset());
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (!StringUtils.isEmpty(filter.getSoCode())) {
            booleanBuilder.and(qPch1Entity.uSo.containsIgnoreCase(filter.getSoCode()));
        }
        if (!StringUtils.isEmpty(filter.getMrpId())) {
            booleanBuilder.and(qPch1Entity.uMCode.containsIgnoreCase(filter.getMrpId()));
        }
        if (!StringUtils.isEmpty(filter.getPoId())) {
            booleanBuilder.and(qPor1Entity.docEntry.stringValue().containsIgnoreCase(filter.getPoId()));
        }
        if (!StringUtils.isEmpty(filter.getGrpoId())) {
            booleanBuilder.and(qPdn1Entity.docEntry.stringValue().containsIgnoreCase(filter.getGrpoId()));
        }
        if (!StringUtils.isEmpty(filter.getInvoiceId())) {
            booleanBuilder.and(qOpchEntity.docEntry.stringValue().containsIgnoreCase(filter.getInvoiceId()));
        }
        if (!StringUtils.isEmpty(filter.getProductId())) {
            booleanBuilder.and(qPch1Entity.itemCode.containsIgnoreCase(filter.getProductId()));
        }
        if (!StringUtils.isEmpty(filter.getDescription())) {
            booleanBuilder.and(qPch1Entity.dscription.containsIgnoreCase(filter.getDescription()));
        }
        if (!StringUtils.isEmpty(filter.getVendorCode())) {
            booleanBuilder.and(qOpchEntity.cardCode.containsIgnoreCase(filter.getVendorCode()));
        }
        if (!StringUtils.isEmpty(filter.getVendor())) {
            booleanBuilder.and(qOpchEntity.cardName.containsIgnoreCase(filter.getVendor()));
        }
        if (!StringUtils.isEmpty(filter.getCreatedAt())) {
            booleanBuilder.and(qOpchEntity.uDeclarePd.eq(filter.getCreatedAt()));
        }
        if (!StringUtils.isEmpty(filter.getInvCode())) {
            booleanBuilder.and(qOpchEntity.uInvCode.containsIgnoreCase(filter.getInvCode()));
        }
        if (!StringUtils.isEmpty(filter.getStatus())) {
            booleanBuilder.and(qOpchEntity.docStatus.eq(filter.getStatus()));
        }
        booleanBuilder.and(qOpchEntity.uDeclareStat.ne("2"));
        query.where(booleanBuilder);
        query.orderBy(qPch1Entity.docEntry.desc());

        List<InvoiceDTO> result = query.fetch();
        return new PageResponse<List<InvoiceDTO>>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(query.fetchCount())
            .data(result);
    }
}
