package org.toastit_v2.core.application.basecocktail.port;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.toastit_v2.feature.basecocktail.domain.Cocktail;
import org.toastit_v2.feature.basecocktail.domain.CocktailSearch;

import java.util.List;
import java.util.Optional;

public interface CocktailRepository {
    // 검색 관련
    Page<Cocktail> search(CocktailSearch searchCondition, Pageable pageable);

    // ID 기반 조회
    Optional<Cocktail> findById(ObjectId id);
    List<Cocktail> findByIdIn(List<ObjectId> ids);

    // 랜덤 조회
    List<Cocktail> findRandom(int count);

    // 전체 조회
    Page<Cocktail> findAll(Pageable pageable);

    // 이름만 조회
    List<Cocktail> findAllNames();

    // 칵테일 추가
    Cocktail save(Cocktail cocktail);
}

