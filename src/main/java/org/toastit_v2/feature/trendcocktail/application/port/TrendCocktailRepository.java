package org.toastit_v2.feature.trendcocktail.application.port;

import org.springframework.stereotype.Repository;
import org.toastit_v2.feature.trendcocktail.application.dto.TrendCocktailDTO;

import java.util.List;

@Repository
public interface TrendCocktailRepository {

    void save(List<String> cocktailList);

    String naverRequest(List<String> keywords);

}
