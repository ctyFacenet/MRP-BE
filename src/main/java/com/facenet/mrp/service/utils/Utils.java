package com.facenet.mrp.service.utils;

import com.facenet.mrp.service.dto.mrp.LandMarkDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.util.*;

public class Utils {
    private static Logger log = LogManager.getLogger(Utils.class);
    public static <T> boolean hasDuplicate(Iterable<T> all) {
        Set<T> set = new HashSet<>();
        // Set#add returns false if the set does not change, which
        // indicates that a duplicate element has been added.
        for (T each: all) if (!set.add(each)) return true;
        return false;
    }

    public static String toItemKey(String itemCode, String bomVersion) {
        if (StringUtils.isEmpty(bomVersion))
            return itemCode + "-";
        else
            return itemCode + "-" + bomVersion;
    }

    public static Date toUTC(Date source) {
        return DateUtils.addHours(source, -7);
    }

    //lấy tổng sl yêu cầu các mốc theo đk hợp lệ
    public static Double getTotalItem(String inputMode, Date startTime, Date endTime, List<LandMarkDTO> list) throws ParseException {
        Double total = 0.0;
        String mode = inputMode.toLowerCase(Locale.ROOT);
        if (mode.equals("0")) {
            log.info("----------------------step in week");
            Calendar calendar = Calendar.getInstance();
            LocalDate startDate = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            calendar.set(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfYear());
            int startWeek = calendar.get(Calendar.WEEK_OF_YEAR);
            LocalDate endDate = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            calendar.set(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfYear());
            int endWeek = calendar.get(Calendar.WEEK_OF_YEAR);
            //nếu cùng year thì đk đúng
            if (startDate.getYear() == endDate.getYear()) {
                for (LandMarkDTO item : list) {
                    Date getDate = new SimpleDateFormat("yyyy-MM-dd").parse(item.getLandMarkDay());
                    LocalDate date = getDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    calendar.set(date.getYear(), date.getMonthValue(), date.getDayOfYear());
                    int week = calendar.get(Calendar.WEEK_OF_YEAR);
                    //nếu week thuộc khoảng [startWeek - endWeek]
                    if (week >= startWeek && week <= endWeek) {
                        total += item.getQuantity();
                    }
                }
            } else {//nếu khác year thì đk đúng
                for (LandMarkDTO item : list) {
                    Date getDate = new SimpleDateFormat("yyyy-MM-dd").parse(item.getLandMarkDay());
                    LocalDate date = getDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    calendar.set(date.getYear(), date.getMonthValue(), date.getDayOfYear());
                    int week = calendar.get(Calendar.WEEK_OF_YEAR);
                    //nếu cùng year với startTime thì đk đúng
                    if (date.getYear() == startDate.getYear()) {
                        if (week >= startWeek) {
                            total += item.getQuantity();
                        }
                    } else if (date.getYear() == endDate.getYear()) {//nếu cùng year với endTime thì đk đúng
                        if (week <= endWeek) {
                            total += item.getQuantity();
                        }
                    } else if (date.getYear() > startDate.getYear() && date.getYear() < endDate.getYear()) {
                        total += item.getQuantity();//nếu year thuộc khoảng (startTime - endTime) thì đk đúng
                    }
                }
            }
        }else if(mode.equals("1")) {
            log.info("----------------------step in month");
            LocalDate startDate = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int startMonth = startDate.getMonthValue();
            int endMonth = endDate.getMonthValue();
            if (startDate.getYear() == endDate.getYear()) {
                for (LandMarkDTO item : list) {
                    Date getDate = new SimpleDateFormat("yyyy-MM-dd").parse(item.getLandMarkDay());
                    int month = getDate.getMonth() + 1;
                    if (month >= startMonth && month <= endMonth) {
                        total += item.getQuantity();
                    }
                }
            } else {
                for (LandMarkDTO item : list) {
                    Date getDate = new SimpleDateFormat("yyyy-MM-dd").parse(item.getLandMarkDay());
                    int month = getDate.getMonth() + 1;
                    if (getDate.getYear() == startDate.getYear()) {
                        if (month >= startMonth) {
                            total += item.getQuantity();
                        }
                    } else if (getDate.getYear() == endDate.getYear()) {
                        if (month <= endMonth) {
                            total += item.getQuantity();
                        }
                    } else if (getDate.getYear() > startDate.getYear() && getDate.getYear() < endDate.getYear()) {
                        total += item.getQuantity();
                    }
                }
            }
        }else if (mode.equals("2"))
        {
            log.info("----------------------step in quater");
            LocalDate startDateQuater = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int startQuater = startDateQuater.get(IsoFields.QUARTER_OF_YEAR);
            LocalDate endDateQuater = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int endQuater = endDateQuater.get(IsoFields.QUARTER_OF_YEAR);
            if (startDateQuater.getYear() == endDateQuater.getYear()) {
                for (LandMarkDTO item : list
                ) {
                    Date getDate = new SimpleDateFormat("yyyy-MM-dd").parse(item.getLandMarkDay());
                    LocalDate date = getDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int quater = date.get(IsoFields.QUARTER_OF_YEAR);
                    if ((quater >= startQuater && quater <= endQuater)) {
                        total += item.getQuantity();
                    }
                }
            } else {
                for (LandMarkDTO item : list) {
                    Date getDate = new SimpleDateFormat("yyyy-MM-dd").parse(item.getLandMarkDay());
                    LocalDate date = getDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int quater = date.get(IsoFields.QUARTER_OF_YEAR);
                    if (date.getYear() == startDateQuater.getYear()) {
                        if (quater >= startQuater) {
                            total += item.getQuantity();
                        }
                    } else if (date.getYear() == endDateQuater.getYear()) {
                        if (quater <= endQuater) {
                            total += item.getQuantity();
                        }
                    } else if (date.getYear() > startDateQuater.getYear() && date.getYear() < endDateQuater.getYear()) {
                        total += item.getQuantity();
                    }
                }
            }
        }
        log.info("----------------------total "+total);
        return total;
    }
}
