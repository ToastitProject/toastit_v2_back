package org.toastit_v2.feature.trendcocktail.application.service.utill;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class dateService {

    static LocalDate now = LocalDate.now();

    public static String firstDayOfLastMonth() {
    LocalDate firstDayOfLastMonth = now.minusMonths(1).withDayOfMonth(1);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return firstDayOfLastMonth.format(formatter);
    }

    public static String lastDayOfLastMonth() {
    LocalDate lastDayOfLastMonth = now.minusMonths(1).withDayOfMonth(now.minusMonths(1).lengthOfMonth());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return lastDayOfLastMonth.format(formatter);
    }

    public static String firstDayOfTwoMonthsAgo() {
        LocalDate firstDayOfTwoMonthsAgo = now.minusMonths(2).withDayOfMonth(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return firstDayOfTwoMonthsAgo.format(formatter);
    }

    public static String lastDayOfTwoMonthsAgo() {
        LocalDate lastDayOfTwoMonthsAgo = now.minusMonths(2).withDayOfMonth(now.minusMonths(2).lengthOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return lastDayOfTwoMonthsAgo.format(formatter);
    }
}
