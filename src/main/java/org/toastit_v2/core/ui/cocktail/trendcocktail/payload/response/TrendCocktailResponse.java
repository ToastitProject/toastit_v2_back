package org.toastit_v2.core.ui.cocktail.trendcocktail.payload.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.toastit_v2.core.application.cocktail.trendcocktail.dto.NaverTrendCocktailDTO;

import java.io.IOException;

public class TrendCocktailResponse  {

    public static NaverTrendCocktailDTO fromNaverResponse(String naverResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(naverResponse, NaverTrendCocktailDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
