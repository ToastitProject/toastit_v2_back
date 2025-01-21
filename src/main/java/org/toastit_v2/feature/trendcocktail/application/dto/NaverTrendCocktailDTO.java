package org.toastit_v2.feature.trendcocktail.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.toastit_v2.feature.trendcocktail.domain.TrendCocktail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class NaverTrendCocktailDTO {
    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("timeUnit")
    private String timeUnit;

    @JsonProperty("results")
    private List<Result> results;

    @JsonCreator
    public NaverTrendCocktailDTO(@JsonProperty("startDate") String startDate,
                                 @JsonProperty("endDate") String endDate,
                                 @JsonProperty("timeUnit") String timeUnit,
                                 @JsonProperty("results") List<Result> results) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeUnit = timeUnit;
        this.results = results;
    }

    public TrendCocktail toDomain() {
        List<TrendCocktail.Result> domainResults = results.stream()
                .map(Result::toDomain)
                .collect(Collectors.toList());

        return TrendCocktail.builder()
                .startDate(startDate)
                .endDate(endDate)
                .timeUnit(timeUnit)
                .results(domainResults)
                .build();
    }

    @Getter
    @NoArgsConstructor
    public static class Result {
        @JsonProperty("title")
        private String title;

        @JsonProperty("keywords")
        private List<String> keywords;

        @JsonProperty("data")
        private List<Data> data;

        @JsonCreator
        public Result(@JsonProperty("title") String title,
                      @JsonProperty("keywords") List<String> keywords,
                      @JsonProperty("data") List<Data> data) {
            this.title = title;
            this.keywords = keywords;
            this.data = data;
        }

        public TrendCocktail.Result toDomain() {
            List<TrendCocktail.Result.Data> domainData = data.stream()
                    .map(Data::toDomain)
                    .collect(Collectors.toList());

            return TrendCocktail.Result.builder()
                    .title(title)
                    .keywords(keywords)
                    .data(domainData)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Data {
        @JsonProperty("period")
        private String period;

        @JsonProperty("ratio")
        private double ratio;

        @JsonCreator
        public Data(@JsonProperty("period") String period,
                    @JsonProperty("ratio") double ratio) {
            this.period = period;
            this.ratio = ratio;
        }

        public TrendCocktail.Result.Data toDomain() {
            return TrendCocktail.Result.Data.builder()
                    .period(period)
                    .ratio(ratio)
                    .build();
        }
    }

    public Map<String, List<Result>> getResultsByKeyword() {
        Map<String, List<Result>> keywordMap = new HashMap<>();
        for (Result result : results) {
            for (String keyword : result.getKeywords()) {
                keywordMap.computeIfAbsent(keyword, k -> new ArrayList<>()).add(result);
            }
        }
        return keywordMap;
    }
}
