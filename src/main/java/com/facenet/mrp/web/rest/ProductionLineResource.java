package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.ProductionLineService;
import com.facenet.mrp.service.dto.scada.ProductionLineDTO;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.model.ProductionLineFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/production-lines")
public class ProductionLineResource {
    private final ProductionLineService productionLineService;

    public ProductionLineResource(ProductionLineService productionLineService) {
        this.productionLineService = productionLineService;
    }

    /**
     * API lấy danh sách dây chuyền sản xuất, máy móc
     * @param input
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH', 'TK', 'HT', 'KTV', 'QLSX','LINE')")
    public PageResponse<List<ProductionLineDTO>> getAllProductionLines(@RequestBody @Valid PageFilterInput<ProductionLineFilter> input) throws JsonProcessingException {
        return productionLineService.getAllProductionLines(input);
    }
}
