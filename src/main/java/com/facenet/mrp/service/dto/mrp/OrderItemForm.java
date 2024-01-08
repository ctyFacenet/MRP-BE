package com.facenet.mrp.service.dto.mrp;

import java.util.Date;
import java.util.List;

public class OrderItemForm {
    private String soCode;
    private String mrpCode;
    private List<String> itemCode;
    private Date timeStart;
    private Date timeEnd;

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    public List<String> getItemCode() {
        return itemCode;
    }

    public void setItemCode(List<String> itemCode) {
        this.itemCode = itemCode;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }
}
