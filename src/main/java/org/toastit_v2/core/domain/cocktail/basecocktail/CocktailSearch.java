package org.toastit_v2.core.domain.cocktail.basecocktail;

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

    public static CocktailSearch from(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return new CocktailSearch(SearchType.NONE, null, null, null);
        }

        AlcoholType alcoholType = null;

        if (containsAny(keyword, "무알콜", "무알코올", "논알콜", "논알코올")) {
            alcoholType = AlcoholType.NON_ALCOHOLIC;
            keyword = removeWords(keyword, "무알콜", "무알코올", "논알콜", "논알코올");
        }

        else if (containsAny(keyword, "알코올", "알콜")) {
            alcoholType = AlcoholType.ALCOHOLIC;
            keyword = removeWords(keyword, "알코올", "알콜");
        }

        if (keyword.trim().isEmpty()) {
            return new CocktailSearch(SearchType.ALCOHOL_ONLY, null, null, alcoholType);
        }

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
        return new CocktailSearch(SearchType.SINGLE_KEYWORD, keyword, null, alcoholType);
    }

    private static boolean containsAny(String text, String... words) {
        return Arrays.stream(words)
                .anyMatch(text::contains);
    }

    private static String removeWords(String text, String... words) {
        String result = text;
        for (String word : words) {
            result = result.replace(word, "");
        }
        return result.trim().replaceAll("\\s+", " "); // 중복된 공백 제거
    }

}
