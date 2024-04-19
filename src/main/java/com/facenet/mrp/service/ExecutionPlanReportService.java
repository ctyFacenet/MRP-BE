package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.*;
import com.facenet.mrp.repository.mrp.ColumnPropertyRepository;
import com.facenet.mrp.repository.mrp.ExecutionPlanReportDetailEntityRepository;
import com.facenet.mrp.repository.mrp.ExecutionPlanReportDetailQuantityEntityRepository;
import com.facenet.mrp.repository.mrp.ExecutionPlanReportEntityRepository;
import com.facenet.mrp.repository.mrp.KeyValueV2Repository;
import com.facenet.mrp.service.dto.ReportDetailPlanDTO;
import com.facenet.mrp.service.dto.mrp.ExecutionPlanReportDetailEntityDto;
import com.facenet.mrp.service.dto.mrp.ExecutionPlanReportEntityDto;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.ExecutionPlanReportDetailEntityMapper;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.utils.Constants;
import com.facenet.mrp.service.utils.XlsxExcelHandle;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExecutionPlanReportService {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionPlanReportService.class);
    @Autowired
    private ExecutionPlanReportDetailEntityRepository executionPlanReportDetailEntityRepository;
    @Autowired
    private ExecutionPlanReportDetailQuantityEntityRepository executionPlanReportDetailQuantityEntityRepository;
    @Autowired
    private ExecutionPlanReportEntityRepository executionPlanReportEntityRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private KeyValueV2Repository keyValueV2Repository;
    @Autowired
    private ColumnPropertyRepository columnPropertyRepository;

    @Autowired
    private KeyValueService keyValueService;

    @Autowired
    private XlsxExcelHandle excelHandle;



    public CommonResponse getExecution(Long id){
        List<ExecutionPlanReportDetailEntity> reportDetailEntities = executionPlanReportDetailEntityRepository.getAllById(id);
        List<ReportDetailPlanDTO> detailPlanDTOS = new ArrayList<>();
        for (ExecutionPlanReportDetailEntity item: reportDetailEntities){
            ReportDetailPlanDTO reportDetailPlanDTO = new ReportDetailPlanDTO();
            reportDetailPlanDTO.setId(item.getId());
            reportDetailPlanDTO.setProductCode(item.getProductCode());
            reportDetailPlanDTO.setProductName(item.getProductName());
            reportDetailPlanDTO.setActive(item.getIsActive());
            reportDetailPlanDTO.setVersion(item.getVersion());
            reportDetailPlanDTO.setTotalQuantity(item.getTotalQuantity());
            reportDetailPlanDTO.setType(executionPlanReportDetailEntityRepository.getType(id));
            reportDetailPlanDTO.setPlanReportDetail(executionPlanReportDetailQuantityEntityRepository.getAllById(item.getId()));
            detailPlanDTOS.add(reportDetailPlanDTO);
        }
        return new CommonResponse<>().success().data(detailPlanDTOS);
    }
    @Transactional
    public Page<ExecutionPlanReportEntityDto> getExecutionPlanReport(PageFilterInput<ExecutionPlanReportEntityDto> input) {
//        return null;
        Pageable pageable = input.getPageSize() == 0 ? Pageable.unpaged() : PageRequest.of(input.getPageNumber(), input.getPageSize());
        ExecutionPlanReportEntityDto dataFilter = input.getFilter();
        logger.info(String.valueOf(dataFilter));
        QExecutionPlanReportEntity qDataEntity = QExecutionPlanReportEntity.executionPlanReportEntity;
        JPAQuery<ExecutionPlanReportEntity> query = new JPAQueryFactory(entityManager).selectFrom(qDataEntity);
        List<ExecutionPlanReportEntityDto> dataDtos = new ArrayList<>();
        if (pageable.isPaged()) {
            query.limit(pageable.getPageSize()).offset(pageable.getOffset());
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qDataEntity.isActive.isTrue());
        if (dataFilter.getId() != null) {
            booleanBuilder.and(qDataEntity.id.eq(dataFilter.getId()));
        }

        if (!StringUtils.isEmpty(dataFilter.getNameCompare())) {
            booleanBuilder.and(qDataEntity.nameCompare.containsIgnoreCase(dataFilter.getNameCompare()));
        }

        if (dataFilter.getTotalQuantity() != null) {
            booleanBuilder.and(qDataEntity.totalQuantity.eq(dataFilter.getTotalQuantity()));
        }
        Map<String, ColumnPropertyEntity> columnPropertyEntityMap = columnPropertyRepository.getAllDynamicColumnByEntityTypeByMap(Constants.EntityType.EXECUTION_PLAN_REPORT);

        QKeyValueEntityV2 qKeyValueEntityV2 = QKeyValueEntityV2.keyValueEntityV2;

        if (!dataFilter.getPropertiesMap().isEmpty()) {
            logger.info(String.valueOf(dataFilter.getPropertiesMap()));
            Map<String, List<Integer>> sellOrderIdMap = new HashMap<>();
            dataFilter.getPropertiesMap().forEach((keyName, value) -> {
                JPAQuery<Integer> propertySubQuery = new JPAQueryFactory(entityManager).select(qKeyValueEntityV2.entityKey).from(qKeyValueEntityV2);
                BooleanBuilder dynamicBooleanBuilder = new BooleanBuilder();
                dynamicBooleanBuilder.and(qKeyValueEntityV2.isActive.eq(true));
                if (!StringUtils.isEmpty(value)) {
                    dynamicBooleanBuilder.and(qKeyValueEntityV2.columnPropertyEntity.keyName.eq(keyName));
                    dynamicBooleanBuilder.and(qKeyValueEntityV2.commonValue.containsIgnoreCase(value));
                    propertySubQuery.where(dynamicBooleanBuilder);
                    sellOrderIdMap.put(keyName, propertySubQuery.fetch());
                }
            });
            logger.info(String.valueOf(sellOrderIdMap.keySet()));

            for (String key : sellOrderIdMap.keySet()) {
                List<Long> longList = new ArrayList<>();
                for (Integer intValue : sellOrderIdMap.get(key)) {
                    longList.add(intValue.longValue());
                }
                booleanBuilder.and(qDataEntity.id.in(longList));
            }
        }

        query.where(booleanBuilder);

        if (!StringUtils.isEmpty(input.getCommon())) {
            BooleanBuilder booleanBuilderSearchGeneral = new BooleanBuilder();
            booleanBuilderSearchGeneral.or(qDataEntity.nameCompare.containsIgnoreCase(input.getCommon()));
//            booleanBuilderSearchGeneral.or(qDataEntity.totalQuantity.eq(input.getCommon()));
//            booleanBuilderSearchGeneral.or(qDataEntity.productCode.containsIgnoreCase(input.getCommon()));
            //            booleanBuilderSearchGeneral.or(so.status.eq(sellOrderInput.getCommon()));

            Map<String, List<Integer>> sellOrderIdCommon = new HashMap<>();
            columnPropertyEntityMap.forEach((s, columnPropertyEntity) -> {
                if (columnPropertyEntity.getIsFixed() == 0) {
                    JPAQuery<Integer> propertySubQuery = new JPAQueryFactory(entityManager).select(qKeyValueEntityV2.entityKey).from(qKeyValueEntityV2);
                    BooleanBuilder dynamicBooleanBuilder = new BooleanBuilder();
                    dynamicBooleanBuilder.and(qKeyValueEntityV2.isActive.eq(true));
                    dynamicBooleanBuilder.and(qKeyValueEntityV2.columnPropertyEntity.keyName.eq(s));
                    dynamicBooleanBuilder.and(qKeyValueEntityV2.commonValue.containsIgnoreCase(input.getCommon()));
                    propertySubQuery.where(dynamicBooleanBuilder);
                    sellOrderIdCommon.put(s, propertySubQuery.fetch());
                }
            });
            for (String key : sellOrderIdCommon.keySet()) {
                List<Long> longList = new ArrayList<>();
                for (Integer intValue : sellOrderIdCommon.get(key)) {
                    longList.add(intValue.longValue());
                }
                booleanBuilderSearchGeneral.or(qDataEntity.id.in(longList));
            }

            query.where(booleanBuilderSearchGeneral);
        }

        if (!StringUtils.isEmpty(input.getSortProperty())) {
            boolean checkSort = true;
            List<ColumnPropertyEntity> columns = columnPropertyRepository.getAllColumnByEntityType(Constants.EntityType.EXECUTION_PLAN_REPORT);
            for (ColumnPropertyEntity column : columns) {
                // Valid sort column name
                if (input.getSortProperty().equals(column.getKeyName())) {
                    checkSort = false;
                    if (column.getIsFixed() == 1) {
                        // Default col
                        Path<Object> fieldPath = Expressions.path(Object.class, qDataEntity, input.getSortProperty());
                        query.orderBy(new OrderSpecifier(input.getSortOrder(), fieldPath));
                    } else {
                        // Dynamic col
                        QKeyValueEntityV2 qKeyValueEntityV22 = new QKeyValueEntityV2("keyValue2");
                        query.leftJoin(qKeyValueEntityV22).on(qKeyValueEntityV22.entityKey.eq(qDataEntity.id.intValue()).and(qKeyValueEntityV22.entityType.eq(Constants.EntityType.EXECUTION_PLAN_REPORT)).and(qKeyValueEntityV22.columnPropertyEntity.eq(column)));
                        switch (column.getDataType()) {
                            case Constants.INT_VALUE:
                                query.orderBy(new OrderSpecifier<>(input.getSortOrder(), qKeyValueEntityV22.intValue));
                                break;
                            case Constants.FLOAT_VALUE:
                                query.orderBy(new OrderSpecifier<>(input.getSortOrder(), qKeyValueEntityV22.doubleValue));
                                break;
                            case Constants.STRING_VALUE:
                                query.orderBy(new OrderSpecifier<>(input.getSortOrder(), qKeyValueEntityV22.stringValue));
                                break;
                            case Constants.JSON_VALUE:
                                query.orderBy(new OrderSpecifier<>(input.getSortOrder(), qKeyValueEntityV22.jsonValue));
                                break;
                            case Constants.DATE_VALUE:
                                query.orderBy(new OrderSpecifier<>(input.getSortOrder(), qKeyValueEntityV22.dateValue));
                                break;
                        }
                    }
                }
            }
            if (checkSort) {
                Path<Object> fieldPath = Expressions.path(Object.class, qDataEntity, input.getSortProperty());
                query.orderBy(new OrderSpecifier(input.getSortOrder(), fieldPath));
            }
        }

        List<ExecutionPlanReportEntity> entities = query.fetch();
        long count = query.fetchCount();
        Page<ExecutionPlanReportEntity> apsSellOrderEntityPage = new PageImpl<>(entities, pageable, count);
        dataDtos = ExecutionPlanReportEntityMapper.dynamicPropertiesSet(keyValueV2Repository, apsSellOrderEntityPage.getContent(), pageable.isPaged(), Constants.EntityType.EXECUTION_PLAN_REPORT);
        logger.info(dataDtos.toString());
        return new PageImpl<>(dataDtos, pageable, count);
    }

    @Transactional
    public ExecutionPlanReportEntityDto addExecutionPlanReport(MultipartFile file,ExecutionPlanReportEntityDto entityDto) throws IOException {
        List<ExecutionPlanReportEntity> opSoProductCheckExistCode = executionPlanReportEntityRepository.findExecutionPlanReportEntitiesByNameCompare(entityDto.getNameCompare());
        if (!opSoProductCheckExistCode.isEmpty())
            throw new CustomException(HttpStatus.BAD_REQUEST, "Trùng tên", String.valueOf(entityDto.getId()));
        //        if (StringUtils.isEmpty(apsSellOrderProductDTO.getSoCode()))
        //            throw new CustomException(HttpStatus.BAD_REQUEST, "must.have.soCode");
        ExecutionPlanReportEntity apsSellOrderProductEntity = ExecutionPlanReportEntityMapper.INSTANCE.toEntity(entityDto);
        apsSellOrderProductEntity.setIsActive(Boolean.TRUE);
        ExecutionPlanReportEntity resultEntity = executionPlanReportEntityRepository.save(apsSellOrderProductEntity);
        ExecutionPlanReportEntityDto resultDto = ExecutionPlanReportEntityMapper.INSTANCE.entityToDTO(resultEntity);
        //phần đọc excel
        List<ExecutionPlanReportDetailEntity> planReportDetailEntities = excelHandle.convertToReport(file.getInputStream());
        for (ExecutionPlanReportDetailEntity item: planReportDetailEntities){
            item.setIdExecutionPlanReport(resultEntity);
            ExecutionPlanReportDetailEntity detailEntity = executionPlanReportDetailEntityRepository.save(item);
            for (ExecutionPlanReportDetailQuantityEntity quantityEntity : item.getPlanReportDetail()){
                quantityEntity.setIdExecutionPlanReportDetail(detailEntity);
            }
            executionPlanReportDetailQuantityEntityRepository.saveAll(item.getPlanReportDetail());
        }
        if (!entityDto.getPropertiesMap().isEmpty()) {
            keyValueService.createUpdateKeyValueOfEntity(Math.toIntExact(apsSellOrderProductEntity.getId()), entityDto.getPropertiesMap(), Constants.EntityType.EXECUTION_PLAN_REPORT, false);
        }
        return resultDto;
    }

    @Transactional
    public void deleteExecutionPlanReport(Long id) {
        Optional<ExecutionPlanReportEntity> executionPlanReportEntityOptional = executionPlanReportEntityRepository.findById(id);

        if (executionPlanReportEntityOptional.isEmpty())
            throw new CustomException(HttpStatus.NOT_FOUND, "Không tồn tại", String.valueOf(id));
        ExecutionPlanReportEntity executionPlanReportEntity = executionPlanReportEntityOptional.get();
//        ExecutionPlanReportEntity executionPlanReportEntity = opSoProduct.get(0);
        executionPlanReportEntity.setIsActive(false);
        executionPlanReportEntityRepository.save(executionPlanReportEntity);
    }

    public Page<ExecutionPlanReportDetailEntityDto> getExecutionPlanReportDetail(PageFilterInput<ExecutionPlanReportDetailEntityDto> input) {

        Pageable pageable = input.getPageSize() == 0 ? Pageable.unpaged() : PageRequest.of(input.getPageNumber(), input.getPageSize());
        ExecutionPlanReportDetailEntityDto dataFilter = input.getFilter();
        logger.info(String.valueOf(dataFilter));
        QExecutionPlanReportDetailEntity qDataEntity = QExecutionPlanReportDetailEntity.executionPlanReportDetailEntity;
        JPAQuery<ExecutionPlanReportDetailEntity> query = new JPAQueryFactory(entityManager).selectFrom(qDataEntity);
        List<ExecutionPlanReportDetailEntityDto> dataDtos = new ArrayList<>();
        if (pageable.isPaged()) {
            query.limit(pageable.getPageSize()).offset(pageable.getOffset());
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qDataEntity.isActive.isTrue());
        if (dataFilter.getId() != null) {
            booleanBuilder.and(qDataEntity.id.eq(dataFilter.getId()));
        }
        Map<String, ColumnPropertyEntity> columnPropertyEntityMap = columnPropertyRepository.getAllDynamicColumnByEntityTypeByMap(Constants.EntityType.EXECUTION_PLAN_REPORT_DETAIL);
        QKeyValueEntityV2 qKeyValueEntityV2 = QKeyValueEntityV2.keyValueEntityV2;
        if (!dataFilter.getPropertiesMap().isEmpty()) {
            logger.info(String.valueOf(dataFilter.getPropertiesMap()));
            Map<String, List<Integer>> sellOrderIdMap = new HashMap<>();
            dataFilter.getPropertiesMap().forEach((keyName, value) -> {
                JPAQuery<Integer> propertySubQuery = new JPAQueryFactory(entityManager).select(qKeyValueEntityV2.entityKey).from(qKeyValueEntityV2);
                BooleanBuilder dynamicBooleanBuilder = new BooleanBuilder();
                dynamicBooleanBuilder.and(qKeyValueEntityV2.isActive.eq(true));
                if (!StringUtils.isEmpty(value)) {
                    dynamicBooleanBuilder.and(qKeyValueEntityV2.columnPropertyEntity.keyName.eq(keyName));
                    dynamicBooleanBuilder.and(qKeyValueEntityV2.commonValue.containsIgnoreCase(value));
                    propertySubQuery.where(dynamicBooleanBuilder);
                    sellOrderIdMap.put(keyName, propertySubQuery.fetch());
                }
            });
            logger.info(String.valueOf(sellOrderIdMap.keySet()));

            for (String key : sellOrderIdMap.keySet()) {
                List<Long> longList = new ArrayList<>();
                for (Integer intValue : sellOrderIdMap.get(key)) {
                    longList.add(intValue.longValue());
                }
                booleanBuilder.and(qDataEntity.id.in(longList));
            }
        }
        query.where(booleanBuilder);
        if (!StringUtils.isEmpty(input.getCommon())) {
            BooleanBuilder booleanBuilderSearchGeneral = new BooleanBuilder();
//            booleanBuilderSearchGeneral.or(qDataEntity.nameCompare.containsIgnoreCase(input.getCommon()));
//            booleanBuilderSearchGeneral.or(qDataEntity.totalQuantity.eq(input.getCommon()));
//            booleanBuilderSearchGeneral.or(qDataEntity.productCode.containsIgnoreCase(input.getCommon()));
            //            booleanBuilderSearchGeneral.or(so.status.eq(sellOrderInput.getCommon()));

            Map<String, List<Integer>> sellOrderIdCommon = new HashMap<>();
            columnPropertyEntityMap.forEach((s, columnPropertyEntity) -> {
                if (columnPropertyEntity.getIsFixed() == 0) {
                    JPAQuery<Integer> propertySubQuery = new JPAQueryFactory(entityManager).select(qKeyValueEntityV2.entityKey).from(qKeyValueEntityV2);
                    BooleanBuilder dynamicBooleanBuilder = new BooleanBuilder();
                    dynamicBooleanBuilder.and(qKeyValueEntityV2.isActive.eq(true));
                    dynamicBooleanBuilder.and(qKeyValueEntityV2.columnPropertyEntity.keyName.eq(s));
                    dynamicBooleanBuilder.and(qKeyValueEntityV2.commonValue.containsIgnoreCase(input.getCommon()));
                    propertySubQuery.where(dynamicBooleanBuilder);
                    sellOrderIdCommon.put(s, propertySubQuery.fetch());
                }
            });
            for (String key : sellOrderIdCommon.keySet()) {
                List<Long> longList = new ArrayList<>();
                for (Integer intValue : sellOrderIdCommon.get(key)) {
                    longList.add(intValue.longValue());
                }
                booleanBuilderSearchGeneral.or(qDataEntity.id.in(longList));
            }
            query.where(booleanBuilderSearchGeneral);
        }

        if (!StringUtils.isEmpty(input.getSortProperty())) {
            boolean checkSort = true;
            List<ColumnPropertyEntity> columns = columnPropertyRepository.getAllColumnByEntityType(Constants.EntityType.EXECUTION_PLAN_REPORT_DETAIL);
            for (ColumnPropertyEntity column : columns) {
                // Valid sort column name
                if (input.getSortProperty().equals(column.getKeyName())) {
                    checkSort = false;
                    if (column.getIsFixed() == 1) {
                        // Default col
                        Path<Object> fieldPath = Expressions.path(Object.class, qDataEntity, input.getSortProperty());
                        query.orderBy(new OrderSpecifier(input.getSortOrder(), fieldPath));
                    } else {
                        // Dynamic col
                        QKeyValueEntityV2 qKeyValueEntityV22 = new QKeyValueEntityV2("keyValue2");
                        query.leftJoin(qKeyValueEntityV22).on(qKeyValueEntityV22.entityKey.eq(qDataEntity.id.intValue()).and(qKeyValueEntityV22.entityType.eq(Constants.EntityType.EXECUTION_PLAN_REPORT_DETAIL)).and(qKeyValueEntityV22.columnPropertyEntity.eq(column)));
                        switch (column.getDataType()) {
                            case Constants.INT_VALUE:
                                query.orderBy(new OrderSpecifier<>(input.getSortOrder(), qKeyValueEntityV22.intValue));
                                break;
                            case Constants.FLOAT_VALUE:
                                query.orderBy(new OrderSpecifier<>(input.getSortOrder(), qKeyValueEntityV22.doubleValue));
                                break;
                            case Constants.STRING_VALUE:
                                query.orderBy(new OrderSpecifier<>(input.getSortOrder(), qKeyValueEntityV22.stringValue));
                                break;
                            case Constants.JSON_VALUE:
                                query.orderBy(new OrderSpecifier<>(input.getSortOrder(), qKeyValueEntityV22.jsonValue));
                                break;
                            case Constants.DATE_VALUE:
                                query.orderBy(new OrderSpecifier<>(input.getSortOrder(), qKeyValueEntityV22.dateValue));
                                break;
                        }
                    }
                }
            }
            if (checkSort) {
                Path<Object> fieldPath = Expressions.path(Object.class, qDataEntity, input.getSortProperty());
                query.orderBy(new OrderSpecifier(input.getSortOrder(), fieldPath));
            }
        }

        List<ExecutionPlanReportDetailEntity> entities = query.fetch();
        long count = query.fetchCount();
        Page<ExecutionPlanReportDetailEntity> apsSellOrderEntityPage = new PageImpl<>(entities, pageable, count);
        dataDtos = ExecutionPlanReportDetailEntityMapper.dynamicPropertiesSet(keyValueV2Repository, apsSellOrderEntityPage.getContent(), pageable.isPaged(), Constants.EntityType.EXECUTION_PLAN_REPORT_DETAIL);
        logger.info(dataDtos.toString());
        return new PageImpl<>(dataDtos, pageable, count);
    }
}
