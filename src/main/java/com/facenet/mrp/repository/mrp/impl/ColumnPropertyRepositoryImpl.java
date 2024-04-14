package com.facenet.mrp.repository.mrp.impl;


import com.facenet.mrp.domain.mrp.ColumnPropertyCustomRepository;
import com.facenet.mrp.domain.mrp.ColumnPropertyEntity;
import com.facenet.mrp.domain.mrp.QColumnPropertyEntity;
import com.facenet.mrp.service.dto.KeyDictionaryDTO;
import com.facenet.mrp.service.utils.Constants;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ColumnPropertyRepositoryImpl implements ColumnPropertyCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ColumnPropertyEntity> getAllColumn(KeyDictionaryDTO input, String common, Pageable pageable) {
        String keyTitle = input.getKeyTitle();
        QColumnPropertyEntity qColumnPropertyEntity = QColumnPropertyEntity.columnPropertyEntity;
        JPAQuery<ColumnPropertyEntity> query = new JPAQueryFactory(em).selectFrom(qColumnPropertyEntity);
        if (pageable.isPaged()) {
            query = query.limit(pageable.getPageSize()).offset(pageable.getOffset());
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanBuilder booleanBuilder2 = new BooleanBuilder();
        booleanBuilder.and(qColumnPropertyEntity.isActive.eq(true));
        if (input.getEntityType() != null) booleanBuilder.and(qColumnPropertyEntity.entityType.eq(input.getEntityType()));
        if (!StringUtils.isEmpty(common)) {
            booleanBuilder2.or(qColumnPropertyEntity.keyTitle.containsIgnoreCase(common));
            if ("Không bắt buộc".toLowerCase().contains(common.toLowerCase())) booleanBuilder2.or(
                qColumnPropertyEntity.isRequired.eq(false)
            );
            if ("Bắt buộc".toLowerCase().contains(common.toLowerCase())) booleanBuilder2.or(qColumnPropertyEntity.isRequired.eq(true));
            if ("Không hiển thị".toLowerCase().contains(common.toLowerCase())) booleanBuilder2.or(qColumnPropertyEntity.check.eq(false));
            if ("Hiển thị".toLowerCase().contains(common.toLowerCase())) booleanBuilder2.or(qColumnPropertyEntity.check.eq(true));
            if ("Integer".toLowerCase().contains(common.toLowerCase())) booleanBuilder2.or(
                qColumnPropertyEntity.dataType.eq(Constants.INT_VALUE)
            );
            if ("Float".toLowerCase().contains(common.toLowerCase())) booleanBuilder2.or(
                qColumnPropertyEntity.dataType.eq(Constants.FLOAT_VALUE)
            );
            if ("String".toLowerCase().contains(common.toLowerCase())) booleanBuilder2.or(
                qColumnPropertyEntity.dataType.eq(Constants.STRING_VALUE)
            );
            if ("Json".toLowerCase().contains(common.toLowerCase())) booleanBuilder2.or(
                qColumnPropertyEntity.dataType.eq(Constants.JSON_VALUE)
            );
            if ("Date".toLowerCase().contains(common.toLowerCase())) booleanBuilder2.or(
                qColumnPropertyEntity.dataType.eq(Constants.DATE_VALUE)
            );
            if ("Boolean".toLowerCase().contains(common.toLowerCase())) booleanBuilder2.or(
                qColumnPropertyEntity.dataType.eq(Constants.BOOLEAN_VALUE)
            );
        }
        if (!StringUtils.isEmpty(keyTitle)) booleanBuilder.and(qColumnPropertyEntity.keyTitle.containsIgnoreCase(keyTitle));
        if (input.getCheck() != null) {
            booleanBuilder.and(qColumnPropertyEntity.check.eq(input.getCheck()));
        }
        if (input.getIsRequired() != null) {
            booleanBuilder.and(qColumnPropertyEntity.isRequired.eq(input.getIsRequired()));
        }
        if (input.getDataType() != null) {
            booleanBuilder.and(qColumnPropertyEntity.dataType.eq(input.getDataType()));
        }
        query.where(booleanBuilder.and(booleanBuilder2)).orderBy(qColumnPropertyEntity.index.asc());
        List<ColumnPropertyEntity> list = query.fetch();
        return list;
    }
}
