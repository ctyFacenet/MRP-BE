package com.facenet.mrp.service.dto.mrp;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OnOrderWithDurationDetailDTO extends OnOrderMonitoringDTO{
    public OnOrderWithDurationDetailDTO() {
    }

    public OnOrderWithDurationDetailDTO(String itemCode, String itemName, String poCode, String prCode, String contractNumber, String vendorCode, String vendorName, Date poDueDate, Date poCreateDate, String poCreateUser, Double quantity, Integer orderProgressPercent, Integer overDateNumber, String type,String mrpCode, String soCode) {
        super(itemCode, itemName, poCode, prCode, contractNumber, vendorCode, vendorName, poDueDate, poCreateDate, poCreateUser, quantity, orderProgressPercent, overDateNumber,type,mrpCode,soCode);
    }

    public OnOrderWithDurationDetailDTO(Integer id,String itemCode, String itemName, String poCode, String prCode, String contractNumber, String vendorCode, String vendorName, Date poDueDate, Date poCreateDate, String poCreateUser, Double quantity, Double remainQuantity, Double warehouseQuantity,Integer monitorId, Date detailDueDate, Double detailQuantity, String note,String type) {
        super(id,itemCode, itemName, poCode, prCode, contractNumber, vendorCode, vendorName, poDueDate, poCreateDate, poCreateUser, quantity, remainQuantity, warehouseQuantity, monitorId,detailDueDate, detailQuantity, note,type);
    }

    public OnOrderWithDurationDetailDTO(OnOrderMonitoringDTO dto){
        super(dto.getId(),dto.getItemCode(), dto.getItemName(),dto.getPoCode(), dto.getLineNumber(), dto.getPoDueDate(),dto.getPoCreateDate(),dto.getPoCreateUser(),dto.getType());
    }

    List<MonitoringDetailDTO> durationDetail;

    public List<MonitoringDetailDTO> getDurationDetail() {
        return durationDetail;
    }

    public void setDurationDetail(List<MonitoringDetailDTO> durationDetail) {
        this.durationDetail = durationDetail;
    }

}
