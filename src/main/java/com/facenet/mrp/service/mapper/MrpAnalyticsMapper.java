package com.facenet.mrp.service.mapper;

import com.facenet.mrp.service.dto.AdvancedMrpDTO;
import com.facenet.mrp.service.dto.mrp.MrpAnalyticsInput;
import com.facenet.mrp.service.dto.mrp.MrpDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Date;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MrpAnalyticsMapper {

    @Mapping(target = "mrpCode", source = "mrpCode")
    @Mapping(target = "mrpSubCode", source = "mrpSubCode")
    @Mapping(target = "soCode", source = "input.soCode")
    @Mapping(target = "mrpDescription", source = "input.mrpDescription")
    @Mapping(target = "timeStart", source = "input.timeStart")
    @Mapping(target = "timeEnd", source = "input.timeEnd")
    @Mapping(target = "analysisPeriod", source = "input.analysisPeriod")
    @Mapping(target = "analysisDate", source = "currentDate")
    @Mapping(target = "analysisWhs", source = "input.analysisOption")
    @Mapping(target = "sortType", source = "input.sortedBy")
    @Mapping(target = "warehouseAnalysis", source = "input.listAnalysisWhs")
    @Mapping(target = "resultData", source = "detailDTO")
    @Mapping(target = "materialPreparationTime", source = "input.materialPreparationTime")
    @Mapping(target = "analysisMode", source = "input.analysisMode")
    AdvancedMrpDTO inputToDto (MrpAnalyticsInput input, String mrpCode, String mrpSubCode, Date currentDate, List<MrpDetailDTO> detailDTO);

}
