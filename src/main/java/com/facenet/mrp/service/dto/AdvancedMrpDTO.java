package com.facenet.mrp.service.dto;

import com.facenet.mrp.service.dto.mrp.MrpDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdvancedMrpDTO extends MrpDTO {

    private int materialPreparationTime;
    private String analysisMode;
    private boolean isHold = false;

    public AdvancedMrpDTO() {
    }

    public AdvancedMrpDTO(AdvancedMrpDTO mrpDTO ) {
        this.materialPreparationTime = mrpDTO.getMaterialPreparationTime();
        this.analysisMode = mrpDTO.getAnalysisMode();
        this.setSessionId(mrpDTO.getSessionId());
        this.setMrpCode(mrpDTO.getMrpCode());
        this.setMrpSubCode(mrpDTO.getMrpSubCode());
        this.setSoCode(mrpDTO.getSoCode());
        this.setMrpDescription(mrpDTO.getMrpDescription());
        this.setTimeStart(mrpDTO.getTimeStart());
        this.setTimeEnd(mrpDTO.getTimeEnd());
        this.setAnalysisPeriod(mrpDTO.getAnalysisPeriod());
        this.setAnalysisType(mrpDTO.getAnalysisType());
        this.setAnalysisDate(mrpDTO.getAnalysisDate());
        this.setAnalysisMode(mrpDTO.getAnalysisMode());
        this.setAnalysisWhs(mrpDTO.getAnalysisWhs());
        this.setSortType(mrpDTO.getSortType());
        this.setWarehouseAnalysis(mrpDTO.getWarehouseAnalysis());
        this.setCountData(mrpDTO.getResultData().size());
        this.setResultData(mrpDTO.getResultData());
        this.setListItemCode(mrpDTO.getListItemCode());
    }

    public String getAnalysisMode() {
        return analysisMode;
    }

    public void setAnalysisMode(String analysisMode) {
        this.analysisMode = analysisMode;
    }

    public int getMaterialPreparationTime() {
        return materialPreparationTime;
    }

    public void setMaterialPreparationTime(int materialPreparationTime) {
        this.materialPreparationTime = materialPreparationTime;
    }

    public boolean isHold() {
        return isHold;
    }

    public void setIsHold(boolean hold) {
        isHold = hold;
    }
}
