package org.toastit_v2.core.application.cocktail.basecocktail.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.toastit_v2.common.exception.custom.CustomBaseCocktailException;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.core.application.cocktail.basecocktail.port.CocktailRepository;
import org.toastit_v2.core.domain.cocktail.basecocktail.Cocktail;
import org.toastit_v2.core.domain.cocktail.basecocktail.CocktailSearch;
import org.toastit_v2.core.domain.cocktail.basecocktail.CocktailCreate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CocktailServiceImpl implements CocktailService {

    private final CocktailRepository cocktailRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Cocktail> search(String keyword, Pageable pageable) {
        final Page<Cocktail> searchResults = cocktailRepository.search(CocktailSearch.from(keyword), pageable);
        if(searchResults.isEmpty()) {
            throw new CustomBaseCocktailException((ExceptionCode.NOT_FOUND_COCKTAIL));
        }
        return searchResults;
    }

    @Override
    @Transactional(readOnly = true)
    public Cocktail getCocktailById(final ObjectId id) {
        return cocktailRepository.findById(id)
                .orElseThrow(() -> new CustomBaseCocktailException(ExceptionCode.NOT_FOUND_COCKTAIL));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cocktail> getCocktailsByIds(final List<ObjectId> ids) {
        final List<Cocktail> cocktails = cocktailRepository.findByIdIn(ids);
        if (cocktails.isEmpty()) {
            throw new CustomBaseCocktailException(ExceptionCode.NOT_FOUND_COCKTAIL);
        }
        return cocktails;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cocktail> getRandomCocktails(final int count) {
        if (count <= 0) {
            throw new CustomBaseCocktailException(ExceptionCode.INVALID_COCKTAIL_COUNT);
        }
        return cocktailRepository.findRandom(count);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cocktail> getAllCocktails(final Pageable pageable) {
        final Page<Cocktail> cocktails = cocktailRepository.findAll(pageable);
        if (cocktails.isEmpty()) {
            throw new CustomBaseCocktailException(ExceptionCode.NOT_FOUND_COCKTAIL);
        }
        return cocktails;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cocktail> getCocktailNames() {
        return cocktailRepository.findAllNames();
    }

    @Override
    @Transactional
    public Cocktail createCocktail(final CocktailCreate cocktailCreate) {
        final Cocktail cocktail = cocktailCreate.toDomain();
        return cocktailRepository.save(cocktail);
    }
}

