package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.MrpProductionQuantityEntity;
import com.facenet.mrp.domain.mrp.MrpRequiredQuantityEntity;
import com.facenet.mrp.service.dto.ItemSyntheticDTO;
import com.facenet.mrp.service.dto.mrp.MrpDetailDTO;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class MrpRequiredQuantityMapper {
    public MrpRequiredQuantityEntity toEntity(String soCode, String mrpSubCode, MrpDetailDTO mrpDetailDTO) {
        MrpRequiredQuantityEntity entity = new MrpRequiredQuantityEntity();
        entity.setSoCode(soCode);
        entity.setMrpSubCode(mrpSubCode);
        entity.setItemCode(mrpDetailDTO.getItemCode());
        entity.setItemCode(mrpDetailDTO.getItemCode());
        entity.setItemName(mrpDetailDTO.getItemName());
        entity.setBomVersion(mrpDetailDTO.getBomVersion());
        entity.setItemGroup(mrpDetailDTO.getGroupItem());
        entity.setReqQuantity(mrpDetailDTO.getRequiredQuantity());
        return entity;
    }

    public MrpRequiredQuantityEntity toEntity(String soCode, String mrpSubCode, ItemSyntheticDTO item) {
        MrpRequiredQuantityEntity entity = new MrpRequiredQuantityEntity();
        entity.setSoCode(soCode);
        entity.setMrpSubCode(mrpSubCode);
        entity.setItemCode(item.getItemCode());
        entity.setItemCode(item.getItemCode());
        entity.setItemName(item.getItemName());
        entity.setBomVersion(item.getBomVersion());
        entity.setItemGroup(item.getType());
        entity.setReqQuantity(item.getRequiredQuantity());
        return entity;
    }
}
