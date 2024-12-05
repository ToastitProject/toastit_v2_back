package org.toastit_v2.feature.basecocktail.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.toastit_v2.feature.basecocktail.domain.Cocktail;
import org.toastit_v2.feature.basecocktail.domain.CocktailSearch;
import org.bson.types.ObjectId;

import java.util.List;

public interface CocktailService {
    // 검색 기능
    Page<Cocktail> search(String keyword, Pageable pageable);

    // 상세 조회
    Cocktail getCocktailById(ObjectId id);

    // ID 목록으로 조회 (좋아요 목록 등에서 사용)
//    List<Cocktail> getCocktailsByIds(List<ObjectId> ids);

    // 랜덤 조회
    List<Cocktail> getRandomCocktails(int count);
}