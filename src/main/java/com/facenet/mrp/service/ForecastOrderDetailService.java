package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.ForecastOrderDetailEntity;
import com.facenet.mrp.domain.mrp.ProductOrder;
import com.facenet.mrp.repository.mrp.ForecastOrderDetailRepository;
import com.facenet.mrp.repository.mrp.ProductOrderRepository;
import com.facenet.mrp.repository.sap.CoittRepository;
import com.facenet.mrp.repository.sap.OitwRepository;
import com.facenet.mrp.security.SecurityUtils;
import com.facenet.mrp.service.dto.mrp.*;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ForecastOrderDetailService {

    private static final Logger logger = LoggerFactory.getLogger(ForecastOrderDetailService.class);
    private final OitwRepository oitwRepository;

    private final ProductOrderService productOrderService;

    @Autowired
    private ProductOrderRepository forecastRepository;

    private final CoittRepository coittRepository;

    @Autowired
    private ForecastOrderDetailRepository forecastOrderDetailRepository;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat simpleMonthFormat = new SimpleDateFormat("yyyy-MM");
    private SimpleDateFormat simpleYearFormat = new SimpleDateFormat("yyyy");

    public ForecastOrderDetailService( OitwRepository oitwRepository, ProductOrderService productOrderService, CoittRepository coittRepository) {
        this.oitwRepository = oitwRepository;
        this.productOrderService = productOrderService;
        this.coittRepository = coittRepository;
    }

    public ForecastMaterialDTO creatResultOfForecast(ForecastMaterialDTO planDto) throws ParseException {
        List<String> itemCodeList = new ArrayList<>();
        List<String> whsCodeList;
        List<String> landmarkList = new ArrayList<>();
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();

        //Lấy Danh sách kho
//        logger.info("getForecastWhs:{}",planDto.getForecastWhs());
        whsCodeList = List.of(planDto.getForecastWhs());

        //lấy danh sách itemCode
        for (MaterialPlanDTO item: planDto.getListData()) {
            itemCodeList.add(item.getItemCode());
        }

        // Lấy tồn kho cho list hàng hóa theo danh sách kho
        List<CurrentInventory> currentInventories = oitwRepository.getAllCurrentInventoryByWhs(itemCodeList, whsCodeList);
        if (!currentInventories.isEmpty() && currentInventories != null) {
            for (MaterialPlanDTO dto :planDto.getListData()) {
                for (CurrentInventory quantity : currentInventories){
                    if (quantity.getItemCode().equals(dto.getItemCode())){
                        dto.setCurrentInventory(quantity.getCurrentQuantity());
                        break;
                    }
                }
            }
        }

        // Đưa ra calendar date cụ thể cho từng hàng hóa theo chế độ dự báo
        calStart.setTime(planDto.getStartDate());
        calEnd.setTime(planDto.getEndDate());
//        logger.info("getForecastMode: {}", planDto.getForecastMode());
//        logger.info("boolean: {}", planDto.getForecastMode().equalsIgnoreCase("thang"));

        switch (planDto.getForecastMode()) {
            case "0":
                if (calEnd.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                    calEnd.add(Calendar.DATE, Calendar.DAY_OF_WEEK - calEnd.get(Calendar.DAY_OF_WEEK) + 1);
                }
                while (calStart.before(calEnd) || calStart.equals(calEnd)) {

                    if (calStart.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        landmarkList.add(simpleDateFormat.format(calStart.getTime()));
//                    logger.info("simpleDateFormat: {}", simpleDateFormat.format(calStart.getTime()));
                    }
                    calStart.add(Calendar.DATE, 1);
                }
                break;
            case "1":
                calStart.add(Calendar.DAY_OF_MONTH, calStart.getActualMaximum(Calendar.DATE) - calStart.get(Calendar.DAY_OF_MONTH));
                calEnd.add(Calendar.DAY_OF_MONTH, calEnd.getActualMaximum(Calendar.DATE) - calEnd.get(Calendar.DAY_OF_MONTH));

                while (calStart.before(calEnd) || calStart.equals(calEnd)) {
                    if (calStart.get(Calendar.DATE) == calStart.getActualMaximum(Calendar.DATE)) {
                        landmarkList.add(simpleDateFormat.format(calStart.getTime()));
                    }
                    calStart.add(Calendar.DATE, 1);
                }
                break;
            case "2":
                String checkDate = "";
                int month = calEnd.get(Calendar.MONTH);
                int quarter = (month / 3) + 1;
                if (quarter == 1) {
                    checkDate = simpleYearFormat.format(calEnd.getTime()) + "-03-31";
                } else if (quarter == 2) {
                    checkDate = simpleYearFormat.format(calEnd.getTime()) + "-06-30";
                } else if (quarter == 3) {
                    checkDate = simpleYearFormat.format(calEnd.getTime()) + "-09-30";
                } else if (quarter == 4) {
                    checkDate = simpleYearFormat.format(calEnd.getTime()) + "-12-31";
                }
                calEnd.setTime(simpleDateFormat.parse(checkDate));

                while (calStart.before(calEnd) || calStart.equals(calEnd)) {
                    month = calStart.get(Calendar.MONTH);
                    quarter = (month / 3) + 1;
                    if (quarter == 1) {
                        checkDate = simpleYearFormat.format(calStart.getTime()) + "-03-31";
                        landmarkList.add(checkDate);
                    } else if (quarter == 2) {
                        checkDate = simpleYearFormat.format(calStart.getTime()) + "-06-30";
                        landmarkList.add(checkDate);
                    } else if (quarter == 3) {
                        checkDate = simpleYearFormat.format(calStart.getTime()) + "-09-30";
                        landmarkList.add(checkDate);
                    } else if (quarter == 4) {
                        checkDate = simpleYearFormat.format(calStart.getTime()) + "-12-31";
                        landmarkList.add(checkDate);
                    }
                    calStart.setTime(simpleDateFormat.parse(checkDate));
                    calStart.add(Calendar.MONTH, 3);
                }

                break;
            case "3":
                calStart.add(Calendar.DAY_OF_YEAR, calStart.getActualMaximum(Calendar.DAY_OF_YEAR) - calStart.get(Calendar.DAY_OF_YEAR));
                calEnd.add(Calendar.DAY_OF_YEAR, calEnd.getActualMaximum(Calendar.DAY_OF_YEAR) - calEnd.get(Calendar.DAY_OF_YEAR));
                while (calStart.before(calEnd) || calStart.equals(calEnd)) {
                    landmarkList.add(simpleDateFormat.format(calStart.getTime()));
                    calStart.add(Calendar.YEAR, 1);
                }
                break;
        }

        // Ghép calendar date tìm được vào các landmark theo vòng lặp for(i)
        //Mặc định thì trạng thái tạo mới cho hàng hóa của đơn hàng FC lần đầu là "Mới tạo"
        if (landmarkList.size() > planDto.getListData().get(0).getListLandMark().size() || landmarkList.size() < planDto.getListData().get(0).getListLandMark().size()){
            if (planDto.getForecastMode().equals("0")){
                throw new CustomException(HttpStatus.BAD_REQUEST, "landmark.out.of.range", String.valueOf(landmarkList.size()), "Tuần");
            } else if (planDto.getForecastMode().equals("1")) {
                throw new CustomException(HttpStatus.BAD_REQUEST,"landmark.out.of.range", String.valueOf(landmarkList.size()), "Tháng");
            } else if (planDto.getForecastMode().equals("2")) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "landmark.out.of.range", String.valueOf(landmarkList.size()), "Quý");
            } else if (planDto.getForecastMode().equals("3")) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "landmark.out.of.range", String.valueOf(landmarkList.size()), "Năm");
            }
        }else {
            for(MaterialPlanDTO material : planDto.getListData()){
//            logger.info("landmarkList: {}", landmarkList.size());
//            logger.info("material: {}", material.getListLandMark().size());
                for (int i = 0; i < material.getListLandMark().size(); i++) {
                    material.getListLandMark().get(i).setLandMarkDay(landmarkList.get(i));
                    material.setStatus(1);
                }
            }
        }

        //Set status của cả đơn hàng forecastOrder
        planDto.setForecastOrderStatus(1);

        return planDto;
    }

    @Transactional
    public void sendForecastToAnalysis(ForecastMaterialDTO forecastMaterialDTO) throws ParseException, JsonProcessingException {
        //Lưu kế hoạch vào db
        //Đồng thời chuyển status của đơn hàng và sản phẩm trong đơn hàng
        // Tránh lưu lặp lại
        ProductOrder forecastOrder = forecastRepository.findProductOrderByProductOrderCodeAndIsActive(forecastMaterialDTO.getForecastCode(), (byte) 1);
        if (forecastOrder == null) {
            forecastOrder = new ProductOrder();
        }

        ObjectMapper objectMapper = new ObjectMapper();
//        forecastOrder.setId(forecastMaterialDTO.getId());
        forecastOrder.setProductOrderCode(forecastMaterialDTO.getForecastCode());
        forecastOrder.setForecastName(forecastMaterialDTO.getForecastName());
        forecastOrder.setForecastMode(forecastMaterialDTO.getForecastMode());
        forecastOrder.setWarehouseList(forecastMaterialDTO.getForecastWhs());
        forecastOrder.setPriority(forecastMaterialDTO.getLevelPriority());
        forecastOrder.setOrderDate(forecastMaterialDTO.getStartDate());
        forecastOrder.setDeliverDate(forecastMaterialDTO.getEndDate());
        forecastOrder.setCreatedBy(forecastMaterialDTO.getCreatedBy());
        forecastOrder.setCreatedAt(forecastMaterialDTO.getCreatedAt().toInstant());
        forecastOrder.setForecastSource(forecastMaterialDTO.getForecastSource());
        forecastOrder.setForecastLandMark(objectMapper.writeValueAsString(forecastMaterialDTO.getLandMark()));
        forecastOrder.setIsActive((byte) 1);
        forecastOrder.setUpdatedAt(new Date().toInstant());
        forecastOrder.setUpdatedBy(SecurityUtils.getCurrentUserLogin().orElse(""));
        forecastOrder.setProductOrderType("Forecast");
//        forecastOrder.setCustomerId("RAL");
        forecastOrder.setCustomerName("Rang Dong");
        forecastOrder.setType("Forecast");
        forecastOrder.setStatus(2);
        forecastRepository.save(forecastOrder);
        //Update ngày chuẩn cho forecastOrder khi update
        forecastRepository.sendAnalysisPO(forecastOrder.getProductOrderCode(), SecurityUtils.getCurrentUserLogin().orElse(""));
        List<ForecastOrderDetailEntity> list = new ArrayList<>();
        for (MaterialPlanDTO item:forecastMaterialDTO.getListData()) {
            ForecastOrderDetailEntity forecastOrderDetail = new ForecastOrderDetailEntity();
            forecastOrderDetail.setForecastOrderDetailId(item.getId());
            forecastOrderDetail.setItemCode(item.getItemCode());
            forecastOrderDetail.setItemDescription(item.getItemName());
            forecastOrderDetail.setBomVersion(item.getBomVersion());
            forecastOrderDetail.setQuantity(item.getCurrentInventory());
            forecastOrderDetail.setCreatedBy(forecastMaterialDTO.getCreatedBy());
            forecastOrderDetail.setCreatedAt(forecastMaterialDTO.getCreatedAt().toInstant());
            forecastOrderDetail.setItemGroupCode(item.getGroupItem());
            forecastOrderDetail.setNote(item.getNote());
            forecastOrderDetail.setProductOrderCode(forecastMaterialDTO.getForecastCode());
            forecastOrderDetail.setPriority(item.getLevelPriorityItem());
            forecastOrderDetail.setDetailResult(objectMapper.writeValueAsString(item.getListLandMark()));
            forecastOrderDetail.setIsActive(true);
            //Count số lượng của NVL trong từng item
            forecastOrderDetail.setMaterialChildrenCount(countMaterialInFc(item));

            //Chuyển status thành phân tích cho các vật tư được chọn
            if (item.getChecked()){
                forecastOrderDetail.setStatus(2);
            }else {
                forecastOrderDetail.setStatus(item.getStatus());
            }
            //Tính tổng quantity trong thời gian kế hoạch cho vật tư
            if (item.getItemStartTime() == null && item.getItemEndTime() == null){
                forecastOrderDetail.setEndTime(forecastMaterialDTO.getEndDate());
                forecastOrderDetail.setStartTime(forecastMaterialDTO.getStartDate());
                forecastOrderDetail.setTotalRequest(Utils.getTotalItem(forecastMaterialDTO.getForecastMode(),forecastMaterialDTO.getStartDate(),forecastMaterialDTO.getEndDate(),item.getListLandMark()));
            } else if (item.getItemStartTime() != null && item.getItemEndTime() == null) {
                forecastOrderDetail.setEndTime(forecastMaterialDTO.getEndDate());
                forecastOrderDetail.setStartTime(item.getItemStartTime());
                forecastOrderDetail.setTotalRequest(Utils.getTotalItem(forecastMaterialDTO.getForecastMode(),item.getItemStartTime(),forecastMaterialDTO.getEndDate(),item.getListLandMark()));
            } else if (item.getItemStartTime() == null && item.getItemEndTime() != null) {
                forecastOrderDetail.setEndTime(item.getItemEndTime());
                forecastOrderDetail.setStartTime(forecastMaterialDTO.getStartDate());
                forecastOrderDetail.setTotalRequest(Utils.getTotalItem(forecastMaterialDTO.getForecastMode(),forecastMaterialDTO.getStartDate(),item.getItemEndTime(),item.getListLandMark()));
            } else {
                forecastOrderDetail.setEndTime(item.getItemEndTime());
                forecastOrderDetail.setStartTime(item.getItemStartTime());
                forecastOrderDetail.setTotalRequest(Utils.getTotalItem(forecastMaterialDTO.getForecastMode(),item.getItemStartTime(),item.getItemEndTime(),item.getListLandMark()));
            }
            list.add(forecastOrderDetail);
        }
        forecastOrderDetailRepository.saveAll(list);
    }

    public Integer countMaterialInFc(MaterialPlanDTO dto){
        ItemQuantity countChildren = new ItemQuantity();
        countChildren.setQuantity(0.0);
        List<MrpDetailDTO> detailDTOS;

        if (dto.getGroupItem().equals("NVL")){
            return 1;
        }else {
            //query lấy children của BTP
            detailDTOS = coittRepository.getAllMrpProductBom(dto.getItemCode(), dto.getBomVersion());
            logger.info("children của sản phẩm: {}", detailDTOS);

            //Cho vào hàm đệ quy để tìm các NVl/BTp con trong TP
            productOrderService.getChildrenCountOfProduct(countChildren, detailDTOS);

            System.err.println("countChildren dff: " + countChildren.getQuantity());
            return countChildren.getQuantity().intValue();
        }
    }
}
