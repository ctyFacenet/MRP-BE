package com.facenet.mrp.repository.mrp.impl;

import com.facenet.mrp.domain.mrp.LeadTimeEntity;
import com.facenet.mrp.domain.mrp.QLeadTimeEntity;
import com.facenet.mrp.domain.mrp.QMqqPriceEntity;
import com.facenet.mrp.repository.mrp.MqqPriceCustomRepository;
import com.facenet.mrp.repository.sap.impl.PoRepositoryImpl;
import com.facenet.mrp.service.dto.MqqLeadTimeDTO;
import com.facenet.mrp.service.dto.MqqPriceDTO;
import com.facenet.mrp.service.dto.QMqqPriceDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.EntityManager;
import java.util.List;

public class MqqPriceRepositoryImpl implements MqqPriceCustomRepository {

    private static final Logger logger = LoggerFactory.getLogger(PoRepositoryImpl.class);

    @Autowired
    @Qualifier("mrpEntityManager")
    private EntityManager em;

    @Override
    public MqqLeadTimeDTO findMqqPriceAndLeadTime(String vendorCode, String itemCode) {

        QMqqPriceEntity qMqqPriceEntity = QMqqPriceEntity.mqqPriceEntity;
        QLeadTimeEntity qLeadTimeEntity = QLeadTimeEntity.leadTimeEntity;
        BooleanBuilder builder1 = new BooleanBuilder();
        BooleanBuilder builder2 = new BooleanBuilder();

        MqqLeadTimeDTO dto = new MqqLeadTimeDTO();

        //Query lấy list MqqPrice
        JPAQuery<MqqPriceDTO> query = new JPAQueryFactory(em)
            .select(new QMqqPriceDTO(
                qMqqPriceEntity.itemPriceId,
                qMqqPriceEntity.rangeStart,
                qMqqPriceEntity.rangeEnd,
                qMqqPriceEntity.price,
                qMqqPriceEntity.currency,
                qMqqPriceEntity.timeStart,
                qMqqPriceEntity.timeEnd,
                qMqqPriceEntity.note
            ))
            .from(qMqqPriceEntity)
            .where(builder1
                .and(qMqqPriceEntity.vendorCode.eq(vendorCode))
                .and(qMqqPriceEntity.itemCode.eq(itemCode))
                .and(qMqqPriceEntity.isPromotion.eq(false))
                .and(qMqqPriceEntity.isActive.eq((byte) 1)));

        List<MqqPriceDTO> mqqPriceDTOS = query.fetch();

        //Query lấy LeadTime
        JPAQuery<LeadTimeEntity> qr = new JPAQueryFactory(em)
            .selectDistinct(qLeadTimeEntity)
            .from(qLeadTimeEntity).
            where(builder2
                .and(qLeadTimeEntity.vendorCode.eq(vendorCode))
                .and(qLeadTimeEntity.itemCode.eq(itemCode))
                .and(qLeadTimeEntity.isActive.eq((byte) 1)));

        LeadTimeEntity leadTimeEntity = qr.fetchFirst();

        //Ghép 2 DTO của LeadTime Và MqqPrice vào thành LeadTimeMqqPriceDTO
        if (leadTimeEntity != null){
            dto.setLeadTimeId(leadTimeEntity.getId().longValue());
            dto.setLeadTime(leadTimeEntity.getLeadTime());
            dto.setNote(leadTimeEntity.getNote());
        }
        if (mqqPriceDTOS != null && !mqqPriceDTOS.isEmpty()){
            dto.setMqqPriceList(mqqPriceDTOS);
            dto.setDueDate(mqqPriceDTOS.get(0).getDueDate());
        }
        return dto;
    }
}
