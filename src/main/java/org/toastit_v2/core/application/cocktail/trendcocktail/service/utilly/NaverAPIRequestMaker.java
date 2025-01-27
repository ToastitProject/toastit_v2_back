package org.toastit_v2.core.application.cocktail.trendcocktail.service.utilly;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Component
public class NaverAPIRequestMaker {

    private RestTemplate restTemplate;

    public NaverAPIRequestMaker(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${api.naver.client-id}")
    private String clientId;

    @Value("${api.naver.client-secret}")
    private String clientSecret;

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
                DateCalculator.firstDayOfTwoMonthsAgo(),
                DateCalculator.lastDayOfLastMonth(),
                keywordGroupBuilder.toString());
    }

    private String createKeywordGroup(String keyword) {
        return String.format("{\"groupName\":\"%s\",\"keywords\":[\"%s\"]}", keyword, keyword);
    }

    private ResponseEntity<String> sendRequest(String url, HttpHeaders headers, String requestBody) {
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    public String createNaverTrendAPIRequest(List<String> keywords) {
        String url = "https://openapi.naver.com/v1/datalab/search";
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
        return finalResponse.toString();
    }
}
