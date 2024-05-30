package com.facenet.mrp.service.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ReportFilter {
    private String soCode;
    private String customerCode;
    private String customerName;
    private String saleCode;
    private Date orderDate;
    private Date deliveryDate;

}
