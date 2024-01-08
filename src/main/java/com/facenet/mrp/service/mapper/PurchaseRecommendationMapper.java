package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.PurchaseRecommendationEntity;
import com.facenet.mrp.service.dto.DetailItemSyntheticDTO;
import com.facenet.mrp.service.dto.ItemSyntheticDTO;
import com.facenet.mrp.service.dto.SyntheticMrpDTO;
import com.facenet.mrp.service.dto.mrp.ForecastMaterialDTO;
import com.facenet.mrp.service.dto.mrp.LandMarkDTO;
import com.facenet.mrp.service.dto.mrp.MaterialPlanDTO;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.utils.Constants;
import com.facenet.mrp.service.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class PurchaseRecommendationMapper {
    public PurchaseRecommendationEntity toPurchaseRecommendationEntity(ForecastMaterialDTO forecastMaterialDTO) throws ParseException {
        PurchaseRecommendationEntity result = new PurchaseRecommendationEntity();
        result.setMrpPoId(forecastMaterialDTO.getForecastCode());
        int itemCount = 0;
        double totalQuantity;
        for (MaterialPlanDTO materialPlanDTO : forecastMaterialDTO.getListData()) {
            // Calculate total quantity
            //Tính tổng quantity trong thời gian kế hoạch cho vật tư
            if (materialPlanDTO.getItemStartTime() == null && materialPlanDTO.getItemEndTime() == null){
                totalQuantity = Utils.getTotalItem(forecastMaterialDTO.getForecastMode(),forecastMaterialDTO.getStartDate(),forecastMaterialDTO.getEndDate(),materialPlanDTO.getListLandMark());
            } else if (materialPlanDTO.getItemStartTime() != null && materialPlanDTO.getItemEndTime() == null) {
                totalQuantity = Utils.getTotalItem(forecastMaterialDTO.getForecastMode(),materialPlanDTO.getItemStartTime(),forecastMaterialDTO.getEndDate(),materialPlanDTO.getListLandMark());
            } else if (materialPlanDTO.getItemStartTime() == null && materialPlanDTO.getItemEndTime() != null) {
                totalQuantity = Utils.getTotalItem(forecastMaterialDTO.getForecastMode(),forecastMaterialDTO.getStartDate(),materialPlanDTO.getItemEndTime(),materialPlanDTO.getListLandMark());
            } else {
                totalQuantity = Utils.getTotalItem(forecastMaterialDTO.getForecastMode(),materialPlanDTO.getItemStartTime(),materialPlanDTO.getItemEndTime(),materialPlanDTO.getListLandMark());
            }

            // Ignore unchecked and quantity = 0
            if (materialPlanDTO.getChecked() && totalQuantity > 0 && "NVL".equals(materialPlanDTO.getGroupItem())) itemCount++;
        }
        if (itemCount == 0) throw new CustomException("no.item.recommended");
        result.setTotalItems(itemCount);
        result.setStartTime(forecastMaterialDTO.getStartDate());
        result.setEndTime(forecastMaterialDTO.getEndDate());
        result.setNote(forecastMaterialDTO.getNote());
        result.setStatus(Constants.PurchaseRecommendation.STATUS_NEW);
        return result;
    }

    public PurchaseRecommendationEntity toPurchaseRecommendationEntity(SyntheticMrpDTO syntheticMrpDTO) {
        PurchaseRecommendationEntity result = new PurchaseRecommendationEntity();
        int totalItems = 0;
        for (ItemSyntheticDTO item : syntheticMrpDTO.getResultData()) {
            for (DetailItemSyntheticDTO analysisResult : item.getDetailData()) {
                if (analysisResult.getOriginQuantity() != 0) {
                    totalItems++;
                    break;
                }
            }
        }
        if (totalItems == 0) throw new CustomException("no.item.recommended");
        result.setTotalItems(totalItems);
        result.setMrpPoId(syntheticMrpDTO.getSoCode());
        result.setMrpSubCode(syntheticMrpDTO.getMrpSubCode());
        result.setStartTime(syntheticMrpDTO.getTimeStart());
        result.setEndTime(syntheticMrpDTO.getTimeEnd());
        result.setStatus(Constants.PurchaseRecommendation.STATUS_NEW);
        return result;
    }
}
