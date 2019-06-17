package com.github.chillibox.exp.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * <p>Created on 2017/6/26.</p>
 *
 * @author Gonster
 */
public class DateUtil {

    private DateUtil() {
    }

    /**
     * 当天整点 2017-6-26 00:00:00
     *
     * @return 当天整点
     */
    public static Date getTodaySharp() {
        return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date getSomeDaysBefore(Date date, int some) {
        if (some == 0) return date;
        ZonedDateTime zonedDateTime = date.toInstant().atZone(ZoneId.systemDefault());
        ZonedDateTime someDaysBefore = zonedDateTime.minusDays(some);
        return Date.from(someDaysBefore.toInstant());
    }

    public static Date getWeekDateSharp() {
        ZonedDateTime zonedDateTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).withDayOfMonth(1);
        return Date.from(zonedDateTime.toInstant());
    }
}
