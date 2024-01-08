package com.facenet.mrp.service.dto;

import java.util.List;

public class RecommendationPlanNoteDTO {
    String note;
    List<RecommendationPlanDto> plans;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<RecommendationPlanDto> getPlans() {
        return plans;
    }

    public void setPlans(List<RecommendationPlanDto> plans) {
        this.plans = plans;
    }
}
