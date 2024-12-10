package org.toastit_v2.feature.trendcocktail.web;

import org.toastit_v2.feature.trendcocktail.application.service.TrendCocktailService;

public class Controller {

    private final TrendCocktailService trendCocktailService;

    public Controller(TrendCocktailService trendCocktailService) {
        this.trendCocktailService = trendCocktailService;
    }

}
