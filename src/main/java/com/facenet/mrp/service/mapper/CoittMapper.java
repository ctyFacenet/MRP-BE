package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.MrpBomVersionDetail;
import com.facenet.mrp.service.dto.DetailBomVersionDTO;
import org.springframework.stereotype.Service;

@Service
public class CoittMapper {

    public DetailBomVersionDTO convert(MrpBomVersionDetail entity, Long poQuantity){
        DetailBomVersionDTO dto = new DetailBomVersionDTO();
        dto.setItemGroup(entity.getItemGroup());
        dto.setItemCode(entity.getItemCode());
        dto.setItemDescription(entity.getItemName());
        dto.setQuantity(entity.getQuantity() * poQuantity);
        dto.setBasicQuantity(entity.getQuantity());
        dto.setVersion(entity.getVersion());
        dto.setWhsCode(entity.getWareHouse());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
