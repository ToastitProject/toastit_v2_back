package org.toastit_v2.feature.trendcocktail.web;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.toastit_v2.feature.trendcocktail.application.dto.TrendCocktailDTO;
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

    @PostMapping("/test/trendCocktail")
    public String  trendCocktailSearchResult(){
        List<String> cocktailList = new ArrayList<>();
        cocktailList.add("모히또");
        cocktailList.add("마티니");

        return trendCocktailService.naverRequest(cocktailList);
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void scheduledTask() {
        List<String> cocktailList = new ArrayList<>();
        cocktailList.add("모히또");
        cocktailList.add("마티니");

        String naverResponse = trendCocktailService.naverRequest(cocktailList);
        TrendCocktailDTO trendCocktailDTO = TrendCocktailResponse.fromNaverResponse(naverResponse);
        Map<String, List<TrendCocktailDTO.Result>> resultsByKeyword = trendCocktailDTO.getResultsByKeyword();
        List<String> topFiveKeywords = TrendCocktail.getTopFiveKeywords(resultsByKeyword);
        trendCocktailService.save(topFiveKeywords);}
}
