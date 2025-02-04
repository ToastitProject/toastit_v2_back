/*
package org.toastit_v2.core.domain.cocktail.trendcocktail;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.toastit_v2.core.application.cocktail.trendcocktail.dto.NaverTrendCocktailDTO;
import org.toastit_v2.core.infrastructure.persistence.cocktail.trendcocktail.TrendCocktailEntity;
import org.toastit_v2.core.ui.cocktail.trendcocktail.payload.response.TrendCocktailResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class TrendCocktailTest {

    @Test
    @DisplayName("검색량 5개의 칵테일 파싱 메서드 테스트")
    void getTopFiveKeywords() {

        //1. Given 10개 의 칵테일 데이터 검색
        //1. 실제 ratio 차이 모히또(9.6) 마티니(2.6) 다이키리(4.5) 피나 콜라다(5) 블루 하와이(5) 모스코 뮬(5) 마가리타(5) 올드 패션드(2) 사이공(1) 블랙 러시안(2)
        String jsonResponse = "{\"startDate\":\"2024-10-01\",\"endDate\":\"2024-11-30\",\"timeUnit\":\"month\",\"results\":[" +
                "{\"title\":" + "\"모히또\",\"keywords\":[\"모히또\"],\"data\":[{\"period\":\"2024-10-01\",\"ratio\":100},{\"period\":\"2024-11-01\",\"ratio\":90.33435}]}," +
                "{\"title\":\"마티니\"," + "\"keywords\":[\"마티니\"],\"data\":[{\"period\":\"2024-10-01\",\"ratio\":81.50705},{\"period\":\"2024-11-01\",\"ratio\":78.88351}]}," +
                "{\"title\":\"다이키리\",\"keywords\":[\"다이키리\"],\"data\":[{\"period\":\"2024-10-01\",\"ratio\":75},{\"period\":\"2024-11-01\",\"ratio\":70.5}]}," +
                "{\"title\":\"피나 콜라다\",\"keywords\":[\"피나 콜라다\"],\"data\":[{\"period\":\"2024-10-01\",\"ratio\":65},{\"period\":\"2024-11-01\",\"ratio\":60}]}," +
                "{\"title\":\"블루 하와이\",\"keywords\":[\"블루 하와이\"],\"data\":[{\"period\":\"2024-10-01\",\"ratio\":90},{\"period\":\"2024-11-01\",\"ratio\":85}]}," +
                "{\"title\":\"모스코 뮬\",\"keywords\":[\"모스코 뮬\"],\"data\":[{\"period\":\"2024-10-01\",\"ratio\":80},{\"period\":\"2024-11-01\",\"ratio\":75}]}," +
                "{\"title\":\"마가리타\",\"keywords\":[\"마가리타\"],\"data\":[{\"period\":\"2024-10-01\",\"ratio\":70},{\"period\":\"2024-11-01\",\"ratio\":65}]}," +
                "{\"title\":\"올드 패션드\",\"keywords\":[\"올드 패션드\"],\"data\":[{\"period\":\"2024-10-01\",\"ratio\":85},{\"period\":\"2024-11-01\",\"ratio\":83}]}," +
                "{\"title\":\"사이공\",\"keywords\":[\"사이공\"],\"data\":[{\"period\":\"2024-10-01\",\"ratio\":60},{\"period\":\"2024-11-01\",\"ratio\":59}]}," +
                "{\"title\":\"블랙 러시안\",\"keywords\":[\"블랙 러시안\"],\"data\":[{\"period\":\"2024-10-01\",\"ratio\":50},{\"period\":\"2024-11-01\",\"ratio\":48}]}]}";

        //2. When 응답을 DTO -> 자료구조 저장 -> 상위 검색량 5개 추출
        NaverTrendCocktailDTO trendCocktailDTO = TrendCocktailResponse.fromNaverResponse(jsonResponse);
        Map<String, List<NaverTrendCocktailDTO.Result>> resultsByKeyword = trendCocktailDTO.getResultsByKeyword();
        List<String> topFiveKeywords = TrendCocktail.getTopFiveKeywords(resultsByKeyword);

        //3. Then 5개의 키워드 출력
        Assertions.assertThat(topFiveKeywords).hasSize(5);
        for (String keyword : topFiveKeywords) {
            System.out.println("keyword = " + keyword);
        }
    }

    @Test
    @DisplayName("List<도메인> -> 엔티티로 변환 테스트")
    void convertToEntities(){
        List<String> cocktails = new ArrayList<>();
        cocktails.add("모히또");
        cocktails.add("피나 콜라다");
        cocktails.add("블루 하와이");
        cocktails.add("마가리타");
        cocktails.add("모스코 뮬");

        List<TrendCocktailEntity> trendCocktailEntities = TrendCocktail.convertToEntities(cocktails);
        Assertions.assertThat(trendCocktailEntities.get(0).getName()).isEqualTo("모히또");
        Assertions.assertThat(trendCocktailEntities.get(1).getName()).isEqualTo("피나 콜라다");
        Assertions.assertThat(trendCocktailEntities.get(2).getName()).isEqualTo("블루 하와이");
        Assertions.assertThat(trendCocktailEntities.get(3).getName()).isEqualTo("마가리타");
        Assertions.assertThat(trendCocktailEntities.get(4).getName()).isEqualTo("모스코 뮬");
        Assertions.assertThat(trendCocktailEntities.get(4).getName()).isNotEqualTo("모히또");

    }
}*/
