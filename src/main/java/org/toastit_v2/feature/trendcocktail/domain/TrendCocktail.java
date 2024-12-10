package org.toastit_v2.feature.trendcocktail.domain;

import lombok.Builder;
import lombok.Getter;
import org.toastit_v2.feature.trendcocktail.application.dto.TrendCocktailDTO;
import org.toastit_v2.feature.trendcocktail.infrastructure.persistence.mysql.entity.TrendCocktailEntity;

import java.util.*;
import java.util.stream.Collectors;

@Builder
@Getter
public class TrendCocktail {
    private String startDate;
    private String endDate;
    private String timeUnit;
    private List<Result> results;

    @Getter
    @Builder
    public static class Result {
        private String title;
        private List<String> keywords;
        private List<Data> data;

        @Getter
        @Builder
        public static class Data {
            private String period;
            private double ratio;
        }
    }

    public static List<String> getTopFiveKeywords(Map<String, List<TrendCocktailDTO.Result>> keywordMap) {
        List<Map.Entry<String, Double>> keywordDifferences = new ArrayList<>();
        for (Map.Entry<String, List<TrendCocktailDTO.Result>> entry : keywordMap.entrySet()) {
            String keyword = entry.getKey();
            List<TrendCocktailDTO.Result> results = entry.getValue();
            double maxDifference = calculateMaxDifference(results);
            if (maxDifference > 0) {
                keywordDifferences.add(new AbstractMap.SimpleEntry<>(keyword, maxDifference));
            }
        }
        return getTopKeywords(keywordDifferences);
    }

    private static double calculateMaxDifference(List<TrendCocktailDTO.Result> results) {
        if (results == null || results.isEmpty()) {
            return 0;
        }
        return results.stream()
                .mapToDouble(result -> calculateRatioDifference(result))
                .max()
                .orElse(0);
    }

    private static double calculateRatioDifference(TrendCocktailDTO.Result result) {
        if (result.getData().size() > 1) {
            double ratio1 = result.getData().get(0).getRatio();
            double ratio2 = result.getData().get(1).getRatio();
            return ratio1 - ratio2;
        }
        return 0;
    }

    private static List<String> getTopKeywords(List<Map.Entry<String, Double>> keywordDifferences) {
        keywordDifferences.sort((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));
        return keywordDifferences.stream()
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static List<TrendCocktailEntity> convertToEntities(List<String> keywords) {
        return keywords.stream()
                .map(keyword -> {
                    TrendCocktailEntity entity = new TrendCocktailEntity();
                    entity.setName(keyword);
                    return entity;
                })
                .collect(Collectors.toList());
    }
}
