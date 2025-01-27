package org.toastit_v2.core.application.cocktail.trendcocktail.service.utilly;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class GoogleAPIRequestMaker {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper ;

    @Value("${api.google.key}")
    private String googleAPIKey;

    public GoogleAPIRequestMaker(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;

    }

    public Map<String,Object> createGoogleTrendAPIRequest(String keywords) {
        String url = String.format("https://serpapi.com/search?engine=google_trends&q=%s&api_key=%s",keywords, googleAPIKey);
        String jsonResponse = restTemplate.getForObject(url, String.class);
        try{
            return objectMapper.readValue(jsonResponse, Map.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Map.of("GOOGLE API error", e.getMessage());
        }
    }
}
