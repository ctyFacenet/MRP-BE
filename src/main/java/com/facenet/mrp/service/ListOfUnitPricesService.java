package com.facenet.mrp.service;

import com.facenet.mrp.repository.mrp.MqqPriceRepository;
import com.facenet.mrp.repository.mrp.PromotionRepository;
import com.facenet.mrp.repository.sap.OitmRepository;
import com.facenet.mrp.service.dto.ListOfUnitPricesDTO;
import com.facenet.mrp.service.dto.MinMqqPriceLeadTimeDTO;
import com.facenet.mrp.service.dto.PromotionDTO;
import com.facenet.mrp.service.model.ListOfUnitPricesFilter;
import com.facenet.mrp.service.model.RequestInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListOfUnitPricesService {

    private static final Logger logger = LoggerFactory.getLogger(ListOfUnitPricesService.class);

    private final MqqPriceRepository mqqPriceRepository;

    private final OitmRepository oitmRepository;

    public ListOfUnitPricesService(MqqPriceRepository mqqPriceRepository, OitmRepository oitmRepository) {
        this.mqqPriceRepository = mqqPriceRepository;
        this.oitmRepository = oitmRepository;
    }

    public Page<ListOfUnitPricesDTO> getPageListOfUnitPrices(RequestInput<ListOfUnitPricesFilter> input){
        MinMqqPriceLeadTimeDTO priceLeadTimeDTO;
        List<PromotionDTO> promotionDTOS;
        List<ListOfUnitPricesDTO> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());

        //Query lấy dữ liệu vendor và item trên sap
        Page<ListOfUnitPricesDTO> dtoPage = oitmRepository.getVendorItemFromSap(pageable, input.getFilter());

        //Lấy dữ liệu price MQQ, leadTime và Status của khuyến mại theo từng cặp vendorCode và itemCode
        for (ListOfUnitPricesDTO dto : dtoPage.getContent()) {
            //Lấy dữ liệu price MQQ, leadTime
            priceLeadTimeDTO = mqqPriceRepository.findMqqAndLeadTimeWithMinPrice(dto.getVendorCode(), dto.getProductCode());

            //Check xem vật tư này có chương trình khuyến mại không
            promotionDTOS = mqqPriceRepository.getAllTimeInPromotion(dto.getVendorCode(), dto.getProductCode());

            if (promotionDTOS.isEmpty()){
                dto.setStatus("Giá MOQ");
            }else {
                dto.setStatus("Đang khuyến mãi");
            }

            dto.setLeadTime(priceLeadTimeDTO.getLeadTime());
            dto.setUnitPrice(priceLeadTimeDTO.getMqqPrice());
            dto.setMinQuantity(priceLeadTimeDTO.getMinQuantity());
            dto.setMaxQuantity(priceLeadTimeDTO.getMaxQuantity());
            dto.setBillDate(priceLeadTimeDTO.getDueDate());
            dto.setTransactionMoney(priceLeadTimeDTO.getCurrency());
            resultList.add(dto);
        }

        return new PageImpl<>(resultList, pageable, dtoPage.getTotalElements());
    }

}
