package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.ProductOrder;
import com.facenet.mrp.service.dto.ProductOrderDto;
import com.facenet.mrp.service.dto.ReportDTO;
import com.facenet.mrp.service.model.ProductOrderFilter;
import com.facenet.mrp.service.model.ReportFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductOrderCustomRepository {
    public List<ProductOrder> getReportBySoCode( ReportFilter filter);
    Page<ProductOrderDto> getProductOrderByStatus(Pageable pageable, ProductOrderFilter filter);
}
