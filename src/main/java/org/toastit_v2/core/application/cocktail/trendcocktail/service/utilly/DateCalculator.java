package org.toastit_v2.core.application.cocktail.trendcocktail.service.utilly;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateCalculator {

    static LocalDate now = LocalDate.now();

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

    public static String lastMonth() {
        LocalDate lastMonthDate = now.minusMonths(1);
        String monthName = lastMonthDate.getMonth().name().substring(0, 3).toUpperCase();
        int year = lastMonthDate.getYear();
        return String.format("%s - %d", monthName, year);
    }

    public static LocalDate firstDayOfLastMonth() {
        return now.minusMonths(1).withDayOfMonth(1);
    }

    public static LocalDate lastDayOfLastMonth_notFormatting() {
        return now.minusMonths(1).withDayOfMonth(now.minusMonths(1).lengthOfMonth());
    }

}
