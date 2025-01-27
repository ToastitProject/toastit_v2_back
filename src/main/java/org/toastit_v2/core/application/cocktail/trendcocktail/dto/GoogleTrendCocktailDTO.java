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

        // "data"에서 필요한 값을 추출
        Map<String, Object> data = (Map<String, Object>) apiResponse.get("data");
        Map<String, Object> interestOverTime = (Map<String, Object>) data.get("interest_over_time");
        List<Map<String, Object>> timelineData = (List<Map<String, Object>>) interestOverTime.get("timeline_data");

        // 현재 날짜 기준으로 전 달 계산

        int totalValue = 0;

        // timelineData에서 전 달의 extracted_value를 합산
        for (Map<String, Object> entry : timelineData) {
            String dateRange = (String) entry.get("date");
            LocalDate startDate = parseDateRange(dateRange);

            // 전 달인 경우에만 extracted_value 합산
            if (!startDate.isBefore(DateCalculator.firstDayOfLastMonth()) && !startDate.isAfter(DateCalculator.lastDayOfLastMonth_notFormatting())) {
                List<Map<String, Object>> values = (List<Map<String, Object>>) entry.get("values");
                if (!values.isEmpty()) {
                    // Integer로 가져오고 필요 시 변환
                    totalValue += (Integer) values.get(0).get("extracted_value");
                }
            }
        }
        dto.setValue(totalValue);

        // previousMonthData 설정
        String month = DateCalculator.firstDayOfLastMonth().getMonth().name().substring(0, 3).toUpperCase(); // 예: "DEC"
        String year = String.valueOf(DateCalculator.firstDayOfLastMonth().getYear()); // 예: "2024"
        dto.setPreviousMonthData(month + " - " + year);

        return dto;
    }


    // 날짜 범위를 파싱하는 메서드
    private LocalDate parseDateRange(String dateRange) {
        // 다양한 구분자 처리
        String[] parts = dateRange.split("–| - "); // em dash와 hyphen 모두 처리

        // 형식이 올바르지 않은 경우 예외 처리
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid date range format: " + dateRange);
        }

        // 시작 날짜 문자열 생성
        String startDateString = parts[0].trim();
        String endDateString = parts[1].trim();

        // 불필요한 정보 제거 (예: ", 2025"와 같은 부분)
        startDateString = startDateString.replaceAll(",\\s*\\d{4}$", ""); // 연도 제거
        endDateString = endDateString.replaceAll(",\\s*\\d{4}$", ""); // 연도 제거

        startDateString += ", " + parts[1].trim().split(",")[1].trim(); // 올바른 형식으로 연도 추가

        // DateTimeFormatter 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

        // 날짜 파싱
        return LocalDate.parse(startDateString, formatter);
    }

}
