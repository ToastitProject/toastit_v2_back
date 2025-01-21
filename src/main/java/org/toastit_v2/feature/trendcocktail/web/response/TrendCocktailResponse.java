package org.toastit_v2.feature.trendcocktail.web.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.toastit_v2.feature.trendcocktail.application.dto.NaverTrendCocktailDTO;

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
