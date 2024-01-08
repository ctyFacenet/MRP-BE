package com.facenet.mrp.service.dto.mrp;

public class CurrentScheduleTimeInput {

    private String currentTime;
    private String analysisPeriod;
    private int materialPreparationTime;

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getAnalysisPeriod() {
        return analysisPeriod;
    }

    public void setAnalysisPeriod(String analysisPeriod) {
        this.analysisPeriod = analysisPeriod;
    }

    public int getMaterialPreparationTime() {
        return materialPreparationTime;
    }

    public void setMaterialPreparationTime(int materialPreparationTime) {
        this.materialPreparationTime = materialPreparationTime;
    }
}
