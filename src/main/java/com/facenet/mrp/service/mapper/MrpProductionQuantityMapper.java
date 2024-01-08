package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.MrpProductionQuantityEntity;
import com.facenet.mrp.service.dto.DetailItemSyntheticDTO;
import com.facenet.mrp.service.dto.ItemSyntheticDTO;
import com.facenet.mrp.service.dto.ReportComparisonDTO;
import com.facenet.mrp.service.dto.mrp.MrpDetailDTO;
import com.facenet.mrp.service.dto.mrp.MrpResultDTO;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class MrpProductionQuantityMapper {
    public MrpProductionQuantityEntity toEntity(String soCode, String mrpSubCode, MrpDetailDTO mrpDetailDTO, MrpResultDTO quantity) throws ParseException {
        MrpProductionQuantityEntity entity = new MrpProductionQuantityEntity();
        entity.setSoCode(soCode);
        entity.setMrpSubCode(mrpSubCode);
        entity.setItemCode(mrpDetailDTO.getItemCode());
        entity.setItemCode(mrpDetailDTO.getItemCode());
        entity.setItemName(mrpDetailDTO.getItemName());
        entity.setBomVersion(mrpDetailDTO.getBomVersion());
        entity.setItemGroup(mrpDetailDTO.getGroupItem());
        entity.setProductionQuantity(quantity.getOriginQuantity());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        entity.setDueDate(simpleDateFormat.parse(quantity.getLandmark()));
        return entity;
    }

    public MrpProductionQuantityEntity toEntity(String soCode, String mrpSubCode, ItemSyntheticDTO item, DetailItemSyntheticDTO quantity) throws ParseException {
        MrpProductionQuantityEntity entity = new MrpProductionQuantityEntity();
        entity.setSoCode(soCode);
        entity.setMrpSubCode(mrpSubCode);
        entity.setItemCode(item.getItemCode());
        entity.setItemCode(item.getItemCode());
        entity.setItemName(item.getItemName());
        entity.setBomVersion(item.getBomVersion());
        entity.setItemGroup(item.getType());
        entity.setProductionQuantity(quantity.getOriginQuantity());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        entity.setDueDate(simpleDateFormat.parse(quantity.getLandMark()));
        return entity;
    }

    public ReportComparisonDTO toDTO(MrpProductionQuantityEntity quantityData) {
        ReportComparisonDTO result = new ReportComparisonDTO();
        result.setSoCode(quantityData.getSoCode());
        result.setMrpCode(quantityData.getMrpSubCode());
        result.setItemCode(quantityData.getItemCode());
        result.setItemName(quantityData.getItemName());
        result.setVersion(quantityData.getBomVersion());
        return result;
    }
}
