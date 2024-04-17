package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.ExecutionPlanReportDetailEntity;
import com.facenet.mrp.domain.mrp.ExecutionPlanReportEntity;
import com.facenet.mrp.domain.mrp.KeyValueEntityV2;
import com.facenet.mrp.repository.mrp.KeyValueV2Repository;
import com.facenet.mrp.service.dto.mrp.ExecutionPlanReportDetailEntityDto;
import com.facenet.mrp.service.utils.Constants;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExecutionPlanReportDetailEntityMapper {

    ExecutionPlanReportDetailEntityMapper INSTANCE = Mappers.getMapper(ExecutionPlanReportDetailEntityMapper.class);

    static List<ExecutionPlanReportDetailEntityDto> dynamicPropertiesSet(KeyValueV2Repository keyValueV2Repository, List<ExecutionPlanReportDetailEntity> apsProfileEntities, Boolean isPaged, int entityType) {
        List<ExecutionPlanReportDetailEntityDto> apsSellOrderDTOS = new ArrayList<>();
        List<KeyValueEntityV2> soProperties;
        if (isPaged) {
            Collection<Integer> list = new ArrayList<>();
            for (ExecutionPlanReportDetailEntity apsSellOrderEntity : apsProfileEntities) {
                Long id = apsSellOrderEntity.getId();
                list.add(Math.toIntExact(id));
            }
            soProperties = keyValueV2Repository.findByEntityTypeAndEntityKeyInAndIsActiveTrue(entityType, list);
        } else {
            soProperties = keyValueV2Repository.findByEntityTypeAndIsActiveTrue(entityType);
        }
        Map<Integer, List<KeyValueEntityV2>> propertyMap = soProperties.stream().collect(Collectors.groupingBy(KeyValueEntityV2::getEntityKey));
        for (ExecutionPlanReportDetailEntity apsSellOrderEntity : apsProfileEntities) {
            apsSellOrderDTOS.add(ExecutionPlanReportDetailEntityMapper.entityToDTOMap(apsSellOrderEntity, propertyMap.get(Math.toIntExact(apsSellOrderEntity.getId()))));
        }
        return apsSellOrderDTOS;
    }

    static ExecutionPlanReportDetailEntityDto entityToDTOMap(ExecutionPlanReportDetailEntity apsSellOrderEntity, List<KeyValueEntityV2> keyValueEntityV2List) {
        ExecutionPlanReportDetailEntityDto apsSellOrderDTO = ExecutionPlanReportDetailEntityMapper.INSTANCE.toDto(apsSellOrderEntity);
        if (CollectionUtils.isEmpty(keyValueEntityV2List)) return apsSellOrderDTO;
        for (KeyValueEntityV2 keyValueEntityV2 : keyValueEntityV2List) {
            if (StringUtils.isEmpty(keyValueEntityV2.getCommonValue())) {
                apsSellOrderDTO.getPropertiesMap().put(keyValueEntityV2.getColumnPropertyEntity().getKeyName(), null);
                continue;
            }
            if (keyValueEntityV2.getColumnPropertyEntity().getDataType() == Constants.INT_VALUE) {
                apsSellOrderDTO.getPropertiesMap().put(keyValueEntityV2.getColumnPropertyEntity().getKeyName(), String.valueOf(keyValueEntityV2.getIntValue()));
            } else if (keyValueEntityV2.getColumnPropertyEntity().getDataType() == Constants.FLOAT_VALUE) {
                apsSellOrderDTO.getPropertiesMap().put(keyValueEntityV2.getColumnPropertyEntity().getKeyName(), String.valueOf(keyValueEntityV2.getDoubleValue()));
            } else if (keyValueEntityV2.getColumnPropertyEntity().getDataType() == Constants.STRING_VALUE) {
                apsSellOrderDTO.getPropertiesMap().put(keyValueEntityV2.getColumnPropertyEntity().getKeyName(), String.valueOf(keyValueEntityV2.getStringValue()));
            } else if (keyValueEntityV2.getColumnPropertyEntity().getDataType() == Constants.JSON_VALUE) {
                apsSellOrderDTO.getPropertiesMap().put(keyValueEntityV2.getColumnPropertyEntity().getKeyName(), String.valueOf(keyValueEntityV2.getJsonValue()));
            } else if (keyValueEntityV2.getColumnPropertyEntity().getDataType() == Constants.DATE_VALUE) {
                apsSellOrderDTO.getPropertiesMap().put(keyValueEntityV2.getColumnPropertyEntity().getKeyName(), String.valueOf(keyValueEntityV2.getDateValue()));
            } else if (keyValueEntityV2.getColumnPropertyEntity().getDataType() == Constants.BOOLEAN_VALUE) {
                apsSellOrderDTO.getPropertiesMap().put(keyValueEntityV2.getColumnPropertyEntity().getKeyName(), String.valueOf(keyValueEntityV2.getBooleanValue()));
            } else {
                apsSellOrderDTO.getPropertiesMap().put(keyValueEntityV2.getColumnPropertyEntity().getKeyName(), null);
            }
        }
        return apsSellOrderDTO;
    }

    static Long map(ExecutionPlanReportEntity value) {
        return value.getId();
    }

    static ExecutionPlanReportEntity map(Long value) {
        ExecutionPlanReportEntity executionPlanReportEntity = new ExecutionPlanReportEntity();
        executionPlanReportEntity.setId(value);
        return executionPlanReportEntity;
    }

    ExecutionPlanReportDetailEntity toEntity(ExecutionPlanReportDetailEntityDto executionPlanReportDetailEntityDto);

    ExecutionPlanReportDetailEntityDto toDto(ExecutionPlanReportDetailEntity executionPlanReportDetailEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ExecutionPlanReportDetailEntity partialUpdate(ExecutionPlanReportDetailEntityDto executionPlanReportDetailEntityDto, @MappingTarget ExecutionPlanReportDetailEntity executionPlanReportDetailEntity);

}
