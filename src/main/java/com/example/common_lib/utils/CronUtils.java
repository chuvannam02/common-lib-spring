package com.example.common_lib.utils;

import com.example.common_lib.dto.ScheduleIN;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.zone.ZoneRulesException;
import java.util.function.Function;

/**
 * @Project: common-lib
 * @Package: com.example.common_lib.utils  *
 * @Author: ChuVanNam
 * @Date: 7/26/2025
 * @Time: 5:00 PM
 */

public class CronUtils {
    // Javadoc comment
    // Bôi đen method -> alt + enter -> create Javadoc
    /**
     * @param scheduleIN
     * @return cron expression
     */
    public static String generateExpression(ScheduleIN scheduleIN) {
        if (scheduleIN.getFrequency() == null) return null;
        Function<ScheduleIN, String> buildCronExpression = switch (scheduleIN.getFrequency()) {
            case "Thủ công"      -> _v -> null;
            case "Định kỳ phút"  -> CronUtils::minutely;
            case "Định kỳ giờ"   -> CronUtils::hourly;
            case "Định kỳ ngày"  -> CronUtils::daily;
            case "Định kỳ tuần"  -> CronUtils::weekly;
            case "Định kỳ tháng" -> CronUtils::monthly;
            case "Định kỳ năm"   -> CronUtils::yearly;
            default              -> null;
        };
        return buildCronExpression.apply(scheduleIN);
    }
//        return switch (frequency) {
//            case "Thủ công" ->
//                // Thực hiện công việc thủ công
//                    "";
//            case "Định kỳ phút" ->
//                // Thực hiện định kỳ theo phút
//                    "0 */" + minute + " * * * ?";
//            case "Định kỳ giờ" ->
//                // Thực hiện định kỳ theo giờ
//                    "0 */" + minute + " " + (Integer.parseInt(period) / 60) + " * * ?";
//            case "Định kỳ ngày" ->
//                // Thực hiện định kỳ theo ngày
//                    time.split(":")[2] + " " + time.split(":")[1] + " " + time.split(":")[0] + " * * ?";
//            case "Định kỳ tuần" ->
//            // Thực hiện định kỳ theo tuần
//            {
//                if(day.contains("T2")) day = day.replace("T2", "1");
//                if(day.contains("T3")) day = day.replace("T3", "2");
//                if(day.contains("T4")) day = day.replace("T4", "3");
//                if(day.contains("T5")) day = day.replace("T5", "4");
//                if(day.contains("T6")) day = day.replace("T6", "5");
//                if(day.contains("T7")) day = day.replace("T7", "6");
//                if(day.contains("CN")) day = day.replace("CN", "7");
//                yield time.split(":")[2] + " " + time.split(":")[1] + " " + time.split(":")[0] + " ? * " + day;
//            }
//            case "Định kỳ tháng" ->
//                // Thực hiện định kỳ theo tháng
//                    "0 " + time.split(":")[1] + " " + time.split(":")[0] + " " + day + " * ?";
//            case "Định kỳ năm" ->
//                // Thực hiện định kỳ theo năm
//                    "0 " + time.split(":")[1] + " " + time.split(":")[0] + " " + day.split("/")[0] + " " + day.split("/")[1] + " ?";
//            default -> null;
//        };

    private static String minutely(ScheduleIN scheduleIN) {
        return String.format("0 */%s * * * ?", scheduleIN.getMinute());
    }

    private static String hourly(ScheduleIN scheduleIN) {
        return String.format("%s */%s * * *", Integer.valueOf(scheduleIN.getPeriod()) % 60, Integer.valueOf(scheduleIN.getPeriod()) / 60);
    }

    private static String daily(ScheduleIN scheduleIN) {
        return "%s %s * * *".formatted(scheduleIN.getTime().split(":")[2], scheduleIN.getTime().split(":")[1]);
    }

    private static String weekly(ScheduleIN scheduleIN) {
        String[] time = scheduleIN.getTime().split(":");
        String jobDay = scheduleIN.getDay()
                .replace("T2", "Mon")
                .replace("T3", "Tue")
                .replace("T4", "Wed")
                .replace("T5", "Thu")
                .replace("T6", "Fri")
                .replace("T7", "Sat")
                .replace("CN", "Sun");
        return "%s %s * * %s".formatted(time[1], time[0], jobDay);
    }

    private static String monthly(ScheduleIN scheduleIN) {
        String[] time = scheduleIN.getTime().split(":");
        long epochHour = Long.parseLong(time[0]);
        long epochMinute = Long.parseLong(time[1]);
        return "%s %s %s * *".formatted(epochMinute, epochHour, scheduleIN.getDay());
    }

    private static String yearly(ScheduleIN scheduleIN) {
        String day = scheduleIN.getDay().split("/")[0];
        String month = scheduleIN.getDay().split("/")[1];
        var date = parseTime(scheduleIN.getTime(), "Asia/Ho_Chi_Minh");
        long epochHour = date.getHour();
        long epochMinute = date.getMinute();
        scheduleIN.setDay(scheduleIN.getDay() + "/" + LocalDateTime.now().getYear());
        return String.format("%s %s %s %s *", epochMinute, epochHour , day, month);
    }

//    private static LocalDateTime parseTime(String time, String timeZone) {
//        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.of(timeZone)));
//    }

    private static LocalDateTime parseTime(String time, String timeZone) {
        try {
            LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));
            LocalDate today = LocalDate.now(ZoneId.of(timeZone));
            return LocalDateTime.of(today, localTime);
        } catch (DateTimeParseException | ZoneRulesException e) {
            // Fallback về 00:00 nếu lỗi
            return LocalDateTime.of(LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh")), LocalTime.MIDNIGHT);
        }
    }

    private static ZonedDateTime parseTimeToZoned(String time, String timeZone) {
        LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalDate today = LocalDate.now(ZoneId.of(timeZone));
        LocalDateTime ldt = LocalDateTime.of(today, localTime);
        return ldt.atZone(ZoneId.of(timeZone));
    }

}
