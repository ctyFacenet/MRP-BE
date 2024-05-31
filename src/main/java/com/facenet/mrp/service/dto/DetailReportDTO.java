package com.facenet.mrp.service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailReportDTO {
    private String mrpCode;
    private String itemCode;
    private String itemName;
    private String vendorCode;
    private String vendorName;
}
