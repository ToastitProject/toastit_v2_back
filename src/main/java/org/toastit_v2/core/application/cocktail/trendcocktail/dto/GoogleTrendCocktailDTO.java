package org.toastit_v2.core.application.cocktail.trendcocktail.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.toastit_v2.core.application.cocktail.trendcocktail.service.utilly.DateCalculator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Data
@Getter
@Setter
@NoArgsConstructor
public class GoogleTrendCocktailDTO {

    private String previousMonthData;
    private String query;
    private int value;

    public GoogleTrendCocktailDTO(String query, int value) {
        this.query = query;
        this.value = value;
        this.previousMonthData = DateCalculator.lastMonth();
    }

    public GoogleTrendCocktailDTO googleTrendAPIConvertDTO(Map<String, Object> apiResponse, String query) {
        GoogleTrendCocktailDTO dto = new GoogleTrendCocktailDTO();
        dto.setQuery(query);
        Map<String, Object> data = (Map<String, Object>) apiResponse.get("data");
        Map<String, Object> interestOverTime = (Map<String, Object>) data.get("interest_over_time");
        List<Map<String, Object>> timelineData = (List<Map<String, Object>>) interestOverTime.get("timeline_data");

        int totalValue = 0;

        for (Map<String, Object> entry : timelineData) {
            String dateRange = (String) entry.get("date");
            LocalDate startDate = parseDateRange(dateRange);

            if (!startDate.isBefore(DateCalculator.firstDayOfLastMonth()) && !startDate.isAfter(DateCalculator.lastDayOfLastMonth_notFormatting())) {
                List<Map<String, Object>> values = (List<Map<String, Object>>) entry.get("values");
                if (!values.isEmpty()) {
                    totalValue += (Integer) values.get(0).get("extracted_value");
                }
            }
        }

        dto.setValue(totalValue);
        String month = DateCalculator.firstDayOfLastMonth().getMonth().name().substring(0, 3).toUpperCase(); // 예: "DEC"
        String year = String.valueOf(DateCalculator.firstDayOfLastMonth().getYear()); // 예: "2024"
        dto.setPreviousMonthData(month + " - " + year);

        return dto;
    }

    private LocalDate parseDateRange(String dateRange) {

        String[] parts = dateRange.split("–| - ");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid date range format: " + dateRange);
        }
        String startDateString = parts[0].trim();
        String endDateString = parts[1].trim();
        startDateString = startDateString.replaceAll(",\\s*\\d{4}$", "");
        endDateString = endDateString.replaceAll(",\\s*\\d{4}$", "");
        startDateString += ", " + parts[1].trim().split(",")[1].trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
        return LocalDate.parse(startDateString, formatter);
    }

}
