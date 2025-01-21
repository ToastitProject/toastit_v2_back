package org.toastit_v2.feature.trendcocktail.web;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.toastit_v2.feature.trendcocktail.application.dto.NaverTrendCocktailDTO;
import org.toastit_v2.feature.trendcocktail.application.service.TrendCocktailService;
import org.toastit_v2.feature.trendcocktail.domain.TrendCocktail;
import org.toastit_v2.feature.trendcocktail.web.response.TrendCocktailResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class TrendCocktailController {

    private final TrendCocktailService trendCocktailService;

    public TrendCocktailController(TrendCocktailService trendCocktailService) {
        this.trendCocktailService = trendCocktailService;
    }

    @PostMapping("/test/google/trendCocktail")
    public Map<String, Object> getGoogleTrend(@RequestParam String keyword) {
        return trendCocktailService.googleTrendAPIRequest(keyword);
    }


    @PostMapping("/test/naver/TrendCocktail")
    public String trendCocktailSearchResult() {
        List<String> cocktailList = new ArrayList<>();
        cocktailList.add("모히또");
        cocktailList.add("마티니");

        return trendCocktailService.naverTrendAPIRequest(cocktailList);
    }
}
