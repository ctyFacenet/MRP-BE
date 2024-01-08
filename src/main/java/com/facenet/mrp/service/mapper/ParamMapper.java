package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.Param;
import com.facenet.mrp.service.dto.ParamDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ParamMapper {
    ParamDto toDto(Param param);

    List<ParamDto> toDtoList(List<Param> params);
}
