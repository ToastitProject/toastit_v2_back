package org.toastit_v2.core.application.cocktail.basecocktail.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.toastit_v2.core.domain.cocktail.basecocktail.Cocktail;
import org.toastit_v2.core.domain.cocktail.basecocktail.CocktailCreate;
import org.bson.types.ObjectId;

import java.util.List;

public interface CocktailService {
    // 검색 기능
    Page<Cocktail> search(String keyword, Pageable pageable);

    // 상세 조회
    Cocktail getCocktailById(ObjectId id);

    // ID 목록으로 조회 (좋아요 목록 등에서 사용)
    List<Cocktail> getCocktailsByIds(List<ObjectId> ids);

    // 랜덤 조회
    List<Cocktail> getRandomCocktails(int count);

    // 전체 조회
    Page<Cocktail> getAllCocktails(Pageable pageable);

    // 이름만 조회
    List<Cocktail> getCocktailNames();

    // 칵테일 생성
    Cocktail createCocktail(CocktailCreate cocktailCreate);
}

