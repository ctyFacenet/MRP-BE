package com.facenet.mrp.service.mapper;

import com.facenet.mrp.service.dto.scada.ProductionLineDTO;
import com.facenet.mrp.service.dto.scada.BaseAsset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductionLineMapper {
    @Mapping(target = "productionLineCode",source = "assetDetail.lineId")
    @Mapping(target = "productionLineName", source = "name")
    @Mapping(target = "assetType", source = "type")
    @Mapping(target = "group", source = "assetDetail.group")
    @Mapping(target = "label", source = "label")
    @Mapping(target = "computingProductivity", source = "assetDetail.computingProductivity")
    @Mapping(target = "createTime", source = "createdTime")
    @Mapping(target = "cycleTime", source = "assetDetail.cycleTime")
    @Mapping(target = "status", source = "assetDetail.status")
    ProductionLineDTO toDTO(BaseAsset baseAsset);

    List<ProductionLineDTO> toDTO(List<BaseAsset> baseAssets);
}
