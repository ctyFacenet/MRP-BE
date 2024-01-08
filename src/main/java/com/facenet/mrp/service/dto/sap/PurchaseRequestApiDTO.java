package com.facenet.mrp.service.dto.sap;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PurchaseRequestApiDTO {
//    {
//        "Type": "PR",
//        "User": "manager",
//        "TimeReqPR":"01/03/2023",
//        "Remark":"2",
//        "Mcode": "1",
//        "SoCode": "3",
//        "TimeCreate": "01/03/2023",
//        "TimeRequest": "01/03/2023",
//        "DetailPR": [],
//        "TimeApproved": "01/03/2023",
//        "Status": ""
//    }
    @JsonProperty("Type")
    private String type;

    @JsonProperty("User")
    private String user;

    @JsonProperty("TimeReqPR")
    private String timeRequestPR;

    @JsonProperty("Remark")
    private String remark;

    @JsonProperty("Mcode")
    private String mrpSubCode;

    @JsonProperty("SoCode")
    private String soCode;

    @JsonProperty("TimeCreate")
    private String createTime;

    @JsonProperty("TimeRequest")
    private String timeRequest;

    @JsonProperty("TimeApproved")
    private String timeApproved;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("DetailPR")
    private List<PurchaseRequestDetailApiDTO> purchaseRequestDetailApiDTOS;

    @Override
    public String toString() {
        return "PurchaseRequestApiDTO{" +
            "type='" + type + '\'' +
            ", user='" + user + '\'' +
            ", timeRequestPR='" + timeRequestPR + '\'' +
            ", remark='" + remark + '\'' +
            ", mrpSubCode='" + mrpSubCode + '\'' +
            ", soCode='" + soCode + '\'' +
            ", createTime='" + createTime + '\'' +
            ", timeRequest='" + timeRequest + '\'' +
            ", timeApproved='" + timeApproved + '\'' +
            ", status='" + status + '\'' +
            ", purchaseRequestDetailApiDTOS=" + purchaseRequestDetailApiDTOS +
            '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTimeRequestPR() {
        return timeRequestPR;
    }

    public void setTimeRequestPR(String timeRequestPR) {
        this.timeRequestPR = timeRequestPR;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMrpSubCode() {
        return mrpSubCode;
    }

    public void setMrpSubCode(String mrpSubCode) {
        this.mrpSubCode = mrpSubCode;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTimeRequest() {
        return timeRequest;
    }

    public void setTimeRequest(String timeRequest) {
        this.timeRequest = timeRequest;
    }

    public String getTimeApproved() {
        return timeApproved;
    }

    public void setTimeApproved(String timeApproved) {
        this.timeApproved = timeApproved;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("DetailPR")
    public List<PurchaseRequestDetailApiDTO> getPurchaseRecommendationDetails() {
        return purchaseRequestDetailApiDTOS;
    }

    public void setPurchaseRecommendationDetails(List<PurchaseRequestDetailApiDTO> purchaseRequestDetailApiDTOS) {
        this.purchaseRequestDetailApiDTOS = purchaseRequestDetailApiDTOS;
    }
}
