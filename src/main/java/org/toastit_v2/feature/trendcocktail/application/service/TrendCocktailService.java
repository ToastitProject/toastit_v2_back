package org.toastit_v2.feature.trendcocktail.application.service;

import org.springframework.stereotype.Service;
import org.toastit_v2.feature.trendcocktail.application.port.TrendCocktailRepository;
import org.toastit_v2.feature.trendcocktail.application.service.utilly.GoogleAPIRequestMaker;
import org.toastit_v2.feature.trendcocktail.application.service.utilly.NaverAPIRequestMaker;
import org.toastit_v2.feature.trendcocktail.infrastructure.persistence.mysql.custom.JPATrendCocktailRepository;
import java.util.List;
import java.util.Map;

@Service
public class TrendCocktailService implements TrendCocktailRepository {

    private final JPATrendCocktailRepository jpaRepository;
    private final GoogleAPIRequestMaker googleAPIRequestMaker;
    private final NaverAPIRequestMaker naverAPIRequestMaker;

    public TrendCocktailService(JPATrendCocktailRepository jpaRepository, GoogleAPIRequestMaker googleAPIRequestMaker, NaverAPIRequestMaker naverAPIRequestMaker) {
        this.jpaRepository = jpaRepository;
        this.googleAPIRequestMaker = googleAPIRequestMaker;
        this.naverAPIRequestMaker = naverAPIRequestMaker;
    }

    /**
     * String 타입의 List 를 DB 에 저장하는 메서드 입니다.
     * API 를 통해 얻어온 칵테일을 List 자료 구조에 저장하고 이를 DB에 저장할 때 사용합니다.
     * @param cocktailList
     */
    @Override
    public void save(List<String> cocktailList) {
        jpaRepository.save(cocktailList);
    }

    /**
     * Naver DataLaB 에 키워드들의 검색량을 응답받는 요청문을 전송하는 메서드 입니다.
     * @param keywords : Project 에서 제공하는 칵테일 리스트를 입력받을 수 있습니다.
     * @return 요청문을 반환 합니다.
     */
    @Override
    public String naverTrendAPIRequest(List<String> keywords) {
        return naverAPIRequestMaker.createNaverTrendAPIRequest(keywords);
    }

    /**
     * Google Trend API 를 통해 특정 키워드의 검색량을 응답받는 요청문을 전송하는 메서드 입니다.
     * @param keywords : 검색량을 응답받고 싶은 키워드를 입력합니다.
     * @return : 요청문을 반환합니다.
     */
    @Override
    public Map<String,Object> googleTrendAPIRequest(String keywords) {
       return googleAPIRequestMaker.createGoogleTrendAPIRequest(keywords);
    }
}
