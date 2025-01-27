package org.toastit_v2.core.application.cocktail.trendcocktail.port;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrendCocktailRepository {

    void save(List<String> cocktailList);

    String naverTrendAPIRequest(List<String> keywords);

    Map<String,Object> googleTrendAPIRequest(String keywords);

}
