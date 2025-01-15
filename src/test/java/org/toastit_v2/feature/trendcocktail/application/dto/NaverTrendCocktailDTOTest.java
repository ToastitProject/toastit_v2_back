package org.toastit_v2.feature.trendcocktail.application.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.toastit_v2.feature.trendcocktail.web.response.TrendCocktailResponse;

import java.util.List;
import java.util.Map;


class NaverTrendCocktailDTOTest {

    @Test
    @DisplayName("DTO 객체를 자료구조에 저장하는 테스트")
    void getResultsByKeyword() {

        // 1. Given API 응답과 이를 DTO 로 변환,
        String jsonResponse = "{\"startDate\":\"2024-10-01\",\"endDate\":\"2024-11-30\",\"timeUnit\":\"month\"," + "\"results\":" +
                "[{\"title\":\"모히또\",\"keywords\":[\"모히또\"],\"data\":[{\"period\":\"2024-10-01\",\"ratio\":100}," + "{\"period\":\"2024-11-01\",\"ratio\":90.33435}]}," +
                "{\"title\":\"마티니\",\"keywords\":[\"마티니\"],\"data\":[{\"period\":\"2024-10-01\",\"ratio\":81.50705},{\"period\":\"2024-11-01\",\"ratio\":78.88351}]}]}";

        NaverTrendCocktailDTO naverTrendCocktailDTO = TrendCocktailResponse.fromNaverResponse(jsonResponse);

        // 2. When Map 자료구조에 DTO 객체 삽입
        Map<String, List<NaverTrendCocktailDTO.Result>> resultsByKeyword = naverTrendCocktailDTO.getResultsByKeyword();

        // 3. Then 사이즈 확인
        Assertions.assertThat(resultsByKeyword).isNotNull();
        Assertions.assertThat(resultsByKeyword.size()).isEqualTo(2);
        // 3. Then 검색어 확인
        Assertions.assertThat(resultsByKeyword.containsKey("모히또")).isEqualTo(true);
        Assertions.assertThat(resultsByKeyword.containsKey("마티니")).isEqualTo(true);
        Assertions.assertThat(resultsByKeyword.containsKey("데킬라")).isEqualTo(false);


    }
}