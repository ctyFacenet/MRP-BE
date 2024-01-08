package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.MqqPriceEntity;
import com.facenet.mrp.repository.mrp.MqqPriceRepository;
import com.facenet.mrp.repository.mrp.PromotionRepository;
import com.facenet.mrp.repository.mrp.VendorItemRepository;
import com.facenet.mrp.repository.sap.Itm2Repository;
import com.facenet.mrp.service.dto.PriceListDTO;
import com.facenet.mrp.service.dto.PromotionDTO;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.MqqPriceMapper;
import com.facenet.mrp.service.mapper.PromotionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionService {

    private static final Logger logger = LoggerFactory.getLogger(PromotionService.class);

    private final PromotionRepository promotionRepository;

    private final MqqPriceRepository mqqPriceRepository;
    private final Itm2Repository itm2Repository;

    private final PromotionMapper mapper;

    private final MqqPriceMapper mqqPriceMapper;
    private final VendorItemRepository vendorItemRepository;


    public PromotionService(PromotionRepository promotionRepository, MqqPriceRepository mqqPriceRepository, Itm2Repository itm2Repository, PromotionMapper mapper, MqqPriceMapper mqqPriceMapper,
                            VendorItemRepository vendorItemRepository) {
        this.promotionRepository = promotionRepository;
        this.mqqPriceRepository = mqqPriceRepository;
        this.itm2Repository = itm2Repository;
        this.mapper = mapper;
        this.mqqPriceMapper = mqqPriceMapper;
        this.vendorItemRepository = vendorItemRepository;
    }

    public PromotionDTO getNewestPromotionDTo(String vendorCode, String itemCode){

        //Query lấy dữ liệu khoảng thời gian khuyến mại
//        List<PromotionDTO> promotionDTOS = promotionRepository.getAllTimeInPromotion(vendorCode, itemCode);
        List<PromotionDTO> promotionDTOS = mqqPriceRepository.getAllTimeInPromotion(vendorCode, itemCode);

        //Lấy thông tin khuyến mãi có khoảng thời gian tạo mới nhất
        if (!promotionDTOS.isEmpty()){
            PromotionDTO result = promotionDTOS.get(0);

            //Query láy dữ liệu khuyến mãi ( Price )  khoảng thời gian
            List<PriceListDTO> priceListDTOS = mqqPriceRepository.getAllPriceList(vendorCode, itemCode, result.getStartDate(), result.getEndDate());

            if (!priceListDTOS.isEmpty()){
                result.setPriceList(priceListDTOS);
                return result;
            } else {
                logger.info("Không tìm thấy priceList trong khuyến mại");
            }
        }

        return null;
    }

    public void createNewPromotion (String vendorCode, String itemCode, PromotionDTO dto){

//        PromotionEntity promotion;
//        List<PromotionEntity> promotionEntityList = new ArrayList<>();
        if (!vendorItemRepository.existsByItemCodeAndVendorCode(itemCode, vendorCode))
            throw new CustomException(HttpStatus.BAD_REQUEST, "item.does.not.belong.to.vendor", itemCode, vendorCode);

        MqqPriceEntity mqqPriceEntity;
        List<MqqPriceEntity> mqqEntityList = new ArrayList<>();

        for (PriceListDTO priceListDTO : dto.getPriceList()) {
//            promotion = mapper.dtoToEntity(vendorCode, itemCode,dto,priceListDTO);
//            promotionEntityList.add(promotion);

            mqqPriceEntity = mqqPriceMapper.dtoToEntity(vendorCode, itemCode,dto,priceListDTO);
            mqqPriceEntity.setIsPromotion(true);
            mqqEntityList.add(mqqPriceEntity);
        }

//        promotionRepository.saveAll(promotionEntityList);
        mqqPriceRepository.saveAll(mqqEntityList);
    }
}
