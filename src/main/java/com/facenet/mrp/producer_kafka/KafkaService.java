package com.facenet.mrp.producer_kafka;

import com.facenet.mrp.domain.mrp.AlertItemSoEntity;
import com.facenet.mrp.domain.mrp.AlertSoEntity;
import com.facenet.mrp.domain.mrp.ProductOrder;
import com.facenet.mrp.domain.mrp.PurchaseRecommendationEntity;
import com.facenet.mrp.repository.mrp.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class KafkaService {

    @Autowired
    private PurchaseRecommendationDetailRepository recommendationDetailRepository;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private SapOnOrderDurationDetailRepository durationDetailRepository;

    @Autowired
    private AlertSoRepository alertSoRepository;

    @Autowired
    private SapOnOrderSummaryRepository sapOnOrderSummaryRepository;

    @Autowired
    private AlertItemSoRepository alertItemSoRepository;

    private final Logger log = LogManager.getLogger(KafkaService.class);

    /**
     * hàm cảnh báo cho các so và fc
     *
     * @return
     */
    public List<ContextAlert> getInfoSoAndFc() {
        List<ContextAlert> contextAlerts = new ArrayList<>();
        List<AlertSoEntity> alertSoEntities = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -5);
        Date fiveDaysLater = calendar.getTime();
        //lấy danh sách cảch báo đã lưu trong db
        List<AlertSoEntity> alertSoEntityList = alertSoRepository.getAlertSoEntityBeforeTime(fiveDaysLater);
        //lấy danh sách đơn hàng chưa quá hạn trong db
        List<ProductOrder> productOrderList = productOrderRepository.getAllProductOrder(fiveDaysLater);
        for (ProductOrder item : productOrderList) {
            log.info("Chạy đơn hàng");
            log.info(item.getProductOrderCode());
            // Tạo đối tượng Date
            AlertSoEntity check;
            Date startDate = item.getOrderDate();
            Date endDate = item.getDeliverDate();
            Date now = new Date();
            // Tính khoảng cách giữa 2 ngày
            long percent = 0;
            try{
                long rangeTimePO = Math.abs(endDate.getTime() - startDate.getTime());
                long poTime = rangeTimePO / (24 * 60 * 60 * 1000);
                long rangeTimeCheck = Math.abs(now.getTime() - startDate.getTime());
                long checkTime = rangeTimeCheck / (24 * 60 * 60 * 1000);
                percent = (checkTime * 100) / poTime;
            } catch (ArithmeticException e) {
                log.info(e.getMessage());
                log.info("Error: Division by zero or other arithmetic exception occurred.");
            }
            //thời gian đơn hàng quá 60% thì bắt đầu kiểm tra
            if (percent > 60) {
                Double sumPoQuantity = durationDetailRepository.getSapOnOrderDurationDetailBySo(item.getProductOrderCode());
                Double sumQuantitySo = recommendationDetailRepository.sumQuantitySo(item.getProductOrderCode());
                double percentPoOrder;
                if (sumPoQuantity == null || sumQuantitySo == null) {
                    percentPoOrder = 0.0;
                } else {
                    percentPoOrder = (sumPoQuantity / sumQuantitySo) * 100;
                }
                //thời gian đặt hàng > 85% và tỉ lệ hoàn thành > 85%
                if (percent < 85 && percentPoOrder > 85) {
                    ContextAlert contextAlert = new ContextAlert();
                    contextAlert.setSoCode(item.getProductOrderCode());
                    contextAlert.setCostomerName(item.getCustomerName());
                    contextAlert.setSaleCode(item.getPartCode());
                    contextAlert.setSaleName(item.getPartName());
                    contextAlert.setTimeStart(item.getOrderDate());
                    contextAlert.setEndStart(item.getDeliverDate());
                    contextAlert.setStatus("Hoàn thành sớm");
                    contextAlert.setLevel(item.getPriority());
                    contextAlert.setPercent(percentPoOrder);
                    check = checkExist(alertSoEntityList, contextAlert);
                    if (check != null) {

                    }
                    contextAlerts.add(contextAlert);
                    contextAlert.setWarningLevel("Green");
                    alertSoEntities.add(check);
                }
                //thời gian đặt hàng < 85% và tỉ lệ hoàn thành < 85%
                else if (percent < 85 && percentPoOrder < 85) {
                    ContextAlert contextAlert = new ContextAlert();
                    contextAlert.setSoCode(item.getProductOrderCode());
                    contextAlert.setCostomerName(item.getCustomerName());
                    contextAlert.setSaleCode(item.getPartCode());
                    contextAlert.setSaleName(item.getPartName());
                    contextAlert.setTimeStart(item.getOrderDate());
                    contextAlert.setEndStart(item.getDeliverDate());
                    contextAlert.setStatus("Có khả năng chậm tiến độ");
                    contextAlert.setLevel(item.getPriority());
                    contextAlert.setPercent(percentPoOrder);
                    check = checkExist(alertSoEntityList, contextAlert);
                    if (check != null) {

                    }
                    contextAlerts.add(contextAlert);
                    contextAlert.setWarningLevel("Yellow");
                    alertSoEntities.add(check);
                }
                //thời gian đặt hàng > 85% và tỉ lệ hoàn thành < 85%
                else if (percent > 85 && percentPoOrder < 85) {
                    ContextAlert contextAlert = new ContextAlert();
                    contextAlert.setSoCode(item.getProductOrderCode());
                    contextAlert.setCostomerName(item.getCustomerName());
                    contextAlert.setSaleCode(item.getPartCode());
                    contextAlert.setSaleName(item.getPartName());
                    contextAlert.setTimeStart(item.getOrderDate());
                    contextAlert.setEndStart(item.getDeliverDate());
                    contextAlert.setStatus("Không hoàn thành");
                    contextAlert.setLevel(item.getPriority());
                    contextAlert.setPercent(percentPoOrder);
                    check = checkExist(alertSoEntityList, contextAlert);
                    if (check != null) {

                    }
                    contextAlerts.add(contextAlert);
                    contextAlert.setWarningLevel("Red");
                    alertSoEntities.add(check);
                } else if (percent > 85 && percentPoOrder > 85) {
                    ContextAlert contextAlert = new ContextAlert();
                    contextAlert.setSoCode(item.getProductOrderCode());
                    contextAlert.setCostomerName(item.getCustomerName());
                    contextAlert.setSaleCode(item.getPartCode());
                    contextAlert.setSaleName(item.getPartName());
                    contextAlert.setTimeStart(item.getOrderDate());
                    contextAlert.setEndStart(item.getDeliverDate());
                    contextAlert.setStatus("Đã hoàn thành");
                    contextAlert.setLevel(item.getPriority());
                    contextAlert.setPercent(percentPoOrder);
                    check = checkExist(alertSoEntityList, contextAlert);
                    if (check != null) {

                    }
                    contextAlerts.add(contextAlert);
                    contextAlert.setWarningLevel("Green");
                    alertSoEntities.add(check);
                }
            }
        }
        alertSoRepository.saveAll(alertSoEntities);
        for (ContextAlert index : contextAlerts) {
            log.info("----------test o day:" + index.getSoCode() + "/" + index.getPercent() + "/" + index.getTimeStart());
        }
        return contextAlerts;
    }

    /**
     * hàm kiểm tra xem có sự thay dổi các bản ghi hay không
     *
     * @param list
     * @param contextAlert
     * @return
     */
    private AlertSoEntity checkExist(List<AlertSoEntity> list, ContextAlert contextAlert) {
        for (AlertSoEntity item : list) {
            if (item.getSoCode().equals(contextAlert.getSoCode())) {
                if (!item.getPercent().equals(contextAlert.getPercent()) ||
                    !item.getStatus().equals(contextAlert.getStatus())) {
                    item.setSoCode(contextAlert.getSoCode());
                    item.setCustomerName(contextAlert.getCostomerName());
                    item.setStatus(contextAlert.getStatus());
                    item.setLevel(contextAlert.getLevel());
                    item.setTimeEnd(contextAlert.getEndStart());
                    item.setTimeStart(contextAlert.getTimeStart());
                    item.setPercent(contextAlert.getPercent());
                    //có sự thay đổi trả ra bản ghi thay dổi
                    return item;
                } else {
                    return null;
                }
            }
        }
        AlertSoEntity alertSoEntity = new AlertSoEntity();
        alertSoEntity.setSoCode(contextAlert.getSoCode());
        alertSoEntity.setCustomerName(contextAlert.getCostomerName());
        alertSoEntity.setStatus(contextAlert.getStatus());
        alertSoEntity.setLevel(contextAlert.getLevel());
        alertSoEntity.setTimeEnd(contextAlert.getEndStart());
        alertSoEntity.setTimeStart(contextAlert.getTimeStart());
        alertSoEntity.setPercent(contextAlert.getPercent());
        //bản ghi mới
        return alertSoEntity;
    }

    /**
     * hàm cảnh báo nvl ,btp trong đơn hàng
     *
     * @return
     */
    public List<ItemContextAlert> getInfoItemInSoFc() {
        List<ItemContextAlert> alertList = new ArrayList<>();
        List<AlertItemSoEntity> alertItemSoEntityList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -5);
        Date fiveDaysLater = calendar.getTime();
        //lấy danh sách cảnh báo của các item còn trong range time
        List<AlertItemSoEntity> alertItemSoEntities = alertSoRepository.getAlertItemSoEntityBeforeTime(fiveDaysLater);
        //lấy danh sách các item còn trong range time
        List<InfoItemDTO> result = recommendationDetailRepository.getAllOnTime(fiveDaysLater);
        for (InfoItemDTO index : result) {
            PurchaseRecommendationEntity purchaseRecommendation = recommendationDetailRepository.getPurchaseRecommendation(index.getMrpCode());
            if (purchaseRecommendation != null) {
                index.setStartTime(purchaseRecommendation.getStartTime());
            }
        }

        for (InfoItemDTO item : result) {
            log.info("Chạy đơn hàng 1");
            log.info(item.getSoCode());

            Date startDate = item.getStartTime();
            Date endDate = item.getEndTime();
            Date now = new Date();
            // Tính khoảng cách giữa 2 ngày
            long percent = 0;
            try{
                long rangeTimePO = Math.abs(endDate.getTime() - startDate.getTime());
                long poTime = rangeTimePO / (24 * 60 * 60 * 1000);
                long rangeTimeCheck = Math.abs(now.getTime() - startDate.getTime());
                long checkTime = rangeTimeCheck / (24 * 60 * 60 * 1000);
                percent = (checkTime * 100) / poTime;
            } catch (ArithmeticException e) {
                percent = -1;
                log.info(e.getMessage());
                log.info("Error: Division by zero or other arithmetic exception occurred.");
            }
            Double sumPr = sapOnOrderSummaryRepository.sumQuantityItemPr(item.getSoCode(), item.getMrpCode(), item.getItemCode());
            Double sumPO = sapOnOrderSummaryRepository.sumQuantityItemPO(item.getSoCode(), item.getMrpCode(), item.getItemCode());
            double percentFinsh = 0.0;
            if (sumPr == null) {
                log.info("Check 1");
                ItemContextAlert itemContextAlert = new ItemContextAlert();
                itemContextAlert.setSoCode(item.getSoCode());
                itemContextAlert.setMrpCode(item.getMrpCode());
                itemContextAlert.setItemCode(item.getItemCode());
                itemContextAlert.setItemName(item.getItemName());
                itemContextAlert.setStatus("Chưa lên PR");
                itemContextAlert.setStartTime(item.getStartTime());
                itemContextAlert.setEndTime(item.getEndTime());
                itemContextAlert.setPercent(0.0);
                AlertItemSoEntity check = checkExistItem(alertItemSoEntities, itemContextAlert);
                if (check != null) {

                }
                alertItemSoEntityList.add(check);
                itemContextAlert.setWarningLevel("Red");
                alertList.add(itemContextAlert);
            } else if (sumPO == null) {
                log.info("Check 2");
                ItemContextAlert itemContextAlert = new ItemContextAlert();
                itemContextAlert.setSoCode(item.getSoCode());
                itemContextAlert.setMrpCode(item.getMrpCode());
                itemContextAlert.setItemCode(item.getItemCode());
                itemContextAlert.setItemName(item.getItemName());
                itemContextAlert.setStatus("Chưa lên PO");
                itemContextAlert.setPercent(0.0);
                itemContextAlert.setStartTime(item.getStartTime());
                itemContextAlert.setEndTime(item.getEndTime());
                AlertItemSoEntity check = checkExistItem(alertItemSoEntities, itemContextAlert);
                if (check != null) {

                }
                alertItemSoEntityList.add(check);
                itemContextAlert.setWarningLevel("Red");
                alertList.add(itemContextAlert);
            } else if (sumPO != null) {
                log.info("Check 3");
                percentFinsh = (sumPO / sumPr) * 100;
            }
            if (percent > 70 && percent <= 85 && percentFinsh > 85 && sumPr != null) {
                log.info("Check 4");
                ItemContextAlert itemContextAlert = new ItemContextAlert();
                itemContextAlert.setSoCode(item.getSoCode());
                itemContextAlert.setMrpCode(item.getMrpCode());
                itemContextAlert.setItemCode(item.getItemCode());
                itemContextAlert.setItemName(item.getItemName());
                itemContextAlert.setStatus("Hoàn thành sớm");
                itemContextAlert.setStartTime(item.getStartTime());
                itemContextAlert.setEndTime(item.getEndTime());
                itemContextAlert.setPercent(percentFinsh);
                AlertItemSoEntity check = checkExistItem(alertItemSoEntities, itemContextAlert);
                if (check != null) {

                }
                alertItemSoEntityList.add(check);
                itemContextAlert.setWarningLevel("Green");
                alertList.add(itemContextAlert);
            } else if (percent > 70 && percent <= 85 && percentFinsh < 65 && sumPr != null) {
                log.info("Check 5");
                ItemContextAlert itemContextAlert = new ItemContextAlert();
                itemContextAlert.setSoCode(item.getSoCode());
                itemContextAlert.setMrpCode(item.getMrpCode());
                itemContextAlert.setItemCode(item.getItemCode());
                itemContextAlert.setItemName(item.getItemName());
                itemContextAlert.setStatus("Có khả năng chậm tiến độ");
                itemContextAlert.setStartTime(item.getStartTime());
                itemContextAlert.setEndTime(item.getEndTime());
                itemContextAlert.setPercent(percentFinsh);
                AlertItemSoEntity check = checkExistItem(alertItemSoEntities, itemContextAlert);
                if (check != null) {

                }
                alertItemSoEntityList.add(check);
                itemContextAlert.setWarningLevel("Yellow");
                alertList.add(itemContextAlert);
            } else if (percent > 85 && percentFinsh < 85 && sumPr != null) {
                log.info("Check 6");
                ItemContextAlert itemContextAlert = new ItemContextAlert();
                itemContextAlert.setSoCode(item.getSoCode());
                itemContextAlert.setMrpCode(item.getMrpCode());
                itemContextAlert.setItemCode(item.getItemCode());
                itemContextAlert.setItemName(item.getItemName());
                itemContextAlert.setStatus("Không hoàn thành");
                itemContextAlert.setStartTime(item.getStartTime());
                itemContextAlert.setEndTime(item.getEndTime());
                itemContextAlert.setPercent(percentFinsh);
                AlertItemSoEntity check = checkExistItem(alertItemSoEntities, itemContextAlert);
                if (check != null) {

                }
                alertItemSoEntityList.add(check);
                itemContextAlert.setWarningLevel("Red");
                alertList.add(itemContextAlert);
            }
            else {
                log.info("Go to here");
            }
        }
        for (ItemContextAlert a : alertList) {
            log.info("----------------------------------" + a.getItemCode() + "/" + a.getSoCode() + "/" + a.getMrpCode() + "/" + a.getPercent() + "/" + a.getStatus());
        }
        alertItemSoRepository.saveAll(alertItemSoEntityList);
        return alertList;
    }

    private AlertItemSoEntity checkExistItem(List<AlertItemSoEntity> list, ItemContextAlert contextAlert) {
        for (AlertItemSoEntity item : list) {
            if (item.getMrpCode().equals(contextAlert.getMrpCode()) && item.getItemCode().equals(contextAlert.getItemCode())) {
                if (!item.getPercent().equals(contextAlert.getPercent()) ||
                    !item.getStatus().equals(contextAlert.getStatus())) {
                    item.setSoCode(contextAlert.getSoCode());
                    item.setItemCode(contextAlert.getItemCode());
                    item.setStatus(contextAlert.getStatus());
                    item.setPercent(contextAlert.getPercent());
                    item.setItemName(contextAlert.getItemName());
                    item.setMrpCode(contextAlert.getMrpCode());
                    item.setTimeStart(contextAlert.getStartTime());
                    item.setTimeEnd(contextAlert.getEndTime());
                    //có sự thay đổi trả ra bản ghi thay dổi
                    return item;
                } else {
                    return null;
                }
            }
        }
        AlertItemSoEntity alertItemSoEntity = new AlertItemSoEntity();
        alertItemSoEntity.setSoCode(contextAlert.getSoCode());
        alertItemSoEntity.setMrpCode(contextAlert.getMrpCode());
        alertItemSoEntity.setStatus(contextAlert.getStatus());
        alertItemSoEntity.setItemCode(contextAlert.getItemCode());
        alertItemSoEntity.setItemName(contextAlert.getItemName());
        alertItemSoEntity.setPercent(contextAlert.getPercent());
        alertItemSoEntity.setTimeStart(contextAlert.getStartTime());
        alertItemSoEntity.setTimeEnd(contextAlert.getEndTime());
        //bản ghi mới
        return alertItemSoEntity;
    }
}
