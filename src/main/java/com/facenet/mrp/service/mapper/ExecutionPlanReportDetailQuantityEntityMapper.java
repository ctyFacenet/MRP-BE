package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.ExecutionPlanReportDetailQuantityEntity;
import com.facenet.mrp.service.dto.mrp.ExecutionPlanReportDetailQuantityEntityDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExecutionPlanReportDetailQuantityEntityMapper {
    ExecutionPlanReportDetailQuantityEntity toEntity(ExecutionPlanReportDetailQuantityEntityDto executionPlanReportDetailQuantityEntityDto);

    ExecutionPlanReportDetailQuantityEntityDto toDto(ExecutionPlanReportDetailQuantityEntity executionPlanReportDetailQuantityEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ExecutionPlanReportDetailQuantityEntity partialUpdate(ExecutionPlanReportDetailQuantityEntityDto executionPlanReportDetailQuantityEntityDto, @MappingTarget ExecutionPlanReportDetailQuantityEntity executionPlanReportDetailQuantityEntity);
}
