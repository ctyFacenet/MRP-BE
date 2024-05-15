package com.facenet.mrp.service.model;

import com.facenet.mrp.domain.mrp.MrpProductionQuantityEntity;
import com.facenet.mrp.domain.mrp.QMrpProductionQuantityEntity;
import com.facenet.mrp.repository.mrp.MrpProductionQuantityRepository;
import com.facenet.mrp.repository.mrp.PqcStoreCheckRepository;
import com.facenet.mrp.repository.mrp.ProductWorkOrderQuantityRepository;
import com.facenet.mrp.service.AnalysisDetailReportService;
import com.facenet.mrp.service.dto.ComparisonQuantityDTO;
import com.facenet.mrp.service.dto.ReportComparisonDTO;
import com.facenet.mrp.service.dto.request.QmsQuantityDailyDTO;
import com.facenet.mrp.service.dto.request.ScadaQuantityDailyDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.MrpProductionQuantityMapper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MultiMapUtils;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;

@Service
public class ComparisonReportService {
    private final MrpProductionQuantityRepository mrpProductionQuantityRepository;
    private final MrpProductionQuantityMapper mrpProductionQuantityMapper;
    private final EntityManager entityManager;
    private final ProductWorkOrderQuantityRepository workOrderQuantityRepository;
    private final PqcStoreCheckRepository storeCheckRepository;

    public ComparisonReportService(MrpProductionQuantityRepository mrpProductionQuantityRepository, MrpProductionQuantityMapper mrpProductionQuantityMapper, @Qualifier("mrpEntityManager") EntityManager entityManager,
                                   ProductWorkOrderQuantityRepository workOrderQuantityRepository, PqcStoreCheckRepository storeCheckRepository ) {
        this.mrpProductionQuantityRepository = mrpProductionQuantityRepository;
        this.mrpProductionQuantityMapper = mrpProductionQuantityMapper;
        this.entityManager = entityManager;
        this.workOrderQuantityRepository = workOrderQuantityRepository;
        this.storeCheckRepository = storeCheckRepository;
    }

    public CommonResponse<ReportComparisonDTO> getComparisonReport(ComparisonReportFilter filter) {
        if (filter.getAnalysisMode() == null
            || filter.getEndDate() == null
            || filter.getStartDate() == null)
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.param");

        List<MrpProductionQuantityEntity> data = mrpProductionQuantityRepository.findAllByDueDateBetween(filter.getStartDate(), filter.getEndDate());
        if (CollectionUtils.isEmpty(data)) throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");

        List<ReportComparisonDTO> result = new ArrayList<>();

        Map<String, Map<String, List<MrpProductionQuantityEntity>>> mrpItemMap = new HashMap<>();
        for (MrpProductionQuantityEntity quantityData : data) {
            if (!mrpItemMap.containsKey(quantityData.getMrpSubCode())) {
                mrpItemMap.put(quantityData.getMrpSubCode(), new HashMap<>());
            }
            Map<String, List<MrpProductionQuantityEntity>> itemCodeMap = mrpItemMap.get(quantityData.getMrpSubCode());
            if (!itemCodeMap.containsKey(quantityData.getItemCode())) {
                itemCodeMap.put(quantityData.getItemCode(), new ArrayList<>());
                result.add(mrpProductionQuantityMapper.toDTO(quantityData));
            }
            itemCodeMap.get(quantityData.getItemCode()).add(quantityData);
        }

        //xử lý lấy sản lượng qms daily
        List<QmsQuantityDailyDTO> qmsQuantityDailyDTOS = storeCheckRepository.findAllByDueDateBetween(filter.getStartDate(),filter.getEndDate());
        Map<String, Map<String, List<QmsQuantityDailyDTO>>> qmsItemMap = new HashMap<>();
        for (QmsQuantityDailyDTO qmsQuantityDailyDTO: qmsQuantityDailyDTOS){
            if(!qmsItemMap.containsKey(qmsQuantityDailyDTO.getPurchaseOrderCode())){
                qmsItemMap.put(qmsQuantityDailyDTO.getPurchaseOrderCode(),new HashMap<>());
            }
            Map<String,List<QmsQuantityDailyDTO>> qmsItemCodeMap = qmsItemMap.get(qmsQuantityDailyDTO.getPurchaseOrderCode());
            if(!qmsItemCodeMap.containsKey(qmsQuantityDailyDTO.getProductionCode())){
                qmsItemCodeMap.put(qmsQuantityDailyDTO.getProductionCode(), new ArrayList<>());
            }
            qmsItemCodeMap.get(qmsQuantityDailyDTO.getProductionCode()).add(qmsQuantityDailyDTO);
        }

        //xử lý lấy sản lượng scada daily
        List<ScadaQuantityDailyDTO> scadaQuantityDailyDTOS = workOrderQuantityRepository.findAllByDueDateBetween(filter.getStartDate(),filter.getEndDate());
        Map<String, Map<String, List<ScadaQuantityDailyDTO>>> scadaItemMap = new HashMap<>();
        for (ScadaQuantityDailyDTO scadaQuantityDailyDTO: scadaQuantityDailyDTOS){
            if(!scadaItemMap.containsKey(scadaQuantityDailyDTO.getMrpCode())){
                scadaItemMap.put(scadaQuantityDailyDTO.getMrpCode(),new HashMap<>());
            }
            Map<String,List<ScadaQuantityDailyDTO>> scadaItemCodeMap = scadaItemMap.get(scadaQuantityDailyDTO.getMrpCode());
            if(!scadaItemCodeMap.containsKey(scadaQuantityDailyDTO.getProductCode())){
                scadaItemCodeMap.put(scadaQuantityDailyDTO.getProductCode(), new ArrayList<>());
            }
            scadaItemCodeMap.get(scadaQuantityDailyDTO.getProductCode()).add(scadaQuantityDailyDTO);
        }

        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        startTime.setTime(filter.getStartDate());
        endTime.setTime(filter.getEndDate());
        switch (filter.getAnalysisMode()) {
            case 1: // Week
                if (startTime.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
                    startTime.add(Calendar.DATE, 7 - startTime.get(Calendar.DAY_OF_WEEK) + 1);
                if (endTime.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
                    endTime.add(Calendar.DATE, 7 - endTime.get(Calendar.DAY_OF_WEEK) + 1);
                break;
            case 2: // Month
                endTime.set(Calendar.DATE, endTime.getActualMaximum(Calendar.DAY_OF_MONTH));
                startTime.set(Calendar.DATE, startTime.getActualMaximum(Calendar.DAY_OF_MONTH));
                break;
            case 3: // Quarter
                startTime.setTime(AnalysisDetailReportService.getLastDayOfQuarter(filter.getStartDate()));
                endTime.setTime(AnalysisDetailReportService.getLastDayOfQuarter(filter.getEndDate()));
                break;
            default:
                break;
        }


        while (startTime.compareTo(endTime) <= 0) {
            for (ReportComparisonDTO resultData : result) {
                double mrpQuantity = 0;
                List<MrpProductionQuantityEntity> itemQuantityList = mrpItemMap.get(resultData.getMrpCode()).get(resultData.getItemCode());
                // Sum quantity between startTime & endTime
                for (MrpProductionQuantityEntity itemQuantity : itemQuantityList) {
                    if (itemQuantity.getDueDate().compareTo(startTime.getTime()) >= 0
                        && itemQuantity.getDueDate().compareTo(endTime.getTime()) < 0) {
                        mrpQuantity += itemQuantity.getProductionQuantity();
                    }
                }

                //TODO: Add importQuantity, qmsQuantity
                ComparisonQuantityDTO resultQuantity = new ComparisonQuantityDTO();
                resultQuantity.setMrpQuantity(mrpQuantity);
                resultData.getCompareStats().add(resultQuantity);
            }
            startTime.add(Calendar.DATE, 1);

            switch (filter.getAnalysisMode()) {
                case 1:
                    startTime.add(Calendar.DATE, 7 - startTime.get(Calendar.DAY_OF_WEEK) + 1);
                    break;
                case 2:
                    startTime.set(Calendar.DATE, startTime.getActualMaximum(Calendar.DAY_OF_MONTH));
                    break;
                case 3:
                    startTime.setTime(AnalysisDetailReportService.getLastDayOfQuarter(startTime.getTime()));
                    break;
                default:
                    break;
            }
        }

        return new CommonResponse<>()
            .success()
            .data(result);
    }
}
