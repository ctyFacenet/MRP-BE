package com.facenet.mrp.repository.mrp.impl;

import com.facenet.mrp.domain.mrp.ProductOrder;
import com.facenet.mrp.domain.mrp.QProductOrder;
import com.facenet.mrp.repository.mrp.ProductOrderCustomRepository;
import com.facenet.mrp.service.dto.ProductOrderDto;
import com.facenet.mrp.service.dto.ReportDTO;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.ProductOrderMapper;
import com.facenet.mrp.service.model.ProductOrderFilter;
import com.facenet.mrp.service.model.ReportFilter;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ProductOrderRepositoryImpl implements ProductOrderCustomRepository {

    @Autowired
    @Qualifier("mrpEntityManager")
    private EntityManager em;

    @Autowired
    private ProductOrderMapper mapper;

    @Override
    public Page<ProductOrderDto> getProductOrderByStatus(Pageable pageable, ProductOrderFilter filter) {
        QProductOrder qProductOrder = QProductOrder.productOrder;
        JPAQuery<ProductOrder> query = new JPAQueryFactory(em)
            .selectFrom(qProductOrder)
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qProductOrder.isActive.eq((byte) 1));
        booleanBuilder.and(qProductOrder.status.goe(2));

        if (!StringUtils.isEmpty(filter.getPoCode())) {
            booleanBuilder.and(qProductOrder.productOrderCode.containsIgnoreCase(filter.getPoCode()));
        }
        if (!StringUtils.isEmpty(filter.getCustomerCode())) {
            booleanBuilder.and(qProductOrder.customerId.containsIgnoreCase(filter.getCustomerCode()));
        }
        if (!StringUtils.isEmpty(filter.getCustomerName())) {
            booleanBuilder.and(qProductOrder.customerName.containsIgnoreCase(filter.getCustomerName()));
        }
        if (!StringUtils.isEmpty(filter.getPoType())) {
            booleanBuilder.and(qProductOrder.productOrderType.containsIgnoreCase(filter.getPoType()));
        }
        if (filter.getOrderedTime() != null) {
            booleanBuilder.and(qProductOrder.orderDate.eq(filter.getOrderedTime()));
        }
        if (filter.getDeliveryTime() != null) {
            booleanBuilder.and(qProductOrder.deliverDate.eq(filter.getDeliveryTime()));
        }
        if (!StringUtils.isEmpty(filter.getSalesCode())) {
            booleanBuilder.and(qProductOrder.saleId.containsIgnoreCase(filter.getSalesCode()));
        }
        if (!StringUtils.isEmpty(filter.getSalesName())) {
            booleanBuilder.and(qProductOrder.saleName.containsIgnoreCase(filter.getSalesName()));
        }
        query.where(booleanBuilder).orderBy(qProductOrder.updatedAt.desc());
        List<ProductOrder> result = query.fetch();
        long count = query.fetchCount();
        if (CollectionUtils.isEmpty(result)) {
            throw new CustomException("record.notfound");

        } else {
            List<ProductOrderDto> resultDto = result.stream().map(productOrder -> mapper.entityToDto(productOrder))
                .collect(Collectors.toList());
            return new PageImpl<>(resultDto, pageable, count);
        }
    }

    @Override
    public List<ProductOrder> getReportBySoCode( ReportFilter filter) {

        Calendar cal = Calendar.getInstance();

        QProductOrder qProductOrder = QProductOrder.productOrder;
        JPAQuery<ProductOrder> query = new JPAQueryFactory(em)
            .selectFrom(qProductOrder);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qProductOrder.isActive.eq((byte) 1));
        booleanBuilder.and(qProductOrder.createdAt.loe(cal.getTime().toInstant()));

        cal.add(Calendar.MONTH, -3);
        booleanBuilder.and(qProductOrder.createdAt.goe(cal.getTime().toInstant()));

        if (!StringUtils.isEmpty(filter.getSoCode())) {
            booleanBuilder.and(qProductOrder.productOrderCode.containsIgnoreCase(filter.getSoCode()));
        }
        if (!StringUtils.isEmpty(filter.getCustomerCode())) {
            booleanBuilder.and(qProductOrder.customerId.containsIgnoreCase(filter.getCustomerCode()));
        }
        if (!StringUtils.isEmpty(filter.getCustomerName())) {
            booleanBuilder.and(qProductOrder.customerName.containsIgnoreCase(filter.getCustomerName()));
        }
        if (!StringUtils.isEmpty(filter.getSaleCode())) {
            booleanBuilder.and(qProductOrder.saleId.containsIgnoreCase(filter.getSaleCode()));
        }
        if (filter.getOrderDate() != null) {
            booleanBuilder.and(qProductOrder.orderDate.goe(filter.getOrderDate()));
        }
        if (filter.getDeliveryDate() != null) {
            booleanBuilder.and(qProductOrder.deliverDate.loe(filter.getDeliveryDate()));
        }

        query.where(booleanBuilder).orderBy(qProductOrder.updatedAt.desc());
        List<ProductOrder> result = query.fetch();
        if (CollectionUtils.isEmpty(result)) {
            throw new CustomException("record.notfound");
        } else {
            return result;
        }
    }


}
