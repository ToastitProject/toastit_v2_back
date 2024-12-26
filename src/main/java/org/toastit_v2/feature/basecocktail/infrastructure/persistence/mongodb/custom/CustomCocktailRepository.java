package org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb.CocktailDocument;
import org.toastit_v2.feature.basecocktail.domain.CocktailSearch;

import java.util.List;

public interface CustomCocktailRepository {

    // 검색 조건에 따른 칵테일 검색 (페이징)
    Page<CocktailDocument> search(CocktailSearch searchCondition, Pageable pageable);

    // 랜덤 칵테일 조회
    List<CocktailDocument> findRandom(int count);
}

