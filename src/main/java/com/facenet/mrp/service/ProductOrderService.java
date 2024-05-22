package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.ProductOrder;
import com.facenet.mrp.domain.mrp.ProductOrderDetail;
import com.facenet.mrp.domain.mrp.QProductOrder;
import com.facenet.mrp.domain.mrp.ResultMrpJsonEntity;
import com.facenet.mrp.domain.sap.CoittEntity;
import com.facenet.mrp.repository.MrpAnalysisCache;
import com.facenet.mrp.repository.mrp.*;
import com.facenet.mrp.repository.sap.CoittRepository;
import com.facenet.mrp.security.SecurityUtils;
import com.facenet.mrp.service.dto.*;
import com.facenet.mrp.service.dto.mrp.ItemQuantity;
import com.facenet.mrp.service.dto.mrp.MrpDetailDTO;
import com.facenet.mrp.service.dto.mrp.PlanningProductionOrder;
import com.facenet.mrp.service.dto.request.SendAnalysisRequest;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.ProductOrderMapper;
import com.facenet.mrp.service.model.*;
import com.facenet.mrp.service.utils.Constants;
import com.facenet.mrp.service.utils.CsvHandle;
import com.facenet.mrp.service.utils.Utils;
import com.facenet.mrp.service.utils.XlsxExcelHandle;
import com.facenet.mrp.thread.CloneBomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.Column;
import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductOrderService {
    private static final Logger logger = LogManager.getLogger(ProductOrderService.class);
    private final Logger log = LogManager.getLogger(ListSaleService.class);
    private final ProductOrderRepository productOrderRepository;
    @Autowired
    private ProductOrderDetailRepository productOrderDetailRepository;

    private final EntityManager entityManager;
    @Autowired
    private CsvHandle csvHandle;
    @Autowired
    private XlsxExcelHandle xlsxExcelHandle;

    @Autowired
    private CoittRepository coittRepository;

    private final ProductOrderMapper productOrderMapper;

    private final ForecastOrderDetailRepository forecastOrderDetailRepository;
    @Autowired
    private ItemHoldRepository itemHoldRepository;

    @Autowired
    MrpAdvancedAnalysisServiceV3 mrpAdvancedAnalysisServiceV3;

    @Autowired
    planningService planningService;

    @Autowired
    CloneBomService bomService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public ProductOrderService(ProductOrderRepository productOrderRepository, @Qualifier("mrpEntityManager") EntityManager entityManager, ProductOrderMapper productOrderMapper, ForecastOrderDetailRepository forecastOrderDetailRepository) {
        this.productOrderRepository = productOrderRepository;
        this.entityManager = entityManager;
        this.productOrderMapper = productOrderMapper;
        this.forecastOrderDetailRepository = forecastOrderDetailRepository;
    }

    /**
     * lấy tất cả dữ liệu trên table PO có paging
     *
     * @param input chứa các thông tin pageable và filter
     * @return
     */
    public ProductOrderResponse getAllProductOrderWithPaging(ProductOrderInput input) throws IOException, org.apache.el.parser.ParseException {

        ResultCode resultCode = new ResultCode();
        ProductOrderResponse productOrderResponse = new ProductOrderResponse();
        List<ProductOrderDto> productOrderDtoList = new ArrayList<>();

        Pageable pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
//        Page<ProductOrder> productOrderPage = productOrderRepository.getAllProductOrder(pageable,
//            input.getFilter().getPoCode(),
//            input.getFilter().getCustomerCode(),
//            input.getFilter().getCustomerName(),
//            input.getFilter().getPoType(),
//            input.getFilter().getOrderedTime(),
//            input.getFilter().getDeliveryTime(),
//            input.getFilter().getSalesCode(),
//            input.getFilter().getSalesName()
//            );
        ProductOrderFilter filter = input.getFilter();
        QProductOrder qProductOrder = QProductOrder.productOrder;
        JPAQuery<ProductOrder> query = new JPAQueryFactory(entityManager)
            .selectFrom(qProductOrder)
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset());
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qProductOrder.isActive.eq((byte) 1));
        booleanBuilder.andAnyOf(qProductOrder.type.notEqualsIgnoreCase("forecast"), qProductOrder.type.isNull());
        if (!StringUtils.isEmpty(filter.getPoCode())) {
            booleanBuilder.and(qProductOrder.productOrderCode.containsIgnoreCase(filter.getPoCode().trim()));
        }
        if (!StringUtils.isEmpty(filter.getCustomerCode())) {
            booleanBuilder.and(qProductOrder.customerId.containsIgnoreCase(filter.getCustomerCode().trim()));
        }
        if (!StringUtils.isEmpty(filter.getCustomerName())) {
            booleanBuilder.and(qProductOrder.customerName.containsIgnoreCase(filter.getCustomerName().trim()));
        }
        if (!StringUtils.isEmpty(filter.getPoType())) {
            booleanBuilder.and(qProductOrder.productOrderType.containsIgnoreCase(filter.getPoType().trim()));
        }
        if (filter.getOrderedTime() != null) {
            Date orderedTime = filter.getOrderedTime();
            LocalDate localDate = orderedTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Integer month = localDate.getMonthValue();
            Integer year = localDate.getYear();
            Integer day = localDate.getDayOfMonth();
            booleanBuilder.and(qProductOrder.orderDate.year().eq(year)
//                .and(Expressions.numberTemplate(Integer.class, "MONTH({0})", qProductOrder.orderDate).eq(month))
                .and(qProductOrder.orderDate.month().eq(month))
                .and(qProductOrder.orderDate.dayOfMonth().eq(day)));


            // K hiểu
//            if (localDate.getDayOfMonth() == 1 && localDate.getMonthValue() == 1){
//                booleanBuilder.and(qProductOrder.orderDate.year().eq(localDate.getYear() - 1)
//                    .and(qProductOrder.orderDate.month().eq(12))
//                    .and(qProductOrder.orderDate.dayOfMonth().eq(31)));
//
//            }else if(localDate.getDayOfMonth()== 1){
//                LocalDate lastMonth = localDate.minusMonths(1);
//                LocalDate lastDayOfLastMonth = lastMonth.withDayOfMonth(lastMonth.lengthOfMonth());
//                booleanBuilder.and(qProductOrder.orderDate.year().eq(localDate.getYear())
//                    .and(qProductOrder.orderDate.month().eq(localDate.getMonthValue() - 1))
//                    .and(qProductOrder.orderDate.dayOfMonth().eq(lastDayOfLastMonth.getDayOfMonth())));
//            }
//            else {
//                booleanBuilder.and(qProductOrder.orderDate.year().eq(localDate.getYear())
//                    .and(qProductOrder.orderDate.month().eq(localDate.getMonthValue()))
//                    .and(qProductOrder.orderDate.dayOfMonth().eq(localDate.getDayOfMonth()  - 1)));
//            }
        }
        if (filter.getDeliveryTime() != null) {
            Date deliveryTime = filter.getDeliveryTime();
            LocalDate localDate = deliveryTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Integer month = localDate.getMonthValue();
            Integer year = localDate.getYear();
            Integer day = localDate.getDayOfMonth();
            booleanBuilder.and(qProductOrder.orderDate.year().eq(year)
//                .and(Expressions.numberTemplate(Integer.class, "MONTH({0})", qProductOrder.orderDate).eq(month))
                .and(qProductOrder.orderDate.month().eq(month))
                .and(qProductOrder.orderDate.dayOfMonth().eq(day)));

        }
        if (!StringUtils.isEmpty(filter.getSalesCode())) {
            booleanBuilder.and(qProductOrder.partCode.containsIgnoreCase(filter.getSalesCode().trim()));
        }
        if (!StringUtils.isEmpty(filter.getSalesName())) {
            booleanBuilder.and(qProductOrder.partName.containsIgnoreCase(filter.getSalesName().trim()));
        }

        if (!StringUtils.isEmpty(filter.getPriority())) {
            booleanBuilder.and(qProductOrder.priority.eq(Integer.valueOf(filter.getPriority().trim())));
        }
        query.where(booleanBuilder).orderBy(qProductOrder.createdAt.desc());
        TimeZone timeZone = TimeZone.getDefault();
        log.info("Current JVM timezone: " + timeZone.getID());

        List<ProductOrder> result = query.fetch();

//        RowMapper<ProductOrder> rowMapper2 = (rs, rowNum) -> {
//            ProductOrder entity = new ProductOrder();
//            // Ánh xạ các cột từ ResultSet vào các thuộc tính của PlanningWorkOrderEntity
////            entity.setId(rs.getInt("id"));
////            entity.setBomVersion(rs.getString("bom_version"));
////            entity.setProductCode(rs.getString("product_code"));
////            entity.setProductName(rs.getString("product_name"));
//            entity.setOrderDate(rs.getDate("order_date"));
//            // Tiếp tục ánh xạ các thuộc tính khác...
//            return entity;
//        };
//
//        List<ProductOrder> productOrderList = jdbcTemplate.query("SELECT * FROM product_order LIMIT 10", rowMapper2);

        long count = query.fetchCount();
        Page<ProductOrder> productOrderPage = new PageImpl<>(result, pageable, count);
        logger.info("productOrderPage: {}", productOrderPage.getContent().size());

        if (productOrderPage.getContent().isEmpty() || productOrderPage.getContent() == null) {
            throw new CustomException("record.notfound");
//            logger.info("No product order was found");

        } else {

            productOrderPage.getContent().forEach(productOrder -> {
                // Xử lý bị lỗi GMT +7 chưa đc, xử lý chưa triệt để, nếu config đc là tốt nhất
                Date orderDate = productOrder.getOrderDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(orderDate);
                cal.add(Calendar.HOUR, -7); // Dành cho GMT 7
                Date oneHourBack = cal.getTime();
                productOrder.setOrderDate(oneHourBack);
                productOrderDtoList.add(productOrderMapper.entityToDto(productOrder));
            });
            logger.info("productOrderDtoList: {}", productOrderDtoList.size());

            resultCode.setResponseCode("00");
            resultCode.setMessage("Thành công");
            resultCode.setOk(true);

            productOrderResponse.setResult(resultCode);
            productOrderResponse.setDataCount(productOrderPage.getTotalElements());
            productOrderResponse.setData(productOrderDtoList);
        }

        return productOrderResponse;
    }

    public void updateProductOrder(String productOrderCode, ProductOrderDto dto, Boolean isSend) throws CustomException {
        logger.info("Update poCode {}", productOrderCode);
        //lấy dữ liệu productOrder
        ProductOrder existPo = productOrderRepository.findByProductOrderCode(productOrderCode);
        //update dữ liệu cho Po cần cập nhật
        if (existPo == null) {
            throw new CustomException("record.notfound");
        }
        if (!existPo.getProductOrderCode().equals(dto.getPoCode())) {
            ProductOrder checkPoCode = productOrderRepository.findByProductOrderCode(dto.getPoCode());
            if (checkPoCode != null) throw new CustomException("product.order.code.exist", dto.getPoCode());
        }

        existPo.setProductOrderCode(dto.getPoCode());
        existPo.setProductCode(dto.getPoCode());
        existPo.setCustomerId(dto.getCustomerCode());
        existPo.setCustomerName(dto.getCustomerName());
        existPo.setProductOrderType(dto.getPoType());
        existPo.setOrderDate(Date.from(dto.getOrderedTime()));
        existPo.setDeliverDate(Date.from(dto.getDeliveryTime()));
        existPo.setPartCode(dto.getSalesCode());
        existPo.setPartName(dto.getSalesName());
        existPo.setPriority(Integer.valueOf(dto.getPriority()));
        existPo.setNote(dto.getNote());

        if(isSend){
            existPo.setStatusPlanning(2);
        }

        //Save dữ liệu vừa cập nhật
        try {

            //gửi đơn hàng sang planning nếu click send hoặc update nếu có thay đổi
            PlanningProductionOrder productionOrder = new PlanningProductionOrder();
            productionOrder.setProductOrderId(existPo.getProductOrderCode());
            productionOrder.setProductOrderType(dto.getPoType());
            productionOrder.setPartCode(dto.getSalesCode());
            productionOrder.setPartName(dto.getSalesName());
            productionOrder.setOrderDate(Date.from(dto.getOrderedTime()));
            productionOrder.setCompleteDate(Date.from(dto.getDeliveryTime()));
            productionOrder.setNote(dto.getNote());
            String check = updatePoPlanning(productionOrder,"-1",isSend);
            if(check.equals("SUCCESS")){
                productOrderRepository.save(existPo);
            }

        } catch (CustomException e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "internal.error");
        }
    }


    @Transactional(rollbackFor = {Throwable.class})
    public void importProductOrder(InputStream file,Boolean isSend) throws IOException, ParseException {
        HashMap<String, List<ProductOrder>> result = xlsxExcelHandle.readDonHangExcel(file);
        if(result.keySet().isEmpty()) throw new CustomException(HttpStatus.NOT_FOUND, "excel.file.empty");
        saveAll(result,isSend);
        file.close();
    }

    @Transactional(rollbackFor = {Throwable.class})
    public void importProductOrderCsv(InputStream file,Boolean isSend) throws IOException, ParseException {
        HashMap<String, List<ProductOrder>> result = csvHandle.readFileToProductOrder(file);
        saveAll(result,isSend);
        file.close();
    }

    public String saveAll(HashMap<String, List<ProductOrder>> donHangArrayList, Boolean isSend) throws CustomException, ParseException {
        List<MrpDetailDTO> detailDTOS;
        ItemQuantity countChildren;

        ProductOrderDetail orderItem;
        logger.info(" saveAll Order");
        List<List<PlanningProductionOrder>> donHangArraySendPlanning = new ArrayList<>();
        for (List<ProductOrder> orderList : donHangArrayList.values()) {
            if(isSend){
                donHangArraySendPlanning.add(mapExcelToPlanning(orderList));
            }
            String poId = orderList.get(0).getProductOrderCode();
            ProductOrder productionOrder = productOrderRepository.findProductOrderByProductOrderCodeAndIsActive(poId, (byte) 1);
            if (productionOrder != null) {
                logger.info(" poId " + poId + " is exists in database");
                throw new CustomException("product.order.code.exist", poId);
            }

            String mrpPoID = orderList.get(0).getMrpPoId();
            productionOrder = productOrderRepository.findProductOrderByMrpPoIdAndIsActive(mrpPoID, (byte) 1);
            if (productionOrder != null) {
                logger.info(" mrp po id " + mrpPoID + " is exists in database");
                throw new CustomException("product.order.mrp.id.exist", mrpPoID);
            }
            Integer sumQuantity = 0;
            int itemIndex = 0;
            List<ProductOrderDetail> productOrderDetails = new ArrayList<>();
            Set<String> productCodesVersion = new HashSet<>();
            Set<String> productCodes = new HashSet<>();
            for (ProductOrder order : orderList) {
                //lấy danh sách đơn hàng để gửi planning
                countChildren = new ItemQuantity();
                String productCodeVersion = order.getProductCode() + order.getBomVersion();
                if (productCodesVersion.contains(productCodeVersion))
                    throw new CustomException("product.code.duplicate", order.getProductCode());
                productCodesVersion.add(productCodeVersion);
                productCodes.add(order.getProductCode());

                //Count so NVl trong TP
                //query lấy children của sản phẩm TP
                detailDTOS = coittRepository.getAllMrpProductBom(order.getProductCode(), order.getBomVersion());
                logger.info("children của sản phẩm: {}", detailDTOS);

                //Cho vào hàm đệ quy để tìm các NVl/BTp con trong TP
                countChildren.setQuantity(0.0);
                getChildrenCountOfProduct(countChildren, detailDTOS);

                System.err.println("countChildren dff: " + countChildren.getQuantity());

                sumQuantity += order.getQuantity();
                itemIndex++;
                orderItem = new ProductOrderDetail();
                orderItem.setStatus(Constants.ProductOrder.STATUS_NEW);
                orderItem.setProductCode(order.getProductCode());
                orderItem.setProductName(order.getProductName());
                orderItem.setQuantity(order.getQuantity());
                orderItem.setBomVersion(order.getBomVersion());
                orderItem.setOrderDate(order.getStartDate());
                orderItem.setDeliverDate(order.getEndDate());
                orderItem.setPriority(order.getPriorityProduct());
                if(isSend){//nếu gửi planning thì update status
                    orderItem.setStatusPlanning(2);
                    orderList.get(0).setStatusPlanning(2);
                }
                orderItem.setSupplyType(order.getSupplyType());
                orderItem.setItemIndex(itemIndex);
                orderItem.setProductOrderChild(order.getProductCodeChild());
                orderItem.setCustomerCode(order.getCustomerId());
                orderItem.setCustomerName(order.getCustomerName());
                orderItem.setSaleCode(order.getSaleCode());
                orderItem.setMaterialChildrenCount(countChildren.getQuantity().intValue());

                productOrderDetails.add(orderItem);
//                productOrderItemRepository.save(orderItem);
            }
            validateBomVersion(productOrderDetails, productCodes);
            logger.info("sumQuantity = {}", sumQuantity);
            ProductOrder productOrder = orderList.get(0);
            productOrderDetails.forEach(productOrderDetail -> productOrderDetail.setProductOrderCode(productOrder));
            productOrder.setQuantity(sumQuantity);
            productOrderRepository.save(orderList.get(0));
            productOrderDetailRepository.saveAll(productOrderDetails);
        }
        if(isSend){
            if(donHangArraySendPlanning.size() > 0){
                System.out.println("----------------------------danh sách đơn hàng gửi planning khi import excel: "+donHangArraySendPlanning.get(0));
                syncToPlanning(donHangArraySendPlanning);
            }
        }
        return "SUCCESS";
    }

    public String deletePoPlanning(String orderCode, String productCode, Boolean type){
        return planningService.callApiDeletePo(orderCode,productCode,type);
    }
    private void validateBomVersion(List<ProductOrderDetail> productOrderDetails, Set<String> productCodes) {
        List<CoittEntity> coittEntities = coittRepository.getCoittEntitiesByuProNoIn(productCodes);
        MultiValuedMap<String, String> itemBomMap = new ArrayListValuedHashMap<>();
        for (CoittEntity entity : coittEntities) {
            itemBomMap.put(entity.getuProNo(), entity.getuVersions());
        }
        StringBuilder invalidBomMessage = new StringBuilder();
        for (ProductOrderDetail product : productOrderDetails) {
            if (!itemBomMap.containsMapping(product.getProductCode(), product.getBomVersion())) {
                invalidBomMessage.append(product.getProductCode()).append("-").append(product.getBomVersion()).append(", ");
            }
        }
        if (invalidBomMessage.length() > 0) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "item.code.not.bom.version", invalidBomMessage.toString());
        }
    }

    @Transactional
    public void createNewProductOrder(List<ProductOrder> productOrders, Boolean isSend) throws ParseException {
        List<List<PlanningProductionOrder>> donHangArrayList = new ArrayList<>();
        ItemQuantity countChildren;
        List<MrpDetailDTO> detailDTOS;

        logger.info("start create new product order");
        for (ProductOrder productOrder : productOrders) {
            //add BTP TP vào list
            donHangArrayList.add(mapToPlanning(productOrder));
            Set<String> productCodes = new HashSet<>();
            if (productOrder.getProductOrderDetails() == null || productOrder.getProductOrderDetails().isEmpty()) {
                throw new CustomException("order.detail.is.must.not.empty");
            }
            String po_id = "RAL-SO-" + productOrder.getProductOrderCode();
            logger.info("po_id = {} ", po_id);
            ProductOrder tmpOrder = productOrderRepository.findProductOrderByProductOrderCodeAndIsActive(po_id, (byte) 1);
            if (tmpOrder != null) {
                logger.info(" poId " + po_id + " is exists in database");
                throw new CustomException("product.order.code.exist", po_id);
            }
            String mrpPoId = po_id;
            tmpOrder = productOrderRepository.findProductOrderByMrpPoIdAndIsActive(mrpPoId, (byte) 1);
            if (tmpOrder != null) {
                logger.info(" mrp po id " + mrpPoId + " is exists in database");
                throw new CustomException("product.order.mrp.id.exist", mrpPoId);
            }
            if(isSend){
                productOrder.setStatusPlanning(2);
                productOrder.getProductOrderDetails().forEach(productOrderDetail -> {
                    productOrderDetail.setProductOrderCode(productOrder);
                    productOrderDetail.setStatusPlanning(2);
                    productOrderDetail.setStatus(Constants.ProductOrder.STATUS_NEW);
                });
            }else {
                productOrder.getProductOrderDetails().forEach(productOrderDetail -> {
                    productOrderDetail.setProductOrderCode(productOrder);
                    productOrderDetail.setStatus(Constants.ProductOrder.STATUS_NEW);
                });
            }
            productOrder.setMrpPoId(mrpPoId);
            productOrder.setProductOrderCode(po_id);
            productOrder.setStatus(Constants.ProductOrder.STATUS_NEW);
            productOrder.setType("Đơn hàng");
            // Check trùng mã sản phẩm
            // Lay tung san pham de tim so luong NVl ben trong theo bomversion
            for (ProductOrderDetail product : productOrder.getProductOrderDetails()) {
                countChildren = new ItemQuantity();
                countChildren.setQuantity(0.0);
                String productCodeVersion = product.getProductCode() + product.getBomVersion();
                if (productCodes.contains(productCodeVersion)) {
                    throw new CustomException("product.code.duplicate", product.getProductCode());
                }
                productCodes.add(productCodeVersion);

                //query lấy children của sản phẩm TP
                detailDTOS = coittRepository.getAllMrpProductBom(product.getProductCode(), product.getBomVersion());
                log.info("children của sản phẩm: {}", detailDTOS);

                //Cho vào hàm đệ quy để tìm các NVl/BTp con trong TP
                getChildrenCountOfProduct(countChildren, detailDTOS);
                product.setMaterialChildrenCount(countChildren.getQuantity().intValue());
            }
        }
        if(isSend){
            if(donHangArrayList.size() > 0){
                System.out.println("----------------------------danh sách đơn hàng 1: "+donHangArrayList.get(0));
                syncToPlanning(donHangArrayList);
            }
        }
        productOrderRepository.saveAll(productOrders);
    }

    //hàm gọi api planning và đồng bộ
    private void syncToPlanning(List<List<PlanningProductionOrder>> donHangArrayList){
        String check = planningService.callApiPlanning(donHangArrayList,false);
        System.out.println("-----------------"+check);

        if(!check.equals("SUCCESS")){
            throw new CustomException("Đồng bộ planning thất bại: "+check);
        }
    }
    //hàm gọi update đơn hàng hoặc gửi đơn hàng từ màn ql đơn hàng sang planning
    public String updatePoPlanning(PlanningProductionOrder donHangArrayList, String productCode, Boolean isSend){
        String check = planningService.callApiPlanningUpdatePo(donHangArrayList, productCode,isSend);
        System.out.println("---------------"+check);
        if(!check.equals("SUCCESS")){
            throw new CustomException(check);
        }
        return check;
    }

    public ResponseEntity createWorkOrder(List<CreateWoFromMrp> createWoFromMrps){
        return new ResponseEntity<>(planningService.callApiCreateWorkOrder(createWoFromMrps), HttpStatus.OK);
    }

    public ResponseEntity sendPlanningBeforeCreateWo(String soCode) throws ParseException {
        ProductOrder productOrder = productOrderRepository.findAllByProductOrderCode(soCode);
        if(productOrder == null){
            return new ResponseEntity("Mã đơn hàng "+ soCode + "không tồn tại trên hệ thống",HttpStatus.OK);
        }
        List<List<PlanningProductionOrder>> data = new ArrayList<>();
        List<PlanningProductionOrder> planningProductionOrders = mapToPlanning(productOrder);
        data.add(planningProductionOrders);
        String check = planningService.callApiPlanning(data,true);
        if(check != "SUCCESS"){
            throw new CustomException(check);
        }
        productOrder.setStatusPlanning(2);
        for (ProductOrderDetail productOrderDetail: productOrder.getProductOrderDetails()){
            productOrderDetail.setStatusPlanning(2);
        }
        productOrderRepository.save(productOrder);
        return new ResponseEntity("SUCCESS",HttpStatus.OK);
    }

    private List<PlanningProductionOrder> mapToPlanning(ProductOrder productOrder) throws ParseException {
        List<PlanningProductionOrder> productionOrderList = new ArrayList<>();
        for (ProductOrderDetail productOrderDetails: productOrder.getProductOrderDetails()){
            PlanningProductionOrder donHang = new PlanningProductionOrder();
            donHang.setId(UUID.randomUUID());
            donHang.setProductOrderId(productOrder.getProductOrderCode());
            donHang.setProductOrderType(productOrder.getProductOrderType());
            donHang.setPartCode(productOrder.getPartCode());
            donHang.setPartName(productOrder.getPartName());
            donHang.setPriority(String.valueOf(productOrder.getPriority()));
            donHang.setBranchCode(productOrder.getPartCode());

            donHang.setExternalPoId(productOrderDetails.getProductOrderChild());
            donHang.setCustomerCode(productOrderDetails.getCustomerCode());
            donHang.setCustomerName(productOrderDetails.getCustomerName());
            donHang.setBomVersion(productOrderDetails.getBomVersion());
            donHang.setProductCode(productOrderDetails.getProductCode());
            donHang.setProductName(productOrderDetails.getProductName());
            donHang.setQuantity(productOrderDetails.getQuantity());
            donHang.setProductType(1);
            donHang.setState("CREATED");
            donHang.setStatus("active");
            donHang.setEmployeeCode(productOrderDetails.getSaleCode());//nv sale
            donHang.setItemPriority(productOrderDetails.getPriority());
            donHang.setClassify(1);
//            donHang.setCreatedDate(new Date());
            donHang.setOriginal("MRP");
            if (productOrder.getOrderDate() != null) {
                donHang.setOrderDate(convert(productOrder.getOrderDate().toString()));
            } else {
                donHang.setOrderDate(null);
            }

            if (productOrder.getDeliverDate()  != null) {
                donHang.setCompleteDate(convert(productOrder.getDeliverDate().toString()));
            }else {
                donHang.setCompleteDate(null);
            }

            if (productOrder.getStartDate()  != null) {
                donHang.setStartDate(convert(productOrder.getStartDate().toString()));
            }else {
                donHang.setStartDate(null);
            }

            if (productOrder.getEndDate()  != null) {
                //endTime
                donHang.setEndDate( convert(productOrder.getEndDate().toString()));
            }else {
                donHang.setEndDate(null);
            }
            productionOrderList.addAll(callBomForPo(productOrder,productOrderDetails));
            productionOrderList.add(donHang);
            System.out.println("----------------------------danh sách đơn hàng 2: "+productionOrderList.toString());
        }
        return productionOrderList;
    }


    private List<PlanningProductionOrder> mapExcelToPlanning(List<ProductOrder> productOrders) throws ParseException {
        List<PlanningProductionOrder> productionOrderList = new ArrayList<>();
        for (ProductOrder productOrder: productOrders){
            PlanningProductionOrder donHang = new PlanningProductionOrder();
            donHang.setId(UUID.randomUUID());
            donHang.setProductOrderId(productOrder.getProductOrderCode());
            donHang.setProductOrderType(productOrder.getProductOrderType());
            donHang.setPartCode(productOrder.getPartCode());
            donHang.setPartName(productOrder.getPartName());
            donHang.setPriority(String.valueOf(productOrder.getPriority()));
            donHang.setBranchCode(productOrder.getPartCode());

            donHang.setExternalPoId(productOrder.getProductCodeChild());
            donHang.setCustomerCode(productOrder.getCustomerId());
            donHang.setCustomerName(productOrder.getCustomerName());
            donHang.setBomVersion(productOrder.getBomVersion());
            donHang.setProductCode(productOrder.getProductCode());
            donHang.setProductName(productOrder.getProductName());
            donHang.setQuantity(productOrder.getQuantity());
            donHang.setProductType(1);
            donHang.setState("CREATED");
            donHang.setStatus("active");
            donHang.setEmployeeCode(productOrder.getSaleCode());//nv sale
            donHang.setItemPriority(productOrder.getPriorityProduct());
            donHang.setClassify(1);
            donHang.setOriginal("MRP");
            if (productOrder.getOrderDate() != null) {
                donHang.setOrderDate(convert(productOrder.getOrderDate().toString()));
            } else {
                donHang.setOrderDate(null);
            }

            if (productOrder.getDeliverDate()  != null) {
                donHang.setCompleteDate(convert(productOrder.getDeliverDate().toString()));
            }else {
                donHang.setCompleteDate(null);
            }

            if (productOrder.getStartDate()  != null) {
                donHang.setStartDate(convert(productOrder.getStartDate().toString()));
            }else {
                donHang.setStartDate(null);
            }

            if (productOrder.getEndDate()  != null) {
                //endTime
                donHang.setEndDate( convert(productOrder.getEndDate().toString()));
            }else {
                donHang.setEndDate(null);
            }
            ProductOrderDetail productOrderDetail = new ProductOrderDetail();
            productOrderDetail.setProductCode(productOrder.getProductCode());
            productOrderDetail.setBomVersion(productOrder.getBomVersion());
            productOrderDetail.setQuantity(productOrder.getQuantity());
            productOrderDetail.setCustomerCode(productOrder.getCustomerId());
            productOrderDetail.setCustomerName(productOrder.getCustomerName());
            productOrderDetail.setProductOrderChild(productOrder.getProductCodeChild());
            productOrderDetail.setSaleCode(productOrder.getSaleCode());
            productOrderDetail.setPriority(productOrder.getPriority());
            productionOrderList.addAll(callBomForPo(productOrder,productOrderDetail));
            productionOrderList.add(donHang);
            System.out.println("----------------------------danh sách đơn hàng 2: "+productionOrderList.toString());
        }
        return productionOrderList;
    }

    public Date convert(String input) {
        String dateFormat = "EEE MMM dd HH:mm:ss zzz yyyy";

        try {
            SimpleDateFormat doiFormat = new SimpleDateFormat(dateFormat);
            Date date = doiFormat.parse(input);

            SimpleDateFormat desiredFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = desiredFormat.format(date);
            Date output = new SimpleDateFormat("yyyy-mm-dd").parse(formattedDate);
            return output;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<WorkOrder> getListWO(String po){
        return planningService.callApiFindPlanningWorkOrder(po);
    }

    //lấy danh sách btp của product code và đồng bộ cùng thành phẩm sang planning
    private List<PlanningProductionOrder> callBomForPo(ProductOrder productOrder, ProductOrderDetail productOrderDetails) throws ParseException {
        List<MrpDetailDTO> mrpDetailDTOS = getListBtp(productOrderDetails.getProductCode(),productOrderDetails.getBomVersion());
        System.out.println("----------------------------danh sách bom type btp: "+mrpDetailDTOS.size());

        List<PlanningProductionOrder> productionOrderList = new ArrayList<>();
        for(MrpDetailDTO mrpDetailDTO: mrpDetailDTOS){
            PlanningProductionOrder donHang = new PlanningProductionOrder();
            donHang.setId(UUID.randomUUID());
            donHang.setProductOrderId(productOrder.getProductOrderCode());
            donHang.setProductOrderType(productOrder.getProductOrderType());
            donHang.setPartCode(productOrder.getPartCode());
            donHang.setPartName(productOrder.getPartName());
            donHang.setPriority(String.valueOf(productOrder.getPriority()));
            donHang.setBranchCode(productOrder.getPartCode());

            donHang.setCustomerCode(productOrderDetails.getCustomerCode());
            donHang.setExternalPoId(productOrderDetails.getProductOrderChild());
            donHang.setCustomerName(productOrderDetails.getCustomerName());
            donHang.setBomVersion(mrpDetailDTO.getBomVersion());
            donHang.setProductCode(mrpDetailDTO.getItemCode());
            donHang.setProductName(mrpDetailDTO.getItemName());

            if(mrpDetailDTO.getQuota() != null && productOrderDetails.getQuantity() != null){
                donHang.setQuantity((int) (productOrderDetails.getQuantity()*mrpDetailDTO.getQuota()));
            }
            donHang.setProductType(0);
            donHang.setState("CREATED");
            donHang.setStatus("active");
            donHang.setEmployeeCode(productOrderDetails.getSaleCode());//nv sale
            donHang.setItemPriority(productOrderDetails.getPriority());
            donHang.setClassify(1);
//            donHang.setCreatedDate(new Date());
            donHang.setOriginal("MRP");
            if (productOrder.getOrderDate() != null) {
                donHang.setOrderDate(productOrder.getOrderDate());
            } else {
                donHang.setOrderDate(new Date());
            }

            if (productOrder.getDeliverDate()  != null) {
                donHang.setCompleteDate(productOrder.getDeliverDate());
            }else {
                donHang.setCompleteDate(new Date());
            }

            if (productOrder.getStartDate()  != null) {
                //startTime
                donHang.setStartDate(productOrder.getStartDate());
            }else {
                donHang.setStartDate(new Date());
            }

            if (productOrder.getEndDate()  != null) {
                //endTime
                donHang.setEndDate(productOrder.getEndDate());
            }else {
                donHang.setEndDate(new Date());
            }
            productionOrderList.add(donHang);
        }
        System.out.println("----------------------------danh sách đơn hàng 3: "+productionOrderList.size());
        return productionOrderList;
    }

    //hàm đệ quy lấy tất cả btp
    static List<MrpDetailDTO> itemList = new ArrayList<>();
    private  List<MrpDetailDTO> getListBtp(String code, String version){
        //TODO sua lai
        List<MrpDetailDTO> bomItems = coittRepository.getAllMrpProductBomList(code,version);
        List<String> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(bomItems)) {
            for (MrpDetailDTO bomItem : bomItems) {
                if (bomItem.getGroupItemInt()== Constants.TP || bomItem.getGroupItemInt() == Constants.BTP) {
                    if(!list.contains(bomItem.getItemCode())){
                        itemList.add(new MrpDetailDTO(bomItem));
                        list.add(bomItem.getItemCode());
                    }
                    if(bomItem.getBomVersion() != null){
                        getListBtp(bomItem.getItemCode(),bomItem.getBomVersion());
                    }
                }
            }
        }

        System.out.println("-------------------lấy bom:"+itemList);
        return itemList;
    }


    public void getChildrenCountOfProduct(ItemQuantity countChildren, List<MrpDetailDTO> detailDTOS) {
        List<MrpDetailDTO> childrenDetail = new ArrayList<>();
        System.err.println("countChildren truoc khi for: " + countChildren.getQuantity().intValue());

        for (MrpDetailDTO mrpDetailDTO : detailDTOS) {
            // nếu là BTP thì query tiếp con của nó
            //TODO: need to change to BTP.equals
            if (mrpDetailDTO.getGroupItemInt() != null && mrpDetailDTO.getGroupItemInt() == Constants.BTP) {
                //query lấy children của BTP
                childrenDetail = coittRepository.getAllMrpProductBom(mrpDetailDTO.getItemCode(), mrpDetailDTO.getBomVersion());
                log.info("children của sản phẩm: {}", detailDTOS);

                getChildrenCountOfProduct(countChildren, childrenDetail);

            } else {
                countChildren.setQuantity(countChildren.getQuantity() + 1);
            }
        }


        System.err.println("countChildren sau khi for: " + countChildren.getQuantity());

    }

    public void updateStatusPO(String productOrderCode, Integer status) {
        if (status < 1 || status > 6 || StringUtils.isEmpty(productOrderCode)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.param");
        }
        ProductOrder productOrder = productOrderRepository.findByProductOrderCode(productOrderCode);
        if (productOrder == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        }
        productOrderRepository.updateStatusPO(status, productOrderCode, SecurityUtils.getCurrentUserLogin().orElse(""));
        if (status == Constants.ProductOrder.CLOSED) {
            List<ProductOrderDetail> productOrderDetails = productOrderDetailRepository.getAllBySO(productOrderCode);
            if (productOrderDetails != null) {
                for (ProductOrderDetail item : productOrderDetails) {
                    item.setStatus(Constants.ProductOrder.CLOSED);
                }
                productOrderDetailRepository.saveAll(productOrderDetails);
            }
            itemHoldRepository.unHoldItemsOf(productOrderCode);
        }
    }

    //gửi phân tích cả đơn hàng
    @Transactional
    public String sendAnalysisPO(String productOrderCode) {
        String mes;
        ProductOrder productOrder = productOrderRepository.findByProductOrderCode(productOrderCode);
        if (productOrder == null) {
            mes = "không có đơn hàng này!";
            return mes;
        }
        List<ProductOrderDetail> list = productOrderDetailRepository.findAllByProductOrderCode(productOrderCode);
        List<String> listProductHadBom = new ArrayList<>();
        for (ProductOrderDetail item : list
        ) {
            if (item.getBomVersion() == null || item.getBomVersion().equals("")) {
                continue;
            } else {
                listProductHadBom.add(item.getProductCode());
            }
        }
        if (listProductHadBom.size() > 0) {
            Integer in = productOrderRepository.sendAnalysisPO(productOrderCode, SecurityUtils.getCurrentUserLogin().orElse(""));
            Integer detail = productOrderRepository.sendAnalysisAllProductPoNotNullBomVersion(productOrder.getMrpPoId(), SecurityUtils.getCurrentUserLogin().orElse(""), listProductHadBom);
            mes = "ok";
            return mes;
        } else {
            mes = "tất cả sản phẩm của đơn hàng không có bom!";
            return mes;
        }
    }

    //gửi phân tích 1 sản phẩm trong đơn hàng
    @Transactional
    public Integer sendAnalysisProductInPO(String productOrderCode, String productCode) {
        ProductOrder productOrder = productOrderRepository.findByProductOrderCode(productOrderCode);
        if (productOrder == null) {
            return -2;
        }
        Integer in = productOrderRepository.sendAnalysisPO(productOrderCode, SecurityUtils.getCurrentUserLogin().get());
        Integer detail = productOrderRepository.sendAnalysisProductInPO(productOrder.getMrpPoId(), productCode, SecurityUtils.getCurrentUserLogin().get());
        return in + detail;
    }

    @Transactional
    public Integer sendAnalysisListProductInPO(List<SendAnalysisRequest> request) {
        for (SendAnalysisRequest analysisRequest:request){
            ProductOrder productOrder = productOrderRepository.findByProductOrderCode(analysisRequest.getPoCode());
            if (productOrder == null) {
                return -2;
            }
            Integer in = productOrderRepository.sendAnalysisPO(analysisRequest.getPoCode(), SecurityUtils.getCurrentUserLogin().get());
            Integer detail = productOrderRepository.sendAnalysisProductInPO(productOrder.getMrpPoId(), analysisRequest.getProductCode(), SecurityUtils.getCurrentUserLogin().get());
        }
        return 2;
    }

    @Transactional
    public Integer deletePO(String productOrderCode) {

        Integer in = 0;

        if (StringUtils.isEmpty(productOrderCode)) {
            return -1;
        }
        ProductOrder productOrder = productOrderRepository.findByProductOrderCode(productOrderCode);
        if (productOrder == null) {
            return -2;
        }

        if (productOrder.getProductOrderCode().contains("RAL-FC")) {

            in = productOrderRepository.deleteFC(productOrderCode, SecurityUtils.getCurrentUserLogin().orElse(""));
            int deletedItems = forecastOrderDetailRepository.deleteFCDetail(productOrderCode, SecurityUtils.getCurrentUserLogin().orElse(""));
            logger.info("DeletedFC {} itemsFC of {}", deletedItems, productOrderCode);

        } else {
            in = productOrderRepository.deletePO(productOrderCode, productOrder.getMrpPoId() + "_bk");
            logger.info("Deleted {}", productOrderCode);
            int deletedItems = productOrderDetailRepository.deleteAllProductOfPO(productOrder.getMrpPoId());
            logger.info("Deleted {} items of {}", deletedItems, productOrderCode);
        }
        return in;
    }


}
