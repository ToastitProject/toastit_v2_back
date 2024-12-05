package org.toastit_v2.feature.basecocktail.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class CocktailSearch {
    private final SearchType searchType;    // 검색 종류
    private final String keyword;           // 원본 검색 키워드
    private final List<String> ingredients; // 쉼표로 구분된 재료들
    private final AlcoholType alcoholType;  // 알코올 유무

    @Builder
    private CocktailSearch(SearchType searchType, String keyword, List<String> ingredients, AlcoholType alcoholType) {
        this.searchType = searchType;
        this.keyword = keyword;
        this.ingredients = ingredients;
        this.alcoholType = alcoholType;
    }

    // if - else if 로 구상하고 있는데, 나중에 바꿔야 할 듯
    public static CocktailSearch from(String keyword) {

        // 꼭 응답이 없어도 될듯
        if (keyword == null || keyword.trim().isEmpty()) {
            return new CocktailSearch(SearchType.NONE, null, null, null);
        }

        // 알코올 타입 체크
        AlcoholType alcoholType = null;
        if (keyword.contains("논알코올")) {
            alcoholType = AlcoholType.NON_ALCOHOLIC;
            keyword = keyword.replace("논알코올", "").trim();
        } else if (keyword.contains("알코올")) {
            alcoholType = AlcoholType.ALCOHOLIC;
            keyword = keyword.replace("알코올", "").trim();
        }

        // 키워드가 알코올 타입만 있었다면...
        if (keyword.isEmpty()) {
            return new CocktailSearch(SearchType.ALCOHOL_ONLY, null, null, alcoholType);
        }

        // 쉼표가 있으면 복합 재료 검색
        if (keyword.contains(",")) {
            List<String> ingredients = Arrays.stream(keyword.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
            return new CocktailSearch(
                    SearchType.MULTIPLE_INGREDIENTS,
                    keyword,
                    ingredients,
                    alcoholType
            );
        }

        // 단일 검색 (칵테일 이름 또는 단일 재료)
        return new CocktailSearch(SearchType.SINGLE_KEYWORD, keyword, null, alcoholType);
    }

    // enum이라 차후에 외부로 뺄 예정
    public enum SearchType {
        NONE,                  // 검색어 없음
        SINGLE_KEYWORD,        // 단일 키워드 (칵테일 이름 또는 단일 재료)
        MULTIPLE_INGREDIENTS,  // 복수 재료
        ALCOHOL_ONLY          // 알코올 유무만 검색
    }

    public enum AlcoholType {
        ALCOHOLIC,      // 알코올
        NON_ALCOHOLIC   // 논알코올
    }
}