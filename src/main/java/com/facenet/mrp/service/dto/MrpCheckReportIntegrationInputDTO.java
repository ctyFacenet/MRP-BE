package com.facenet.mrp.service.dto;

import java.util.Date;
import java.util.List;

public class MrpCheckReportIntegrationInputDTO {


    private Date startTime;
    private Date endTime;
    private List<MrpActualInputDTO> mrpActualInputDTO;

    public MrpCheckReportIntegrationInputDTO() {
    }

    public MrpCheckReportIntegrationInputDTO(Date startTime, Date endTime, List<MrpActualInputDTO> mrpActualInputDTO) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.mrpActualInputDTO = mrpActualInputDTO;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<MrpActualInputDTO> getMrpActualDTO() {
        return mrpActualInputDTO;
    }

    public void setMrpActualDTO(List<MrpActualInputDTO> mrpActualInputDTO) {
        this.mrpActualInputDTO = mrpActualInputDTO;
    }
}
