package com.facenet.mrp.service.model;

import com.facenet.mrp.domain.mrp.MrpProductionQuantityEntity;
import com.facenet.mrp.repository.mrp.MrpProductionQuantityRepository;
import com.facenet.mrp.service.AnalysisDetailReportService;
import com.facenet.mrp.service.dto.ComparisonQuantityDTO;
import com.facenet.mrp.service.dto.MrpActualInputDTO;
import com.facenet.mrp.service.dto.MrpActualOutputDTO;
import com.facenet.mrp.service.dto.MrpCheckReportIntegrationInputDTO;
import com.facenet.mrp.service.dto.MrpCheckReportIntegrationOutputDTO;
import com.facenet.mrp.service.dto.ReportComparisonDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.MrpProductionQuantityMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComparisonReportService {
    private final MrpProductionQuantityRepository mrpProductionQuantityRepository;
    private final MrpProductionQuantityMapper mrpProductionQuantityMapper;
    private final EntityManager entityManager;

    @Value("${report.get-mrp-integration-url}")
    private String apiMrpGetCheckReportIntegrationDataFromReport;


    public ComparisonReportService(MrpProductionQuantityRepository mrpProductionQuantityRepository, MrpProductionQuantityMapper mrpProductionQuantityMapper, @Qualifier("mrpEntityManager") EntityManager entityManager) {
        this.mrpProductionQuantityRepository = mrpProductionQuantityRepository;
        this.mrpProductionQuantityMapper = mrpProductionQuantityMapper;
        this.entityManager = entityManager;
    }

    public CommonResponse<ReportComparisonDTO> getComparisonReport(ComparisonReportFilter filter) {
        if (filter.getAnalysisMode() == null || filter.getEndDate() == null || filter.getStartDate() == null)
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

            List<MrpActualInputDTO> mrpActualInputDTOList = new ArrayList<>();
            for (ReportComparisonDTO resultData : result) {
                MrpActualInputDTO mrpActualInputDTO = new MrpActualInputDTO();
                mrpActualInputDTO.setSoCode(resultData.getSoCode());
                mrpActualInputDTO.setBomVersion(resultData.getVersion());
                mrpActualInputDTO.setProductCode(resultData.getItemCode());
                mrpActualInputDTOList.add(mrpActualInputDTO);
            }

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            MrpCheckReportIntegrationInputDTO mrpCheckReportIntegrationDTO = new MrpCheckReportIntegrationInputDTO();
            mrpCheckReportIntegrationDTO.setStartTime(startTime.getTime());
            mrpCheckReportIntegrationDTO.setEndTime(endTime.getTime());
            mrpCheckReportIntegrationDTO.setMrpActualDTO(mrpActualInputDTOList);
            HttpEntity<MrpCheckReportIntegrationInputDTO> httpEntity = new HttpEntity<>(mrpCheckReportIntegrationDTO,headers);
            MrpCheckReportIntegrationOutputDTO responseBody = restTemplate.exchange(apiMrpGetCheckReportIntegrationDataFromReport, HttpMethod.POST, httpEntity, MrpCheckReportIntegrationOutputDTO.class).getBody();



            for (ReportComparisonDTO resultData : result) {

                double mrpQuantity = 0;
                List<MrpProductionQuantityEntity> itemQuantityList = mrpItemMap.get(resultData.getMrpCode()).get(resultData.getItemCode());
                // Sum quantity between startTime & endTime
                for (MrpProductionQuantityEntity itemQuantity : itemQuantityList) {
                    if (itemQuantity.getDueDate().compareTo(startTime.getTime()) >= 0 && itemQuantity.getDueDate().compareTo(endTime.getTime()) < 0) {
                        mrpQuantity += itemQuantity.getProductionQuantity();
                    }
                }

                //TODO: Add importQuantity, qmsQuantity
                Double actualQuantity = responseBody.getHashMap().get(resultData.getSoCode() + responseBody.getSplitString() + resultData.getItemCode() + responseBody.getSplitString() + resultData.getVersion()).getActualQuantity();
                Double qmsQuantity = responseBody.getHashMap().get(resultData.getSoCode() + responseBody.getSplitString() + resultData.getItemCode() + responseBody.getSplitString() + resultData.getVersion()).getQmsQuantity();
                ComparisonQuantityDTO resultQuantity = new ComparisonQuantityDTO();
                resultQuantity.setMrpQuantity(mrpQuantity);
                resultQuantity.setActualQuantity(actualQuantity);
                resultQuantity.setImportWhsQMS(qmsQuantity);
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

        return new CommonResponse<>().success().data(result);
    }
}
