package org.toastit_v2.feature.trendcocktail.web.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.toastit_v2.feature.trendcocktail.application.dto.TrendCocktailDTO;

import java.io.IOException;

public class TrendCocktailResponse  {

    public static TrendCocktailDTO fromNaverResponse(String naverResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(naverResponse, TrendCocktailDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
