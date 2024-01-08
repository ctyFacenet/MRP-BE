package com.facenet.mrp.service.utils;

import com.facenet.mrp.service.dto.mrp.ItemQuantityWithDate;
import com.facenet.mrp.service.dto.mrp.MrpDetailDTO;
import com.facenet.mrp.service.dto.mrp.MrpResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MrpAnalysisUtil {
    private static final Logger log = LoggerFactory.getLogger(MrpAnalysisUtil.class);

    public static void setAnalysisDetail(MrpDetailDTO mrpDetailDTO,
                                  MrpResultDTO mrpResultDTO,
                                  Date beforeDate, Calendar calStartTime,
                                  Map<String, List<ItemQuantityWithDate>> itemOnOrderQuantityMap,
//                                  Map<String, List<ItemQuantityWithDate>> poQuantity,
//                                  Map<String, List<ItemQuantityWithDate>> prQuantity,
//                                  Map<String, List<ItemQuantityWithDate>> grpoQuantity,
                                  Map<String, Double> poQuantity,
                                  Map<String, Double> prQuantity,
                                  Map<String, Double> grpoQuantity,
                                  Map<String, List<ItemQuantityWithDate>> itemHoldQuantity,
                                  Map<String, List<ItemQuantityWithDate>> itemHoldQuantityByWhs,
                                  Map<String, Double> currentInventoryHM,
                                  SimpleDateFormat simpleDateFormat
    ) throws ParseException {
        log.info("Set analysis detail for {}, startDate {}, endDate {}", mrpDetailDTO.getItemCode(), beforeDate, calStartTime.getTime());
        // SL PR đã chấp nhận
        if (itemOnOrderQuantityMap.containsKey(mrpDetailDTO.getItemCode())) {
            for (ItemQuantityWithDate checkDate : itemOnOrderQuantityMap.get(mrpDetailDTO.getItemCode())) {
                Date itemDueDate = simpleDateFormat.parse(checkDate.getDate());
                log.info("PR (dueDate: {}), (item: {})", checkDate.getDate(), mrpDetailDTO.getItemCode());
                // Between beforeDate and startTime
                if (itemDueDate.compareTo(beforeDate) > 0 && itemDueDate.compareTo(calStartTime.getTime()) <= 0) {
                    mrpResultDTO.setExpectedQuantity(mrpResultDTO.getExpectedQuantity() + checkDate.getQuantity());
                }
            }
        }

        // PO quantity
//        if (poQuantity.containsKey(mrpDetailDTO.getItemCode())) {
//            for (ItemQuantityWithDate checkDate : poQuantity.get(mrpDetailDTO.getItemCode())) {
//                Date itemDueDate = simpleDateFormat.parse(checkDate.getDate());
//                log.info("PO (dueDate: {}), (item: {})", checkDate.getDate(), mrpDetailDTO.getItemCode());
//                // Between beforeDate and startTime
//                if (itemDueDate.compareTo(beforeDate) > 0 && itemDueDate.compareTo(calStartTime.getTime()) <= 0) {
//                    mrpResultDTO.setPoQuantity(mrpResultDTO.getPoQuantity() + checkDate.getQuantity());
//                }
//            }
//        }

        // PR quantity
//        if (prQuantity.containsKey(mrpDetailDTO.getItemCode())) {
//            for (ItemQuantityWithDate checkDate : prQuantity.get(mrpDetailDTO.getItemCode())) {
//                Date itemDueDate = simpleDateFormat.parse(checkDate.getDate());
//                log.info("PR (dueDate: {}), (item: {})", checkDate.getDate(), mrpDetailDTO.getItemCode());
//                // Between beforeDate and startTime
//                if (itemDueDate.compareTo(beforeDate) > 0 && itemDueDate.compareTo(calStartTime.getTime()) <= 0) {
//                    mrpResultDTO.setDeliveringQuantity(mrpResultDTO.getDeliveringQuantity() + checkDate.getQuantity());
//                }
//            }
//            mrpResultDTO.setDeliveringQuantity(mrpResultDTO.getDeliveringQuantity() - mrpResultDTO.getPoQuantity());
//        }

        // GRPO quantity
//        if (grpoQuantity.containsKey(mrpDetailDTO.getItemCode())) {
//            Double grpoSumQuantity = 0.0;
//            for (ItemQuantityWithDate checkDate : grpoQuantity.get(mrpDetailDTO.getItemCode())) {
//                Date itemDueDate = simpleDateFormat.parse(checkDate.getDate());
//                log.info("GRPO (dueDate: {}), (item: {})", checkDate.getDate(), mrpDetailDTO.getItemCode());
//                // Between beforeDate and startTime
//                if (itemDueDate.compareTo(beforeDate) > 0 && itemDueDate.compareTo(calStartTime.getTime()) <= 0) {
//                    grpoSumQuantity += checkDate.getQuantity();
//                }
//            }
//            mrpResultDTO.setPoQuantity(mrpResultDTO.getPoQuantity() - grpoSumQuantity);
//        }

        // Hold quantity
//        if (itemHoldQuantity.containsKey(mrpDetailDTO.getItemCode())) {
//            for (ItemQuantityWithDate checkDate : itemHoldQuantity.get(mrpDetailDTO.getItemCode())) {
//                Date itemHoldDate = simpleDateFormat.parse(checkDate.getDate());
//                log.info("hold date {}", checkDate.getDate());
//                // Between beforeDate and startTime  beforeDate < itemHoldDate < startTime
//                if (itemHoldDate.compareTo(beforeDate) > 0 && itemHoldDate.compareTo(calStartTime.getTime()) <= 0) {
//                    log.info("set hold number {} for {} ", checkDate.getQuantity(), checkDate.getItemCode());
//                    mrpResultDTO.setRequiredQuantity(mrpResultDTO.getRequiredQuantity() + checkDate.getQuantity());
//                }
//            }
//        }
//
//        // Hold quantity by whs
//        if (itemHoldQuantityByWhs.containsKey(mrpDetailDTO.getItemCode())) {
//            for (ItemQuantityWithDate checkDate : itemHoldQuantityByWhs.get(mrpDetailDTO.getItemCode())) {
//                Date itemHoldDate = simpleDateFormat.parse(checkDate.getDate());
//                log.info("hold date {}", checkDate.getDate());
//                // Between beforeDate and startTime  beforeDate < itemHoldDate < startTime
//                if (itemHoldDate.compareTo(beforeDate) > 0 && itemHoldDate.compareTo(calStartTime.getTime()) <= 0) {
//                    log.info("set hold number {} for {} ", checkDate.getQuantity(), checkDate.getItemCode());
//                    mrpResultDTO.setRequiredQuantity(mrpResultDTO.getRequiredQuantity() + checkDate.getQuantity());
//                }
//            }
//        }
    }

    public static void setAnalysisDetailV2(MrpDetailDTO mrpDetailDTO,
                                         MrpResultDTO mrpResultDTO,
                                         Date beforeDate, Calendar calStartTime,
                                         Map<String, List<ItemQuantityWithDate>> itemOnOrderQuantityMap,
                                         SimpleDateFormat simpleDateFormat
    ) throws ParseException {
//        log.debug("Set analysis detail for {}, startDate {}, endDate {}", mrpDetailDTO.getItemCode(), beforeDate, calStartTime.getTime());
        // SL PR đã chấp nhận
        if (itemOnOrderQuantityMap.containsKey(mrpDetailDTO.getItemCode())) {
            for (ItemQuantityWithDate checkDate : itemOnOrderQuantityMap.get(mrpDetailDTO.getItemCode())) {
                Date itemDueDate = simpleDateFormat.parse(checkDate.getDate());
//                log.debug("PR (dueDate: {}), (item: {})", checkDate.getDate(), mrpDetailDTO.getItemCode());
                // Between beforeDate and startTime
                if (itemDueDate.compareTo(beforeDate) > 0 && itemDueDate.compareTo(calStartTime.getTime()) <= 0) {
                    mrpResultDTO.setExpectedQuantity(mrpResultDTO.getExpectedQuantity() + checkDate.getQuantity());
                }
            }
        }
    }

    public static void setHoldQuantity(MrpDetailDTO mrpDetailDTO,
                                       MrpResultDTO mrpResultDTO,
                                       Date beforeDate, Calendar calStartTime,
                                       Map<String, List<ItemQuantityWithDate>> itemHoldQuantity,
                                       SimpleDateFormat simpleDateFormat
    ) throws ParseException {
        if (itemHoldQuantity.containsKey(mrpDetailDTO.getItemCode())) {
            for (ItemQuantityWithDate checkDate : itemHoldQuantity.get(mrpDetailDTO.getItemCode())) {
                Date itemHoldDate = simpleDateFormat.parse(checkDate.getDate());
//                log.info("hold date {}", checkDate.getDate());
                // Between beforeDate and startTime  beforeDate < itemHoldDate < startTime
                if (itemHoldDate.compareTo(beforeDate) > 0 && itemHoldDate.compareTo(calStartTime.getTime()) <= 0) {
//                    log.info("set hold number {} for {} ", checkDate.getQuantity(), checkDate.getItemCode());
                    mrpResultDTO.setRequiredQuantity(mrpResultDTO.getRequiredQuantity() + checkDate.getQuantity());
                }
            }
        }
    }

    public static void setHoldQuantityV3(MrpDetailDTO mrpDetailDTO,
                                       MrpResultDTO mrpResultDTO,
                                       Date beforeDate, Date currentDate,
                                       Map<String, List<ItemQuantityWithDate>> itemHoldQuantity,
                                       SimpleDateFormat simpleDateFormat
    ) throws ParseException {
        if (itemHoldQuantity.containsKey(mrpDetailDTO.getItemCode())) {
            for (ItemQuantityWithDate checkDate : itemHoldQuantity.get(mrpDetailDTO.getItemCode())) {
                Date itemHoldDate = simpleDateFormat.parse(checkDate.getDate());
//                log.info("hold date {}", checkDate.getDate());
                // Between beforeDate and startTime  beforeDate < itemHoldDate < startTime
                if (itemHoldDate.compareTo(beforeDate) > 0 && itemHoldDate.compareTo(currentDate) <= 0) {
//                    log.info("set hold number {} for {} ", checkDate.getQuantity(), checkDate.getItemCode());
                    mrpResultDTO.setRequiredQuantity(mrpResultDTO.getRequiredQuantity() + checkDate.getQuantity());
                }
            }
        }
    }

    public static void addMrpBomToItem(MrpDetailDTO parent, List<MrpDetailDTO> children, String type) {
        if (!CollectionUtils.isEmpty(children)) {
            for (MrpDetailDTO childItem : children) {
                if (childItem.getGroupItem().equals(type)) {
                    parent.getChildren().add(childItem);
                }
            }
        }
    }

    public static void addMrpBomToItem(MrpDetailDTO parent, List<MrpDetailDTO> children) {
        if (!CollectionUtils.isEmpty(children)) {
            for (MrpDetailDTO childItem : children) {
                parent.getChildren().add(childItem);
            }
        }
    }


    public static void setPrPoQuantity(
        String itemCode,
        MrpResultDTO mrpResultDTO, Map<String, Double> prQuantity,
        Map<String, Double> poQuantity,
        Map<String, Double> grpoQuantity) {
        mrpResultDTO.setPoQuantity(
            poQuantity.getOrDefault(itemCode, 0.0)
//                - grpoQuantity.getOrDefault(itemCode, 0.0)
        );
        mrpResultDTO.setDeliveringQuantity(
            prQuantity.getOrDefault(itemCode, 0.0)
//                - poQuantity.getOrDefault(itemCode, 0.0)
        );
    }

    public static void setPrPoQuantityV3(
        String itemCode,
        MrpResultDTO mrpResultDTO, Map<String, Double> prQuantity,
        Map<String, Double> poQuantity) {
        mrpResultDTO.setPoQuantity(
            poQuantity.getOrDefault(itemCode, 0.0)
        );
        mrpResultDTO.setDeliveringQuantity(
            prQuantity.getOrDefault(itemCode, 0.0)
        );
    }
}
