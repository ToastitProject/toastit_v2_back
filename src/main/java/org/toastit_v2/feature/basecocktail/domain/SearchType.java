package org.toastit_v2.feature.basecocktail.domain;

public enum SearchType {

    /**
     * SearchType
     */
    NONE,                  // 검색어 없음
    SINGLE_KEYWORD,        // 단일 키워드 (칵테일 이름 또는 단일 재료)
    MULTIPLE_INGREDIENTS,  // 복수 재료
    ALCOHOL_ONLY,          // 알코올 유무만 검색

}
