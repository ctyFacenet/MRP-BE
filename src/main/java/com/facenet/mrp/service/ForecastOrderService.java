package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.ForecastOrderDetailEntity;
import com.facenet.mrp.domain.mrp.ProductOrder;
import com.facenet.mrp.domain.mrp.QForecastOrderDetailEntity;
import com.facenet.mrp.domain.mrp.QProductOrder;
import com.facenet.mrp.repository.mrp.ForecastOrderDetailRepository;
import com.facenet.mrp.repository.mrp.ProductOrderRepository;
import com.facenet.mrp.repository.sap.OitwRepository;
import com.facenet.mrp.service.dto.ForecastOrderDTO;
import com.facenet.mrp.service.dto.ForecastOrderDetailDTO;
import com.facenet.mrp.service.dto.QForecastOrderDTO;
import com.facenet.mrp.service.dto.mrp.CurrentInventory;
import com.facenet.mrp.service.dto.mrp.ForecastMaterialDTO;
import com.facenet.mrp.service.dto.mrp.LandMarkDTO;
import com.facenet.mrp.service.dto.mrp.MaterialPlanDTO;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.ForrecastOrderMapper;
import com.facenet.mrp.service.model.*;
import com.facenet.mrp.service.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.el.parser.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ForecastOrderService {
    private final ProductOrderRepository productOrderRepository;
    private final ForecastOrderDetailRepository forecastOrderDetailRepository;
    private final EntityManager entityManager;
    private final ForrecastOrderMapper forrecastOrderMapper;
    private final OitwRepository oitwRepository;

    public ForecastOrderService(ForrecastOrderMapper forrecastOrderMapper, ForecastOrderDetailRepository forecastOrderDetailRepository, ProductOrderRepository productOrderRepository,
                                @Qualifier("mrpEntityManager") EntityManager entityManager, OitwRepository oitwRepository) {
        this.forrecastOrderMapper = forrecastOrderMapper;
        this.forecastOrderDetailRepository = forecastOrderDetailRepository;
        this.productOrderRepository = productOrderRepository;
        this.entityManager = entityManager;
        this.oitwRepository = oitwRepository;
    }

    private static final Logger log = LogManager.getLogger(MqqPriceService.class);

    public PageResponse<List<ForecastOrderDTO>> getAllForecastOrder(PageFilterInput<ForecastOrderDTO> input) {
        Pageable pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
        ForecastOrderDTO filter = input.getFilter();

        QProductOrder qProductOrder = QProductOrder.productOrder;
        JPAQuery<ForecastOrderDTO> query = new JPAQueryFactory(entityManager)
            .select(new QForecastOrderDTO(
                qProductOrder.productOrderCode,
                qProductOrder.forecastName,
                qProductOrder.orderDate,
                qProductOrder.deliverDate,
                qProductOrder.forecastMode,
                qProductOrder.warehouseList,
                qProductOrder.forecastSource,
                qProductOrder.priority,
                qProductOrder.note,
                qProductOrder.status,
                qProductOrder.createdBy,
                qProductOrder.createdAt
            ))
            .from(qProductOrder)
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qProductOrder.isActive.eq((byte) 1));
        booleanBuilder.and(qProductOrder.forecastName.isNotNull());
        if (!StringUtils.isEmpty(filter.getForecastOrderCode())) {
            booleanBuilder.and(qProductOrder.productOrderCode.like(filter.getForecastOrderCode()));
        }
        if (!StringUtils.isEmpty(filter.getForecastOrderName())) {
            booleanBuilder.and(qProductOrder.forecastName.like(filter.getForecastOrderName()));
        }
        if (filter.getStartTime() != null) {
            booleanBuilder.and(qProductOrder.orderDate.eq(filter.getStartTime()));
        }
        if (filter.getEndTime() != null) {
            booleanBuilder.and(qProductOrder.deliverDate.eq(filter.getEndTime()));
        }
        query.where(booleanBuilder).orderBy(qProductOrder.createdAt.desc());

        List<ForecastOrderDTO> result = query.fetch();
        long count = query.fetchCount();
        return new PageResponse<List<ForecastOrderDTO>>()
            .result("00", "Thành công", true)
            .data(result)
            .dataCount(count);
    }

    @Transactional
    public String deleteForecastOrder(String fcCode) {
        ProductOrder forecastOrder = productOrderRepository.findByProductOrderCode(fcCode);
        if (forecastOrder == null) {
            return null;
        }
        forecastOrder.setIsActive((byte) 2);
        productOrderRepository.save(forecastOrder);
        forecastOrderDetailRepository.deleteAllByFcCode(fcCode);
        return "OK";
    }

    public ForecastMaterialDTO getDetailForecast(String fcCode) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductOrder productOrder = forecastOrderDetailRepository.getForecastOrder(fcCode);
        if (productOrder == null) {
            return null;
        }
        ForecastMaterialDTO forecastMaterialDTO = new ForecastMaterialDTO();
        List<MaterialPlanDTO> materialPlanDTOList = new ArrayList<>();
        forecastMaterialDTO.setId(productOrder.getId());
        forecastMaterialDTO.setForecastCode(productOrder.getProductOrderCode());
        forecastMaterialDTO.setForecastName(productOrder.getForecastName());
        forecastMaterialDTO.setForecastMode(productOrder.getForecastMode());
        forecastMaterialDTO.setForecastSource(productOrder.getForecastSource());
        forecastMaterialDTO.setForecastWhs(productOrder.getWarehouseList());
        forecastMaterialDTO.setCreatedBy(productOrder.getCreatedBy());
        Date date = Date.from(productOrder.getCreatedAt());
        forecastMaterialDTO.setCreatedAt(date);
        forecastMaterialDTO.setEndDate(productOrder.getDeliverDate());
        forecastMaterialDTO.setStartDate(productOrder.getOrderDate());
        forecastMaterialDTO.setLevelPriority(productOrder.getPriority());
        List<String> landMark = objectMapper.readValue(productOrder.getForecastLandMark(), ArrayList.class);
        forecastMaterialDTO.setLandMark(landMark);
        List<ForecastOrderDetailEntity> list = forecastOrderDetailRepository.getListForecastOrderDetail(fcCode);
        List<CurrentInventory> inStockQuantity = oitwRepository.getAllCurrentInventoryByWhs(
            list.stream().map(ForecastOrderDetailEntity::getItemCode).collect(Collectors.toList()),
            List.of(productOrder.getWarehouseList().split(";"))
        );
        Map<String, Double> inStockQuantityMap = inStockQuantity.stream().collect(Collectors.toMap(CurrentInventory::getItemCode, CurrentInventory::getCurrentQuantity));
        if (list.size() == 0) {
            return null;
        }
        for (ForecastOrderDetailEntity item : list) {
            MaterialPlanDTO materialPlanDTO = new MaterialPlanDTO();
            materialPlanDTO.setItemCode(item.getItemCode());
            materialPlanDTO.setId(item.getForecastOrderDetailId());
            materialPlanDTO.setItemName(item.getItemDescription());
            materialPlanDTO.setBomVersion(item.getBomVersion());
            materialPlanDTO.setNote(item.getNote());
            materialPlanDTO.setGroupItem(item.getItemGroupCode());
            materialPlanDTO.setStatus(item.getStatus());
            materialPlanDTO.setLevelPriorityItem(item.getPriority());
            materialPlanDTO.setCurrentInventory(inStockQuantityMap.getOrDefault(item.getItemCode(), 0.0));
            materialPlanDTO.setItemEndTime(item.getEndTime());
            materialPlanDTO.setItemStartTime(item.getStartTime());
            materialPlanDTO.setTotalRequest(item.getTotalRequest());
            List<LandMarkDTO> landMarkDTO = objectMapper.readValue(item.getDetailResult(), ArrayList.class);
            materialPlanDTO.setListLandMark(landMarkDTO);
            materialPlanDTOList.add(materialPlanDTO);
        }
        forecastMaterialDTO.setListData(materialPlanDTOList);
        return forecastMaterialDTO;

    }

    public ProductOrderDetailResponse getAllAnalyticsForecast(String productOrderCode, ProductOrderDetailInput input) throws IOException, ParseException {

        List<ForecastOrderDetailDTO> dtoFcList = new ArrayList<>();
        List<String> poCodeList = new ArrayList<>();
        ResultCode resultCode = new ResultCode();
        ProductOrderDetailResponse response = new ProductOrderDetailResponse();
        HashMap<String, Double> inStockQuantityHM = new HashMap<>();
        double missingQuantity;
        Pageable pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
        ProductOrderDetailFilter filter = input.getFilter();

        QForecastOrderDetailEntity qForecastOrderDetail = QForecastOrderDetailEntity.forecastOrderDetailEntity;
        JPAQuery<ForecastOrderDetailEntity> query = new
            JPAQueryFactory(entityManager)
            .selectFrom(qForecastOrderDetail)
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset());
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qForecastOrderDetail.productOrderCode.eq(productOrderCode));
        booleanBuilder.and(qForecastOrderDetail.status.goe(2));
        booleanBuilder.and(qForecastOrderDetail.isActive.eq(true));
        if (!StringUtils.isEmpty(filter.getProductCode())) {
            booleanBuilder.and(qForecastOrderDetail.itemCode.containsIgnoreCase(filter.getProductCode()));
        }
        if (!StringUtils.isEmpty(filter.getProductName())) {
            booleanBuilder.and(qForecastOrderDetail.itemDescription.containsIgnoreCase(filter.getProductName()));
        }
        if (!StringUtils.isEmpty(filter.getBomVersion())) {
            booleanBuilder.and(qForecastOrderDetail.bomVersion.containsIgnoreCase(filter.getBomVersion()));
        }
        if (!StringUtils.isEmpty(filter.getOrderQuantity())) {
            booleanBuilder.and(qForecastOrderDetail.quantity.eq(Double.valueOf(Integer.valueOf(filter.getOrderQuantity()))));
        }
        if (filter.getOrderedTime() != null) {
            booleanBuilder.and(qForecastOrderDetail.startTime.eq(filter.getOrderedTime()));
        }
        if (filter.getDeliveryTime() != null) {
            booleanBuilder.and(qForecastOrderDetail.endTime.eq(filter.getDeliveryTime()));
        }

        query.where(booleanBuilder).orderBy(qForecastOrderDetail.updatedAt.desc());
        System.err.println(query);
        List<ForecastOrderDetailEntity> result = query.fetch();
        long count = query.fetchCount();
        Page<ForecastOrderDetailEntity> productOrderDetailPage = new PageImpl<>(result, pageable, count);

        log.info("productOrderDetailPage.content: {}", productOrderDetailPage.getContent().size());

        if (productOrderDetailPage.getContent().isEmpty() || productOrderDetailPage.getContent() == null) {
            throw new CustomException("record.notfound");
        } else if (!productOrderDetailPage.getContent().isEmpty() || !(productOrderDetailPage.getContent() == null)) {
            resultCode.setResponseCode("00");
            resultCode.setMessage("Thành công");
            resultCode.setOk(true);

            productOrderDetailPage.getContent()
                .forEach(forecastOrderDetail -> poCodeList.add(forecastOrderDetail.getItemCode()));

            List<CurrentInventory> currentInventoryList = oitwRepository.getAllInStockQuantityByItemCode(poCodeList);

            for (String poCode : poCodeList) {
                for (CurrentInventory item : currentInventoryList) {
                    if (poCode.equals(item.getItemCode())) {
                        inStockQuantityHM.put(poCode, item.getCurrentQuantity());
                    }
                }
            }
            log.info("poCodeList: {}", poCodeList);
            log.info("inStockQuantityHM: {}", inStockQuantityHM);

            for (ForecastOrderDetailEntity detail : productOrderDetailPage.getContent()) {
                Double inStockQuantity = inStockQuantityHM.get(detail.getItemCode());
                if (inStockQuantity == null) inStockQuantity = 0.0;
                missingQuantity = Double.valueOf(100000) - inStockQuantity;
                if (missingQuantity <= 0) {
                    missingQuantity = 0.0;
                }
                dtoFcList.add(forrecastOrderMapper.entityToDto(
                    detail,
                    inStockQuantityHM.get(detail.getItemCode()),
                    String.format("%.1f", missingQuantity)));
            }
            response.setResult(resultCode);
            response.setDataCount(productOrderDetailPage.getTotalElements());
            response.setFcData(dtoFcList);
        }
        return response;
    }
}
