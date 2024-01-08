package com.facenet.mrp.service.dto.scada;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AssetDetail {
    @JsonProperty("Line_Id")
    private List<EntityValue> lineId;
    @JsonProperty("Line_Name")
    private List<EntityValue> lineName;
    @JsonProperty("Cycle_Time")
    private List<EntityValue> cycleTime;
    @JsonProperty("Computing_Productivity")
    private List<EntityValue> computingProductivity;
    private List<EntityValue> group;
    @JsonProperty("STATUS")
    private List<EntityValue> status;

    public String getLineId() {
        if (lineId == null) return null;
        return lineId.get(lineId.size() - 1).getValue();
    }

    public void setLineId(List<EntityValue> lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        if (lineName == null) return null;
        return lineName.get(lineName.size() - 1).getValue();
    }

    public void setLineName(List<EntityValue> lineName) {
        this.lineName = lineName;
    }

    public String getCycleTime() {
        if (cycleTime == null) return null;
        return cycleTime.get(cycleTime.size() - 1).getValue();
    }

    public void setCycleTime(List<EntityValue> cycleTime) {
        this.cycleTime = cycleTime;
    }

    public String getComputingProductivity() {
        if (computingProductivity == null) return null;
        return computingProductivity.get(computingProductivity.size() - 1).getValue();
    }

    public void setComputingProductivity(List<EntityValue> computingProductivity) {
        this.computingProductivity = computingProductivity;
    }

    public String getGroup() {
        if (group == null) return null;
        return group.get(group.size() - 1).getValue();
    }

    public void setGroup(List<EntityValue> group) {
        this.group = group;
    }

    public String getStatus() {
        if (status == null) return null;
        return status.get(status.size() - 1).getValue();
    }

    public void setStatus(List<EntityValue> status) {
        this.status = status;
    }
}
