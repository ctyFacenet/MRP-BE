package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.ExecutionPlanReportDetailEntity;
import com.facenet.mrp.service.dto.mrp.ExecutionPlanReportDetailEntityDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExecutionPlanReportDetailEntityMapper {
    ExecutionPlanReportDetailEntity toEntity(ExecutionPlanReportDetailEntityDto executionPlanReportDetailEntityDto);

    ExecutionPlanReportDetailEntityDto toDto(ExecutionPlanReportDetailEntity executionPlanReportDetailEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ExecutionPlanReportDetailEntity partialUpdate(ExecutionPlanReportDetailEntityDto executionPlanReportDetailEntityDto, @MappingTarget ExecutionPlanReportDetailEntity executionPlanReportDetailEntity);
}
