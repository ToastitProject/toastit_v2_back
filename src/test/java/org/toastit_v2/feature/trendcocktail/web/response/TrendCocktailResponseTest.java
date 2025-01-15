package org.toastit_v2.feature.trendcocktail.web.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.toastit_v2.feature.trendcocktail.application.dto.NaverTrendCocktailDTO;

import static org.junit.jupiter.api.Assertions.*;

class TrendCocktailResponseTest {

    @Test
    @DisplayName("DTO 객체 생성 테스트")
    void fromNaverResponse() {
        String jsonResponse = "{\"startDate\":\"2024-10-01\",\"endDate\":\"2024-11-30\",\"timeUnit\":\"month\",\"results\":[{\"title\":\"모히또\",\"keywords\":[\"모히또\"],\"data\":[{\"period\":\"2024-10-01\",\"ratio\":100},{\"period\":\"2024-11-01\",\"ratio\":90.33435}]},{\"title\":\"마티니\",\"keywords\":[\"마티니\"],\"data\":[{\"period\":\"2024-10-01\",\"ratio\":81.50705},{\"period\":\"2024-11-01\",\"ratio\":78.88351}]}]}";
        String response = "{\"startDate\":\"2024-10-01\",\"endDate\":\"2024-11-30\",\"timeUnit\":\"month\",\"results\":[{\"title\":\"모히또\",\"keywords\":[\"모히또\"],\"data\":[{\"period\":\"2024-10-01\",\"ratio\":100},{\"period\":\"2024-11-01\",\"ratio\":90.33435}]},{\"title\":\"마티니\",\"keywords\":[\"마티니\"],\"data\":[{\"period\":\"2024-10-01\",\"ratio\":81.50705},{\"period\":\"2024-11-01\",\"ratio\":78.88351}]}]}";
        // 메서드 호출
        NaverTrendCocktailDTO result = TrendCocktailResponse.fromNaverResponse(response);

        assertNotNull(result);
        assertEquals("2024-10-01", result.getStartDate());
        assertEquals("2024-11-30", result.getEndDate());
        assertEquals("month", result.getTimeUnit());
        assertEquals(2, result.getResults().size());


        // 첫 번째 결과 검증
        NaverTrendCocktailDTO.Result firstResult = result.getResults().get(0);
        assertEquals("모히또", firstResult.getTitle());
        assertEquals(1, firstResult.getKeywords().size());
        assertEquals("모히또", firstResult.getKeywords().get(0));
        assertEquals(2, firstResult.getData().size());

        // 첫 번째 데이터 검증
        NaverTrendCocktailDTO.Data firstData = firstResult.getData().get(0);
        assertEquals("2024-10-01", firstData.getPeriod());
        assertEquals(100, firstData.getRatio());

        // 두 번째 결과 검증
        NaverTrendCocktailDTO.Result secondResult = result.getResults().get(1);
        assertEquals("마티니", secondResult.getTitle());
        assertEquals(1, secondResult.getKeywords().size());
        assertEquals("마티니", secondResult.getKeywords().get(0));
        assertEquals(2, secondResult.getData().size());

        // 두 번째 데이터 검증
        NaverTrendCocktailDTO.Data secondData = secondResult.getData().get(0);
        assertEquals("2024-10-01", secondData.getPeriod());
        assertEquals(81.50705, secondData.getRatio());
    }

    }
