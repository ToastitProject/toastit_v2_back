package org.toastit_v2.core.ui.cocktail.trendcocktail;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.toastit_v2.core.application.cocktail.trendcocktail.service.TrendCocktailService;

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
