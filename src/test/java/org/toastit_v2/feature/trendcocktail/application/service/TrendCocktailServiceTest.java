package org.toastit_v2.feature.trendcocktail.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.toastit_v2.feature.trendcocktail.application.service.utilly.dateService;


import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TrendCocktailServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    private String clientId = "1Zias9yg8HHeEbbOHlZr";

    private String clientSecret = "fjtVzVcq88";
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
        headers.set("Content-Type", "application/json");
        return headers;
    }

    private String createRequestBody(List<String> sublist) {
        StringBuilder keywordGroupBuilder = new StringBuilder();
        keywordGroupBuilder.append("\"keywordGroups\":[");

        for (int j = 0; j < sublist.size(); j++) {
            keywordGroupBuilder.append(createKeywordGroup(sublist.get(j)));
            if (j < sublist.size() - 1) {
                keywordGroupBuilder.append(",");
            }
        }
        keywordGroupBuilder.append("]");
        return String.format("{\"startDate\":\"%s\",\"endDate\":\"%s\",\"timeUnit\":\"month\",%s}",
                dateService.firstDayOfTwoMonthsAgo(),
                dateService.lastDayOfLastMonth(),
                keywordGroupBuilder.toString());
    }

    private String createKeywordGroup(String keyword) {
        return String.format("{\"groupName\":\"%s\",\"keywords\":[\"%s\"]}", keyword, keyword);
    }

    private ResponseEntity<String> sendRequest(String url, HttpHeaders headers, String requestBody) {
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }
    @Test
    @DisplayName("요청문 생성 테스트")
    void naverRequest() {
        List<String> keywords = new ArrayList<>();
        keywords.add("모히또");
        keywords.add("마티니");
        String url = "https://openapi.naver.com/v1/datalab/search"; // API URL (네이버 데이터랩 트렌드)
        HttpHeaders headers = createHeaders();
        StringBuilder finalResponse = new StringBuilder();
        for (int i = 0; i < keywords.size(); i += 5) {
            List<String> sublist = keywords.subList(i, Math.min(i + 5, keywords.size()));
            String requestBody = createRequestBody(sublist);
            ResponseEntity<String> response = sendRequest(url, headers, requestBody);
            finalResponse.append(response.getBody());
            if (i + 5 < keywords.size()) {
                finalResponse.append("||");
            }
        }
        System.out.println(finalResponse.toString());
    }
}