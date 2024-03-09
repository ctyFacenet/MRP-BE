package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.ForecastTableEntity;
import com.facenet.mrp.domain.mrp.QForecastTableEntity;
import com.facenet.mrp.repository.mrp.ForecastTableRepository;
import com.facenet.mrp.security.SecurityUtils;
import com.facenet.mrp.service.dto.mrp.ForecastDTO;
import com.facenet.mrp.service.dto.mrp.QForecastDTO;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.PageFilterInput;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ForecastService {
    @Autowired
    ForecastTableRepository forecastTableRepository;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    EntityManager entityManager;
    public PageResponse<List<ForecastDTO>> searchForecast(PageFilterInput<ForecastDTO> input){
        if (SecurityUtils.getCurrentUsername().isEmpty()) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "access.denied");
        }
        Pageable pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
        QForecastTableEntity qForecastTableEntity = QForecastTableEntity.forecastTableEntity;
        JPAQuery<ForecastDTO> query = new JPAQueryFactory(entityManager)
            .select(new QForecastDTO(
                qForecastTableEntity.id,
                qForecastTableEntity.fcCode,
                qForecastTableEntity.fcName,
                qForecastTableEntity.timeStart,
                qForecastTableEntity.timeEnd,
                qForecastTableEntity.fcMode,
                qForecastTableEntity.warehouse,
                qForecastTableEntity.fcSource,
                qForecastTableEntity.priority,
                qForecastTableEntity.note,
                qForecastTableEntity.status,
                qForecastTableEntity.createdBy,
                qForecastTableEntity.createdAt,
                qForecastTableEntity.updatedBy,
                qForecastTableEntity.updatedAt,
                qForecastTableEntity.parentFc
            ))
            .from(qForecastTableEntity)
            .orderBy(qForecastTableEntity.updatedAt.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset());
        ForecastDTO filter = input.getFilter();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qForecastTableEntity.isActive.eq(1));
        if (filter.getParentFc() != null) {
            booleanBuilder.and(qForecastTableEntity.parentFc.eq(filter.getParentFc()));
        }
        if (!StringUtils.isEmpty(filter.getFcCode())) {
            booleanBuilder.and(qForecastTableEntity.fcCode.containsIgnoreCase(filter.getFcCode()));
        }
        if (!StringUtils.isEmpty(filter.getFcName())) {
            booleanBuilder.and(qForecastTableEntity.fcName.containsIgnoreCase(filter.getFcName()));
        }
        if (!StringUtils.isEmpty(filter.getFcMode())) {
            booleanBuilder.and(qForecastTableEntity.fcMode.containsIgnoreCase(filter.getFcMode()));
        }
        if (!StringUtils.isEmpty(filter.getFcSource())) {
            booleanBuilder.and(qForecastTableEntity.fcSource.containsIgnoreCase(filter.getFcSource()));
        }
        if (filter.getStatus() != null) {
            booleanBuilder.and(qForecastTableEntity.status.eq(filter.getStatus()));
        }
        if (filter.getPriority() != null) {
            booleanBuilder.and(qForecastTableEntity.priority.eq(filter.getPriority()));
        }
        if (!StringUtils.isEmpty(filter.getNote())) {
            booleanBuilder.and(qForecastTableEntity.note.containsIgnoreCase(filter.getNote()));
        }
        if (!StringUtils.isEmpty(filter.getCreatedBy())) {
            booleanBuilder.and(qForecastTableEntity.createdBy.containsIgnoreCase(filter.getCreatedBy()));
        }
        if (filter.getCreatedAt() != null && !StringUtils.isEmpty(simpleDateFormat.format(filter.getCreatedAt()))) {
            booleanBuilder.and(qForecastTableEntity.createdAt.eq(filter.getCreatedAt()));
        }
        if (filter.getTimeStart() != null && !StringUtils.isEmpty(simpleDateFormat.format(filter.getTimeStart()))) {
            booleanBuilder.and(qForecastTableEntity.createdAt.eq(filter.getCreatedAt()));
        }
        if (filter.getTimeEnd() != null && !StringUtils.isEmpty(simpleDateFormat.format(filter.getTimeEnd()))) {
            booleanBuilder.and(qForecastTableEntity.timeEnd.eq(filter.getTimeEnd()));
        }
        query.where(booleanBuilder);
        List<ForecastDTO> result = query.fetch();
        long count = query.fetchCount();

        return new PageResponse<List<ForecastDTO>>()
            .result("00", "Thành công", true)
            .dataCount(count)
            .data(result);
    }

    @Transactional
    public PageResponse createOrUpdate(ForecastDTO forecastDTO){
        try{
            //save or update forecast parent
            ForecastTableEntity forecastTableEntity = new ForecastTableEntity();
            // Lấy ngày và giờ hiện tại
            LocalDateTime now = LocalDateTime.now();
            String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            forecastTableEntity.setId(forecastDTO.getId());
            forecastTableEntity.setFcCode("FC-"+formattedDateTime);
            forecastTableEntity.setFcName(forecastDTO.getFcName());
            forecastTableEntity.setFcMode(forecastDTO.getFcMode());
            forecastTableEntity.setFcSource(forecastDTO.getFcSource());
            forecastTableEntity.setNote(forecastDTO.getNote());
            forecastTableEntity.setPriority(forecastDTO.getPriority());
            forecastTableEntity.setWarehouse(forecastDTO.getWarehouse());
            forecastTableEntity.setIsActive(1);
            forecastTableEntity.setResult(forecastDTO.getResult());
            forecastTableEntity.setTimeStart(forecastDTO.getTimeStart());
            forecastTableEntity.setTimeEnd(forecastDTO.getTimeEnd());
            ForecastTableEntity returnFC = forecastTableRepository.save(forecastTableEntity);
            //save mã chạy forecast
            ForecastTableEntity subForecast = new ForecastTableEntity();
            Integer count = forecastTableRepository.countFc(returnFC.getId());
            subForecast.setParentFc(returnFC.getId());//set parent
            subForecast.setFcCode(returnFC.getFcCode()+"_"+(count+1));//mã chạy fc
            subForecast.setFcName(forecastDTO.getSubForecast().getFcName());//mô tả fc
            subForecast.setStatus(forecastDTO.getSubForecast().getStatus());//trạng thái
            subForecast.setNote(forecastDTO.getNote());//ghi chu
            subForecast.setResult(forecastDTO.getSubForecast().getResult());//kết quả chạy forecast
            forecastTableRepository.save(subForecast);
            return new PageResponse<>()
                .errorCode("00")
                .message("Thành công")
                .isOk(true);
        }catch (Exception ex){
            return new PageResponse<>()
                .errorCode("500")
                .message("Lưu kết quả thất bại")
                .isOk(false);
        }
    }
}
