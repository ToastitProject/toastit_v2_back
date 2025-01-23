package org.toastit_v2.core.domain.basecocktail;

import lombok.Builder;
import lombok.Getter;
import org.toastit_v2.feature.basecocktail.domain.AlcoholType;
import org.toastit_v2.feature.basecocktail.domain.SearchType;

import java.util.Arrays;
import java.util.List;

@Getter
public class CocktailSearch {
    private final SearchType searchType;    // 검색 종류
    private final String keyword;           // 원본 검색 키워드
    private final List<String> ingredients; // 쉼표로 구분된 재료들
    private final org.toastit_v2.feature.basecocktail.domain.AlcoholType alcoholType;  // 알코올 유무

    @Builder
    private CocktailSearch(SearchType searchType, String keyword, List<String> ingredients, org.toastit_v2.feature.basecocktail.domain.AlcoholType alcoholType) {
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
        org.toastit_v2.feature.basecocktail.domain.AlcoholType alcoholType = null;

        // 무알콜/논알콜 체크
        if (containsAny(keyword, "무알콜", "무알코올", "논알콜", "논알코올")) {
            alcoholType = org.toastit_v2.feature.basecocktail.domain.AlcoholType.NON_ALCOHOLIC;
            keyword = removeWords(keyword, "무알콜", "무알코올", "논알콜", "논알코올");
        }
        // 알코올 체크
        else if (containsAny(keyword, "알코올", "알콜")) {
            alcoholType = AlcoholType.ALCOHOLIC;
            keyword = removeWords(keyword, "알코올", "알콜");
        }

        // 키워드가 알코올 타입만 있었다면...
        if (keyword.trim().isEmpty()) {
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

    // 여러 단어 중 하나라도 포함되는지 체크
    private static boolean containsAny(String text, String... words) {
        return Arrays.stream(words)
                .anyMatch(text::contains);
    }

    // 주어진 단어들을 모두 제거하고 정리
    private static String removeWords(String text, String... words) {
        String result = text;
        for (String word : words) {
            result = result.replace(word, "");
        }
        return result.trim().replaceAll("\\s+", " "); // 중복된 공백 제거
    }
}

