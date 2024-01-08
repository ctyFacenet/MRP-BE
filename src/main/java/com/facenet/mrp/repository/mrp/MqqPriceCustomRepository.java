package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.service.dto.MqqLeadTimeDTO;

public interface MqqPriceCustomRepository {

    MqqLeadTimeDTO findMqqPriceAndLeadTime(String vendorCode, String itemCode);

//    List<ListOfUnitPricesDTO> getMqqPriceWithMinValue(List<ListOfUnitPricesDTO> dtoList);

}
