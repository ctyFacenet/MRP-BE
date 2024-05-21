package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.*;
import com.facenet.mrp.repository.mrp.ForecastOrderDetailRepository;
import com.facenet.mrp.repository.mrp.ProductOrderDetailRepository;
import com.facenet.mrp.repository.mrp.ProductOrderRepository;
import com.facenet.mrp.repository.sap.CoittRepository;
import com.facenet.mrp.repository.sap.OitwRepository;
import com.facenet.mrp.security.SecurityUtils;
import com.facenet.mrp.service.dto.ForecastOrderDetailDTO;
import com.facenet.mrp.service.dto.ProductOrderDetailDto;
import com.facenet.mrp.service.dto.mrp.CurrentInventory;
import com.facenet.mrp.service.dto.mrp.ItemQuantity;
import com.facenet.mrp.service.dto.mrp.MrpDetailDTO;
import com.facenet.mrp.service.dto.mrp.PlanningProductionOrder;
import com.facenet.mrp.service.dto.request.SendAnalysisRequest;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.ForrecastOrderMapper;
import com.facenet.mrp.service.mapper.ProductOrderDetailMapper;
import com.facenet.mrp.service.model.ProductOrderDetailFilter;
import com.facenet.mrp.service.model.ProductOrderDetailInput;
import com.facenet.mrp.service.model.ProductOrderDetailResponse;
import com.facenet.mrp.service.model.ResultCode;
import com.facenet.mrp.service.utils.Constants;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.el.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductOrderDetailService {
    private static final Logger logger = LoggerFactory.getLogger(ProductOrderDetailService.class);

    private final ProductOrderDetailRepository detailRepository;

    private final ProductOrderRepository productOrderRepository;

    private final OitwRepository oitwRepository;

    private final ProductOrderDetailMapper mapper;

    private final ForrecastOrderMapper forrecastOrderMapper;

    private EntityManager entityManager;

    private final ForecastOrderDetailRepository forecastOrderDetailRepository;

    private final ProductOrderService productOrderService;

    private final CoittRepository coittRepository;

    public ProductOrderDetailService(ForrecastOrderMapper forrecastOrderMapper, ProductOrderDetailRepository detailRepository, ProductOrderRepository productOrderRepository, OitwRepository oitwRepository, ProductOrderDetailMapper mapper, @Qualifier("mrpEntityManager") EntityManager entityManager, ForecastOrderDetailRepository forecastOrderDetailRepository, ProductOrderService productOrderService, CoittRepository coittRepository) {
        this.forrecastOrderMapper = forrecastOrderMapper;
        this.detailRepository = detailRepository;
        this.productOrderRepository = productOrderRepository;
        this.oitwRepository = oitwRepository;
        this.mapper = mapper;
        this.entityManager = entityManager;
        this.forecastOrderDetailRepository = forecastOrderDetailRepository;
        this.productOrderService = productOrderService;
        this.coittRepository = coittRepository;
    }
    /**
     * Lấy sản phẩm groupBy Đơn hàng có hỗ trợ paging
     *
     * @param productOrderCode
     * @param input
     * @return
     */
    public ProductOrderDetailResponse getAllProductDetail(String productOrderCode, ProductOrderDetailInput input) throws IOException, ParseException {
        List<ProductOrderDetailDto> dtoList = new ArrayList<>();
        List<String> poCodeList = new ArrayList<>();
        ResultCode resultCode = new ResultCode();
        ProductOrderDetailResponse response = new ProductOrderDetailResponse();
        HashMap<String, Double> inStockQuantityHM = new HashMap<>();
        HashMap<String, Double> missingQuantityHM = new HashMap<>();
        double missingQuantity;
        Pageable pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
        ProductOrderDetailFilter filter = input.getFilter();
        QProductOrder qProductOrder = QProductOrder.productOrder;
        QProductOrderDetail qProductOrderDetail = QProductOrderDetail.productOrderDetail;
        JPAQuery<ProductOrderDetail> query = new
            JPAQueryFactory(entityManager)
            .selectFrom(qProductOrderDetail);
//            .join(qProductOrder).on(qProductOrder.productOrderCode.eq(String.valueOf(qProductOrderDetail.productOrderCode)))
//            .limit(pageable.getPageSize())
//            .offset(pageable.getOffset())
//            .orderBy(qProductOrderDetail.itemIndex.asc());
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qProductOrderDetail.productOrderCode.productOrderCode.eq(productOrderCode));
//        booleanBuilder.and(qProductOrder.isActive.eq((byte) 1));
        booleanBuilder.and(qProductOrderDetail.isActive.eq((byte) 1));
        if (!StringUtils.isEmpty(filter.getProductCode())) {
            booleanBuilder.and(qProductOrderDetail.productCode.containsIgnoreCase(filter.getProductCode()));
        }
        if (!StringUtils.isEmpty(filter.getProductName())) {
            booleanBuilder.and(qProductOrderDetail.productName.containsIgnoreCase(filter.getProductName()));
        }
        if (!StringUtils.isEmpty(filter.getBomVersion())) {
            booleanBuilder.and(qProductOrderDetail.bomVersion.containsIgnoreCase(filter.getBomVersion()));
        }
        if (!StringUtils.isEmpty(filter.getOrderQuantity())) {
            booleanBuilder.and(qProductOrderDetail.quantity.eq(Integer.valueOf(filter.getOrderQuantity())));
        }
        if (filter.getOrderedTime() != null) {
            Date orderedTime = filter.getOrderedTime();
            LocalDate localDate = orderedTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            booleanBuilder.and(qProductOrder.orderDate.year().eq(localDate.getYear())
                .and(qProductOrder.orderDate.month().eq(localDate.getMonthValue()))
                .and(qProductOrder.orderDate.dayOfMonth().eq(localDate.getDayOfMonth()  - 1)));
        }
        if (filter.getDeliveryTime() != null) {
            Date deliveryTime = filter.getDeliveryTime();
            LocalDate localDate = deliveryTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            booleanBuilder.and(qProductOrder.deliverDate.year().eq(localDate.getYear())
                .and(qProductOrder.deliverDate.month().eq(localDate.getMonthValue()))
                .and(qProductOrder.deliverDate.dayOfMonth().eq(localDate.getDayOfMonth() - 1)));
        }
        if (!StringUtils.isEmpty(filter.getSupplyMethod())) {
            booleanBuilder.and(qProductOrderDetail.supplyType.containsIgnoreCase(filter.getSupplyMethod()));
        }

        if (!StringUtils.isEmpty(filter.getProductOrderChild())) {
            booleanBuilder.and(qProductOrderDetail.productOrderChild.containsIgnoreCase(filter.getProductOrderChild()));
        }

        if (!StringUtils.isEmpty(filter.getCustomerCode())) {
            booleanBuilder.and(qProductOrderDetail.customerCode.containsIgnoreCase(filter.getCustomerCode()));
        }
        if (!StringUtils.isEmpty(filter.getCustomerName())) {
            booleanBuilder.and(qProductOrderDetail.customerName.containsIgnoreCase(filter.getCustomerName()));
        }

        if (!StringUtils.isEmpty(filter.getSaleCode())) {
            booleanBuilder.and(qProductOrderDetail.saleCode.containsIgnoreCase(filter.getSaleCode()));
        }

        query.where(booleanBuilder).orderBy(qProductOrderDetail.createdAt.desc());
        List<ProductOrderDetail> result = query.fetch();
        long count = query.fetchCount();
//        Page<ProductOrderDetail> productOrderDetailPage = new PageImpl<>(result, pageable, count);
        logger.info("productOrderDetailPage.content: {}", result.size());
        if (result.isEmpty() || result == null) {
            throw new CustomException("record.notfound");
//            logger.info("No record for product order {}", productOrderCode);
        } else if (!result.isEmpty() || !(result == null)) {
            resultCode.setResponseCode("00");
            resultCode.setMessage("Thành công");
            resultCode.setOk(true);

            result
                .forEach(productOrderDetail -> poCodeList.add(productOrderDetail.getProductCode()));

            List<CurrentInventory> currentInventoryList = oitwRepository.getAllInStockQuantityByItemCode(poCodeList);

            for (String poCode : poCodeList) {
                for (CurrentInventory item : currentInventoryList) {
                    if (poCode.equals(item.getItemCode())) {
                        inStockQuantityHM.put(poCode, item.getCurrentQuantity());
                    }
                }
            }
            logger.info("poCodeList: {}", poCodeList);
            logger.info("inStockQuantityHM: {}", inStockQuantityHM);

            for (ProductOrderDetail detail : result) {
                Double inStockQuantity = inStockQuantityHM.get(detail.getProductCode());
                if (inStockQuantity == null) inStockQuantity = 0.0;
                missingQuantity = Double.valueOf(detail.getQuantity()) - inStockQuantity;
                if (missingQuantity <= 0) {
                    missingQuantity = 0.0;
                }

                dtoList.add(mapper.entityToDto(
                    detail,
                    inStockQuantityHM.get(detail.getProductCode()),
                    String.format("%.1f", missingQuantity)));
            }
            response.setResult(resultCode);
            response.setDataCount(count);
            response.setData(dtoList);
        }
        return response;
    }

    /**
     * @param poCode
     * @param dto
     * @return
     */
    public void createNewProductOrderDetail(String poCode, ProductOrderDetailDto dto) throws IOException, ParseException {
        ItemQuantity countChildren = new ItemQuantity();
        List<MrpDetailDTO> detailDTOS;
        //tìm po chứa product để insert
        ProductOrder productOrder = productOrderRepository.findByProductOrderCode(poCode);
        ProductOrderDetail existDetail = detailRepository.getOneProductOrderDetail(dto.getProductCode(), poCode);
        if (productOrder == null) {
            throw new CustomException("record.notfound");
        }
        if (!(existDetail == null)) {
            throw new CustomException("product.order.detail.exist", dto.getProductCode());
        }
        //Count so NVl trong TP
        //query lấy children của sản phẩm TP
        detailDTOS = coittRepository.getAllMrpProductBom(dto.getProductCode(), dto.getBomVersion());
        logger.info("children của sản phẩm: {}", detailDTOS);
        //Cho vào hàm đệ quy để tìm các NVl/BTp con trong TP
        countChildren.setQuantity(0.0);
        productOrderService.getChildrenCountOfProduct(countChildren, detailDTOS);
        System.err.println("countChildren dff: " + countChildren.getQuantity());
        //Chuyển Dto thành entity
        ProductOrderDetail orderDetail = mapper.dtoToEntity(dto);
        orderDetail.setProductOrderCode(productOrder);
        orderDetail.setStatus(1);
        orderDetail.setIsActive((byte) 1);
        orderDetail.setStatusPlanning(1);
        orderDetail.setMaterialChildrenCount(countChildren.getQuantity().intValue());
        //Save product vao db
        try {
            detailRepository.save(orderDetail);
        } catch (RuntimeException e) {
            logger.error("createNewProductOrderDetail error", e);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "internal.error");
        }
    }

    @Transactional
    public Integer updateStatusProductInPO(String productCode, Byte status, String productOrderCode) {
        try {
            if (status < 1 || status > 6 || StringUtils.isEmpty(productCode)) {
                return -1;
            }
            ProductOrderDetail productOrderDetail = detailRepository.findByProductCode(productCode, productOrderCode);
            if (productOrderDetail == null) {
                return -2;
            }
            detailRepository.updateStatusProductInPO(status, productCode, productOrderCode);
            if (status == Constants.ProductOrder.CLOSED) {
                List<ProductOrderDetail> productOrderDetails = detailRepository.getAllBySO(productOrderCode);
                if (productOrderDetails.size() == 0) {
                    productOrderRepository.updateStatusPO(
                        Constants.ProductOrder.CLOSED,
                        productOrderCode,
                        SecurityUtils.getCurrentUserLogin().orElse("system"));
                }
            } else if (status == Constants.ProductOrder.STATUS_ORDER_ANALYTICS_FULL) {
                int newItemCount = detailRepository.countByItemOfSOAndStatusIn(
                    productOrderCode,
                    List.of(Constants.ProductOrder.STATUS_NEW, Constants.ProductOrder.STATUS_ORDER_ANALYTICS)
                );
                if (newItemCount == 0)
                    productOrderRepository.updateStatusPO(Constants.ProductOrder.STATUS_ORDER_ANALYTICS_FULL, productOrderCode, SecurityUtils.getCurrentUserLogin().orElse("system"));
            }
            return 1;
        } catch (Exception e) {
            logger.error("Lỗi cập nhật trạng thái sản phẩm", e);
            throw e;
        }
    }

    @Transactional
    public Integer updateStatusListProductInPO(List<SendAnalysisRequest> requestList) {
        for (SendAnalysisRequest item: requestList){
            try {
                if (item.getStatus() < 1 || item.getStatus() > 6 || StringUtils.isEmpty(item.getProductCode())) {
                    return -1;
                }
                ProductOrderDetail productOrderDetail = detailRepository.findByProductCode(item.getProductCode(), item.getPoCode());
                if (productOrderDetail == null) {
                    return -2;
                }
                detailRepository.updateStatusProductInPO(item.getStatus(), item.getProductCode(), item.getPoCode());
                if (item.getStatus() == Constants.ProductOrder.CLOSED) {
                    List<ProductOrderDetail> productOrderDetails = detailRepository.getAllBySO(item.getPoCode());
                    if (productOrderDetails.size() == 0) {
                        productOrderRepository.updateStatusPO(
                            Constants.ProductOrder.CLOSED,
                            item.getPoCode(),
                            SecurityUtils.getCurrentUserLogin().orElse("system"));
                    }
                } else if (item.getStatus() == Constants.ProductOrder.STATUS_ORDER_ANALYTICS_FULL) {
                    int newItemCount = detailRepository.countByItemOfSOAndStatusIn(
                        item.getPoCode(),
                        List.of(Constants.ProductOrder.STATUS_NEW, Constants.ProductOrder.STATUS_ORDER_ANALYTICS)
                    );
                    if (newItemCount == 0)
                        productOrderRepository.updateStatusPO(Constants.ProductOrder.STATUS_ORDER_ANALYTICS_FULL, item.getPoCode(), SecurityUtils.getCurrentUserLogin().orElse("system"));
                }
            } catch (Exception e) {
                logger.error("Lỗi cập nhật trạng thái sản phẩm", e);
                throw e;
            }
        }
        return 1;
    }

    public Integer deleteProductInPO(String productOrderCode, String productCode) {
        try {
            int in = 0;
            if (StringUtils.isEmpty(productCode)) {
                return -1;
            }

            if (productOrderCode.contains("RAL-FC")) {
                ForecastOrderDetailEntity forecastOrderDetail = forecastOrderDetailRepository.findForecastDetailByItemCode(productOrderCode, productCode);
                if (forecastOrderDetail == null) {
                    return -2;
                }

                in = forecastOrderDetailRepository.deleteFCDetailByItemCode(productOrderCode, productCode, SecurityUtils.getCurrentUserLogin().orElse(""));
            } else {
                ProductOrderDetail productOrderDetail = detailRepository.findByProductCode(productCode, productOrderCode);
                if (productOrderDetail == null) {
                    return -2;
                }
                in = detailRepository.deleteProductInPO(productCode, productOrderCode);
            }

            return in;
        } catch (Exception e) {
            logger.error("Lỗi cập nhật trạng thái sản phẩm", e);
            throw e;
        }
    }

    public void updateProductOrderDetail(String productCode, String productOrderCode, ProductOrderDetailDto dto,Boolean isSend) throws CustomException {
        ProductOrderDetail existDetail = detailRepository.getOneProductOrderDetail(productCode, productOrderCode);
        if (existDetail == null) {
            throw new CustomException("record.notfound");
        }
        if (productCode.equals(dto.getProductCode())) {
            existDetail.setProductName(dto.getProductName());
            existDetail.setBomVersion(dto.getBomVersion());
            existDetail.setQuantity(dto.getOrderQuantity());
            existDetail.setOrderDate(dto.getOrderedTime());
            existDetail.setDeliverDate(dto.getDeliveryTime());
            existDetail.setSupplyType(dto.getSupplyMethod());
            existDetail.setPriority(Integer.valueOf(dto.getPriority()));
            existDetail.setNote(dto.getNote());
            existDetail.setBomStatus(dto.getBomStatus());
            existDetail.setCustomerCode(dto.getCustomerCode());
            existDetail.setProductOrderChild(dto.getProductOrderChild());
            existDetail.setCustomerName(dto.getCustomerName());
            existDetail.setSaleCode(dto.getSaleCode());
            if(isSend){
                existDetail.setStatusPlanning(2);
            }
        } else {
            int checkExistProduct = detailRepository.checkExistProduct(dto.getProductCode(), productOrderCode);

            if (checkExistProduct >= 1) {
                throw new CustomException("product.order.detail.exist", dto.getProductCode());
            } else {
                if(isSend){
                    existDetail.setStatusPlanning(2);
                }
                existDetail.setProductCode(dto.getProductCode());
                existDetail.setProductName(dto.getProductName());
                existDetail.setBomVersion(dto.getBomVersion());
                existDetail.setQuantity(dto.getOrderQuantity());
                existDetail.setOrderDate(dto.getOrderedTime());
                existDetail.setDeliverDate(dto.getDeliveryTime());
                existDetail.setSupplyType(dto.getSupplyMethod());
                existDetail.setPriority(Integer.valueOf(dto.getPriority()));
                existDetail.setNote(dto.getNote());
                existDetail.setBomStatus(dto.getBomStatus());
                existDetail.setCustomerCode(dto.getCustomerCode());
                existDetail.setProductOrderChild(dto.getProductOrderChild());
                existDetail.setCustomerName(dto.getCustomerName());
                existDetail.setSaleCode(dto.getSaleCode());
            }
        }

        logger.info("------------------------------------------------");
        logger.info("existDetail.productName: {}", existDetail.getProductName());

        try {
            detailRepository.save(existDetail);
            PlanningProductionOrder productOrderItem = new PlanningProductionOrder();
            productOrderItem.setProductOrderId(productOrderCode);//mã so nội bộ
            productOrderItem.setExternalPoId(dto.getProductOrderChild());//mã so
            productOrderItem.setProductCode(dto.getProductCode());//mã sp
            productOrderItem.setProductName(dto.getProductName());//tên sp
            productOrderItem.setQuantity(dto.getOrderQuantity());//số lượng
            productOrderItem.setBomVersion(dto.getBomVersion());//bom
            productOrderItem.setStartDate(dto.getOrderedTime());//thời gian bắt đầu
            productOrderItem.setEndDate(dto.getDeliveryTime());//thời gian kết thúc
            productOrderItem.setCustomerCode(dto.getCustomerCode());//mã kh
            productOrderItem.setCustomerName(dto.getCustomerName());//tên kh
            productOrderItem.setNote(dto.getNote());//ghi chú
            productOrderItem.setEmployeeCode(dto.getSaleCode());//mã sale
            productOrderItem.setPriority(dto.getPriority());//mức độ ưu tiên
            //cập nhật sp ở planning
            productOrderService.updatePoPlanning(productOrderItem,existDetail.getProductCode(),isSend);
        } catch (RuntimeException e) {
            logger.error("UpdateProductOrderDetail error", e);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "internal.error");
        }
    }
    public ProductOrderDetailResponse getAllAnalyticsProduct(String productOrderCode, ProductOrderDetailInput input, Integer type) throws IOException, ParseException {

        List<ProductOrderDetailDto> dtoList = new ArrayList<>();
        List<ForecastOrderDetailDTO> dtoFcList = new ArrayList<>();
        List<String> poCodeList = new ArrayList<>();
        ResultCode resultCode = new ResultCode();
        ProductOrderDetailResponse response = new ProductOrderDetailResponse();
        HashMap<String, Double> inStockQuantityHM = new HashMap<>();
        double missingQuantity;
        Pageable pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
        ProductOrderDetailFilter filter = input.getFilter();

        if (type == 2) {
            QForecastOrderDetailEntity qForecastOrderDetail = QForecastOrderDetailEntity.forecastOrderDetailEntity;
            JPAQuery<ForecastOrderDetailEntity> query = new
                JPAQueryFactory(entityManager)
                .selectFrom(qForecastOrderDetail)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            booleanBuilder.and(qForecastOrderDetail.productOrderCode.eq(productOrderCode));
            booleanBuilder.and(qForecastOrderDetail.status.goe(2));
            booleanBuilder.and(qForecastOrderDetail.itemGroupCode.containsIgnoreCase("btp"));
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
            List<ForecastOrderDetailEntity> result = query.fetch();
            long count = query.fetchCount();
            Page<ForecastOrderDetailEntity> productOrderDetailPage = new PageImpl<>(result, pageable, count);

            logger.info("productOrderDetailPage.content: {}", productOrderDetailPage.getContent().size());

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
                logger.info("poCodeList: {}", poCodeList);
                logger.info("inStockQuantityHM: {}", inStockQuantityHM);

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
        } else {
            QProductOrderDetail qProductOrderDetail = QProductOrderDetail.productOrderDetail;
            JPAQuery<ProductOrderDetail> query = new
                JPAQueryFactory(entityManager)
                .selectFrom(qProductOrderDetail)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            booleanBuilder.and(qProductOrderDetail.productOrderCode.productOrderCode.eq(productOrderCode));
            booleanBuilder.and(qProductOrderDetail.status.goe(2));
            booleanBuilder.and(qProductOrderDetail.isActive.eq((byte) 1));
            if (!StringUtils.isEmpty(filter.getProductCode())) {
                booleanBuilder.and(qProductOrderDetail.productCode.containsIgnoreCase(filter.getProductCode()));
            }
            if (!StringUtils.isEmpty(filter.getProductName())) {
                booleanBuilder.and(qProductOrderDetail.productName.containsIgnoreCase(filter.getProductName()));
            }

            if (!StringUtils.isEmpty(filter.getProductOrderChild())) {
                booleanBuilder.and(qProductOrderDetail.productOrderChild.containsIgnoreCase(filter.getProductOrderChild()));
            }
            if (!StringUtils.isEmpty(filter.getBomVersion())) {
                booleanBuilder.and(qProductOrderDetail.bomVersion.containsIgnoreCase(filter.getBomVersion()));
            }
            if (!StringUtils.isEmpty(filter.getOrderQuantity())) {
                booleanBuilder.and(qProductOrderDetail.quantity.eq(Integer.valueOf(filter.getOrderQuantity())));
            }
            if (filter.getOrderedTime() != null) {
                Date orderedTime = filter.getOrderedTime();
                LocalDate localDate = orderedTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (localDate.getDayOfMonth() == 1 && localDate.getMonthValue() == 1){
                    booleanBuilder.and(qProductOrderDetail.orderDate.year().eq(localDate.getYear() - 1)
                        .and(qProductOrderDetail.orderDate.month().eq(12))
                        .and(qProductOrderDetail.orderDate.dayOfMonth().eq(31)));

                }else if(localDate.getDayOfMonth()== 1){
                    LocalDate lastMonth = localDate.minusMonths(1);
                    LocalDate lastDayOfLastMonth = lastMonth.withDayOfMonth(lastMonth.lengthOfMonth());
                    booleanBuilder.and(qProductOrderDetail.orderDate.year().eq(localDate.getYear())
                        .and(qProductOrderDetail.orderDate.month().eq(localDate.getMonthValue() - 1))
                        .and(qProductOrderDetail.orderDate.dayOfMonth().eq(lastDayOfLastMonth.getDayOfMonth())));
                }
                else {
                    booleanBuilder.and(qProductOrderDetail.orderDate.year().eq(localDate.getYear())
                        .and(qProductOrderDetail.orderDate.month().eq(localDate.getMonthValue()))
                        .and(qProductOrderDetail.orderDate.dayOfMonth().eq(localDate.getDayOfMonth()  - 1)));
                }
            }
            if (filter.getDeliveryTime() != null) {
                Date deliveryTime = filter.getDeliveryTime();
                LocalDate localDate = deliveryTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (localDate.getDayOfMonth() == 1 && localDate.getMonthValue() == 1){
                    booleanBuilder.and(qProductOrderDetail.deliverDate.year().eq(localDate.getYear() - 1)
                        .and(qProductOrderDetail.deliverDate.month().eq(12))
                        .and(qProductOrderDetail.deliverDate.dayOfMonth().eq(31)));

                }else if(localDate.getDayOfMonth()== 1){
                    LocalDate lastMonth = localDate.minusMonths(1);
                    LocalDate lastDayOfLastMonth = lastMonth.withDayOfMonth(lastMonth.lengthOfMonth());
                    booleanBuilder.and(qProductOrderDetail.deliverDate.year().eq(localDate.getYear())
                        .and(qProductOrderDetail.deliverDate.month().eq(localDate.getMonthValue() - 1))
                        .and(qProductOrderDetail.deliverDate.dayOfMonth().eq(lastDayOfLastMonth.getDayOfMonth())));
                }
                else {
                    booleanBuilder.and(qProductOrderDetail.deliverDate.year().eq(localDate.getYear())
                        .and(qProductOrderDetail.deliverDate.month().eq(localDate.getMonthValue()))
                        .and(qProductOrderDetail.deliverDate.dayOfMonth().eq(localDate.getDayOfMonth()  - 1)));
                }
            }
            if (!StringUtils.isEmpty(filter.getSupplyMethod())) {
                booleanBuilder.and(qProductOrderDetail.supplyType.containsIgnoreCase(filter.getSupplyMethod()));
            }

            query.where(booleanBuilder).orderBy(qProductOrderDetail.updatedAt.desc());
            System.err.println(query);
            List<ProductOrderDetail> result = query.fetch();
            long count = query.fetchCount();
            Page<ProductOrderDetail> productOrderDetailPage = new PageImpl<>(result, pageable, count);

            logger.info("productOrderDetailPage.content: {}", productOrderDetailPage.getContent().size());

            if (productOrderDetailPage.getContent().isEmpty() || productOrderDetailPage.getContent() == null) {
                throw new CustomException("record.notfound");
            } else if (!productOrderDetailPage.getContent().isEmpty() || !(productOrderDetailPage.getContent() == null)) {
                resultCode.setResponseCode("00");
                resultCode.setMessage("Thành công");
                resultCode.setOk(true);

                productOrderDetailPage.getContent()
                    .forEach(productOrderDetail -> poCodeList.add(productOrderDetail.getProductCode()));

                List<CurrentInventory> currentInventoryList = oitwRepository.getAllInStockQuantityByItemCode(poCodeList);

                for (String poCode : poCodeList) {
                    for (CurrentInventory item : currentInventoryList) {
                        if (poCode.equals(item.getItemCode())) {
                            inStockQuantityHM.put(poCode, item.getCurrentQuantity());
                        }
                    }
                }
                logger.info("poCodeList: {}", poCodeList);
                logger.info("inStockQuantityHM: {}", inStockQuantityHM);

                for (ProductOrderDetail detail : productOrderDetailPage.getContent()) {
                    Double inStockQuantity = inStockQuantityHM.get(detail.getProductCode());
                    if (inStockQuantity == null) inStockQuantity = 0.0;
                    missingQuantity = Double.valueOf(detail.getQuantity()) - inStockQuantity;
                    if (missingQuantity <= 0) {
                        missingQuantity = 0.0;
                    }
                    dtoList.add(mapper.entityToDto(
                        detail,
                        inStockQuantityHM.get(detail.getProductCode()),
                        String.format("%.1f", missingQuantity)));
                }
                response.setResult(resultCode);
                response.setDataCount(productOrderDetailPage.getTotalElements());
                response.setData(dtoList);
            }
        }

        return response;
    }
}
