package org.toastit_v2.feature.basecocktail.application.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.toastit_v2.core.common.application.code.CommonExceptionCode;
import org.toastit_v2.core.common.application.exception.RestApiException;
import org.toastit_v2.feature.basecocktail.application.port.CocktailRepository;
import org.toastit_v2.feature.basecocktail.domain.Cocktail;
import org.toastit_v2.feature.basecocktail.domain.CocktailSearch;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CocktailServiceImpl implements CocktailService {

    private final CocktailRepository cocktailRepository;

    @Override
    public Page<Cocktail> search(String keyword, Pageable pageable) {
        try {
            CocktailSearch searchCondition = CocktailSearch.from(keyword);
            return cocktailRepository.search(searchCondition, pageable);
        } catch (Exception e) {
            throw new RestApiException(CommonExceptionCode.INVALID_COCKTAIL_SEARCH);
        }
    }
    @Override
    public Cocktail getCocktailById(ObjectId id) {
        return cocktailRepository.findById(id)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_COCKTAIL));
    }

    @Override
    public List<Cocktail> getCocktailsByIds(List<ObjectId> ids) {
        List<Cocktail> cocktails = cocktailRepository.findByIdIn(ids);
        if (cocktails.isEmpty()) {
            throw new RestApiException(CommonExceptionCode.NOT_FOUND_COCKTAIL);
        }
        return cocktails;
    }

    @Override
    public List<Cocktail> getRandomCocktails(int count) {
        if (count <= 0) {
            throw new RestApiException(CommonExceptionCode.INVALID_COCKTAIL_COUNT);
        }
        return cocktailRepository.findRandom(count);
    }

    @Override
    public Page<Cocktail> getAllCocktails(Pageable pageable) {
        Page<Cocktail> cocktails = cocktailRepository.findAll(pageable);
        // 만약 페이징 처리후에 없으면
        if (cocktails.isEmpty()) {
            throw new RestApiException(CommonExceptionCode.NOT_FOUND_COCKTAIL);
        }
        return cocktails;
    }
}
