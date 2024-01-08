package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.SapOnOrderSummary;
import com.facenet.mrp.service.dto.GrpoDTO;
import com.facenet.mrp.service.dto.PoDto;
import com.facenet.mrp.service.dto.PurchaseRequestDTO;
import com.facenet.mrp.service.utils.Constants;
import org.springframework.stereotype.Service;

@Service
public class SapMaterialOnOrderMapper {

    public SapOnOrderSummary poToEntity(PoDto dto){
        SapOnOrderSummary entity = new SapOnOrderSummary();
        entity.setSapCode(dto.getPoCode() == null ? null : dto.getPoCode().trim());
        entity.setSoCode(dto.getSoCode() == null ? null : dto.getSoCode().trim());
        entity.setItemCode(dto.getProductCode() == null ? null : dto.getProductCode().trim());
        entity.setItemName(dto.getProductName());
        entity.setQuantity((double) dto.getQuantity());
        entity.setType(Constants.PO_TYPE);
        entity.setStatus(dto.getStatus());
        entity.setPoCode(dto.getPoCode() == null ? null : dto.getPoCode().trim());
        entity.setPrCode(dto.getPrCode() == null ? null : dto.getPrCode().trim());
        entity.setGrpoCode(dto.getGrpoCode() == null ? null : dto.getGrpoCode().trim());
        entity.setProviderName(dto.getVendor());
        entity.setContractNumber(dto.getContractCode());
        entity.setPoCreateDate(dto.getCreatedAt());
        entity.setCreatePoUser(dto.getPersonInCharge());
        entity.setDueDate(dto.getReceivedDate());
        entity.setLineNumber(dto.getLineNumber());
        return entity;
    }

    public SapOnOrderSummary prToEntity(PurchaseRequestDTO dto){
        SapOnOrderSummary entity = new SapOnOrderSummary();
        entity.setSapCode(dto.getPrId() == null ? null : dto.getPrId().trim());
        entity.setSoCode(dto.getSoCode() == null ? null : dto.getSoCode().trim());
        entity.setItemCode(dto.getProductId() == null ? null : dto.getProductId().trim());
        entity.setItemName(dto.getDescription());
        entity.setQuantity(Double.valueOf(dto.getQuantity()));
        entity.setType(Constants.PR_TYPE);
        entity.setStatus(dto.getStatus());
        entity.setDueDate(dto.getDeliverAt());
        entity.setPoCode(dto.getPoId() == null ? null : dto.getPoId().trim());
        entity.setPrCode(dto.getPrId() == null ? null : dto.getPrId().trim());
        entity.setGrpoCode(dto.getGrpoId() == null ? null : dto.getGrpoId().trim());
        entity.setDueDate(dto.getDeliverAt());
        entity.setLineNumber(dto.getLineNumber());
        return entity;
    }

    public SapOnOrderSummary grpoToEntity(GrpoDTO dto){
        SapOnOrderSummary entity = new SapOnOrderSummary();
        entity.setSapCode(dto.getGrpoId() == null ? null : dto.getGrpoId().trim());
        entity.setSoCode(dto.getMrpCode() == null ? null : dto.getMrpCode().trim());
        entity.setItemCode(dto.getProductId() == null ? null : dto.getProductId().trim());
        entity.setItemName(dto.getDescription());
        entity.setQuantity((double) dto.getDeliveredNum());
        entity.setType(Constants.GRPO_TYPE);
        entity.setStatus(dto.getStatus());
        entity.setPoCode(dto.getPoId() == null ? null : dto.getPoId().trim());
        entity.setPrCode(dto.getPrId() == null ? null : dto.getPrId().trim());
        entity.setGrpoCode(dto.getGrpoId() == null ? null : dto.getGrpoId().trim());
        entity.setCreatePoUser(dto.getWareHouser());
        entity.setPoCreateDate(dto.getCreatedAt());
        entity.setProviderName(dto.getVendor());
        entity.setDueDate(dto.getWareHouseDate());
        entity.setLineNumber(dto.getLineNumber());
        return entity;
    }
}
