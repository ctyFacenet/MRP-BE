package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.QSaleEntity;
import com.facenet.mrp.domain.mrp.SaleEntity;
import com.facenet.mrp.domain.sap.Crd1Entity;
import com.facenet.mrp.domain.sap.OcrdEntity;
import com.facenet.mrp.domain.sap.QCrd1Entity;
import com.facenet.mrp.domain.sap.QOcrdEntity;
import com.facenet.mrp.repository.mrp.SaleRepository;
import com.facenet.mrp.repository.sap.OcrdRepository;
import com.facenet.mrp.service.dto.*;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * service xử lý nghiệp vụ lấy danh sách ncc và sale vendor kèm theo
 */
@Service
public class ListSaleService {
    private final Logger log = LogManager.getLogger(ListSaleService.class);

    @Autowired
    private OcrdRepository ocrdRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    @Qualifier("sapEntityManager")
    private EntityManager entityManagerSap;
    //hàm lấy ds ncc và sale vendor kèm theo
    public PageResponse<?> getAllData(PageFilterInput<DataVendorAndSale> input, Pageable pageable){
        List<DataVendorAndSale> listData = new ArrayList<>();
        PageResponse<List<ListVendorDTO>> listVendor = getAllVendor(input,pageable);
        List<ListVendorDTO> list = listVendor.getData();
        for (ListVendorDTO item:list
             ) {
            SaleEntity saleVendor = saleRepository.getAllSaleVendor(item.getVendorCode());
            DataVendorAndSale data = new DataVendorAndSale();
            data.setVendorCode(item.getVendorCode());
            data.setVendorName(item.getVendorName());
            data.setOtherName(item.getOtherName());
            data.setAddress(item.getAddress());
            data.setEmail(item.getEmail());
            data.setFaxNumber(item.getFaxNumber());
            data.setTransactionMoney(item.getTransactionMoney());
            data.setTaxCode(item.getTaxCode());
            data.setPhoneNumber(item.getPhoneNumber());
            if(item.getValidFor().equals("Y") && item.getFrozenFor().equals("N")){
                data.setStatus("active");
            }else {
                data.setStatus("inActive");
            }
            if(saleVendor != null){
                data.setSaleCode(saleVendor.getSaleCode());
                data.setSaleName(saleVendor.getSaleName());
            }else {
                data.setSaleCode("");
                data.setSaleName("");
            }
            log.info(data.getStatus());
            listData.add(data);
        }
        return new PageResponse<>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(listVendor.getDataCount())
            .data(listData);
    }
    //hàm search lấy danh sách ncc ở db sap
    public PageResponse<List<ListVendorDTO>> getAllVendor(PageFilterInput<DataVendorAndSale> input, Pageable pageable){
        DataVendorAndSale  filter = input.getFilter();
        QOcrdEntity qOcrdEntity = QOcrdEntity.ocrdEntity;
        QCrd1Entity qCrd1Entity = QCrd1Entity.crd1Entity;
        JPAQuery<ListVendorDTO> query = new JPAQueryFactory(entityManagerSap)
            .selectDistinct(
                new QListVendorDTO(
                    qOcrdEntity.cardCode,
                    qOcrdEntity.cardName,
                    qOcrdEntity.cartFName,
                    qOcrdEntity.EMail,
                    qOcrdEntity.address,
                    qOcrdEntity.currency,
                    qOcrdEntity.phone1,
                    qOcrdEntity.fax,
                    qCrd1Entity.taxCode,
                    qOcrdEntity.validFor,
                    qOcrdEntity.frozenFor
                )
            )
            .from(qOcrdEntity)
            .leftJoin(qCrd1Entity).on(qOcrdEntity.cardCode.eq(qCrd1Entity.cardCode))
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset());
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qOcrdEntity.cardType.eq("S"));
        if (!StringUtils.isEmpty(filter.getVendorCode())) {
            booleanBuilder.and(qOcrdEntity.cardCode.containsIgnoreCase(filter.getVendorCode()));
        }
        if (!StringUtils.isEmpty(filter.getVendorName())) {
            booleanBuilder.and(qOcrdEntity.cardName.containsIgnoreCase(filter.getVendorName()));
        }
        if (!StringUtils.isEmpty(filter.getOtherName())) {
            booleanBuilder.and(qOcrdEntity.cartFName.containsIgnoreCase(filter.getOtherName()));
        }
        if (!StringUtils.isEmpty(filter.getEmail())) {
            booleanBuilder.and(qOcrdEntity.EMail.containsIgnoreCase(filter.getEmail()));
        }
        if (!StringUtils.isEmpty(filter.getAddress())) {
            booleanBuilder.and(qOcrdEntity.address.containsIgnoreCase(filter.getAddress()));
        }
        query.where(booleanBuilder);
        List<ListVendorDTO> result = query.fetch();
        long count = query.fetchCount();
        return new PageResponse<List<ListVendorDTO>>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(count)
            .data(result);
    }
    //hàm search lấy danh sách sale vendor ở db mrp
    public PageResponse<Object> getAllSale(PageFilterInput<DataVendorAndSale> input, Pageable pageable){
        DataVendorAndSale  filter = input.getFilter();
        QSaleEntity qSaleEntity = QSaleEntity.saleEntity;
        JPAQuery<SaleVendorDTO> query = new JPAQueryFactory(entityManager)
            .selectDistinct(
                new QSaleVendorDTO(
                    qSaleEntity.saleCode,
                    qSaleEntity.saleName,
                    qSaleEntity.vendorCode
                )
            )
            .from(qSaleEntity)
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset());
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(Expressions.TRUE.isTrue());
        if (!StringUtils.isEmpty(filter.getSaleCode())) {
            booleanBuilder.and(qSaleEntity.saleCode.containsIgnoreCase(filter.getSaleCode()));
        }
        if (!StringUtils.isEmpty(filter.getSaleName())) {
            booleanBuilder.and(qSaleEntity.saleName.containsIgnoreCase(filter.getSaleName()));
        }
        query.where(booleanBuilder);
        List<SaleVendorDTO> result = query.fetch();
        long count = query.fetchCount();
        return new PageResponse<>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(count)
            .data(result);
    }
    //hàm lấy danh sách ncc search theo sale vendor ở db mrp
    public PageResponse<Object> getAllDataSource(PageFilterInput<DataVendorAndSale> input, Pageable pageable){
        List<DataVendorAndSale> listData = new ArrayList<DataVendorAndSale>();
        PageResponse<Object> listSale = getAllSale(input,pageable);
        List<SaleVendorDTO> list = (List<SaleVendorDTO>) listSale.getData();
        for (SaleVendorDTO item:list
        ) {
            DataVendorAndSale data = new DataVendorAndSale();
            OcrdEntity ocrdEntity = ocrdRepository.getVendor(item.getVendorCode());
            Crd1Entity crd1Entity = ocrdRepository.getTaxCode(item.getVendorCode());
            if(ocrdEntity != null && crd1Entity != null){
                data.setAddress(ocrdEntity.getAddress());
                data.setEmail(ocrdEntity.getEMail());
                data.setFaxNumber(ocrdEntity.getFax());
                data.setTransactionMoney(ocrdEntity.getCurrency());
                data.setPhoneNumber(ocrdEntity.getPhone1());
                data.setOtherName(ocrdEntity.getCartFName());
                data.setVendorName(ocrdEntity.getCardName());
                data.setTaxCode(crd1Entity.getTaxCode());
                data.setVendorCode(ocrdEntity.getCardCode());
                data.setSaleCode(item.getSaleCode());
                data.setSaleName(item.getSaleName());
                listData.add(data);
            }
        }
        return new PageResponse<>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(listSale.getDataCount())
            .data(listData);
    }
    //hàm update và create sale vendor
    public Integer updateSaleVendor(SaleVendorForm saleVendorForm,String vendorCode){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        SaleEntity saleEntity = saleRepository.getAllSaleVendor(vendorCode);
        if(saleEntity != null){
            saleEntity.setSaleCode(saleVendorForm.getSaleCode());
            saleEntity.setSaleName(saleVendorForm.getSaleName());
            saleEntity.setUpdatedAt(timestamp);
            saleRepository.save(saleEntity);
            return 1;
        }
        SaleEntity data = new SaleEntity();
        data.setVendorCode(vendorCode);
        data.setSaleCode(saleVendorForm.getSaleCode());
        data.setSaleName(saleVendorForm.getSaleName());
        data.setCreatedAt(timestamp);
        data.setIsActive((byte) 1);
        saleRepository.save(data);
        return 2;
    }
}
