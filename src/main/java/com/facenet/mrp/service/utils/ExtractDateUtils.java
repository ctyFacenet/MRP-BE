package com.facenet.mrp.service.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class ExtractDateUtils {
    public static Timestamp extractDateToTimestamp(Instant instant) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(instant));
        calendar.set(Calendar.ZONE_OFFSET, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static List<Timestamp> startDate(Timestamp timestamp) {
        ZonedDateTime zonedDateTime = timestamp.toInstant().atZone(ZoneId.of("UTC"));
        LocalDate date = zonedDateTime.toLocalDate();
        ZonedDateTime startOfDay = date.atStartOfDay(ZoneId.of("UTC"));
        ZonedDateTime startOfNextDay = startOfDay.plusDays(1);
        Timestamp startOfDayTimestamp = Timestamp.from(startOfDay.toInstant());
        Timestamp startOfNextDayTimestamp = Timestamp.from(startOfNextDay.toInstant());
        return new ArrayList<>(List.of(startOfDayTimestamp, startOfNextDayTimestamp));
    }

    public static LocalDate toLocalDateTime(String dateString) {
        if (Pattern.matches("\\d{1,2}-\\d{1,2}-\\d{4}", dateString)) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d-M-yyyy"); // Updated format
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            try {
                LocalDate date = LocalDate.parse(dateString, inputFormatter);
                LocalDateTime localDateTime = date.atStartOfDay();
                ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"));
                String isoFormattedDate = zonedDateTime.format(outputFormatter);
                return localDate(isoFormattedDate);
            } catch (DateTimeParseException e) {
                return null;
            }
        } else {
            return localDate(dateString);
        }

    }

    public static LocalDate localDate(String dateString) {
        Instant instant = Instant.parse(dateString);
        ZoneId zoneId = ZoneId.of("UTC");
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return LocalDate.from(localDateTime);
    }

}
