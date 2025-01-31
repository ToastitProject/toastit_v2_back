package org.toastit_v2.core.application.cocktail.craftcocktail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.toastit_v2.common.exception.custom.CustomCraftCocktailException;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.core.application.cocktail.craftcocktail.port.CraftCocktailRepository;
import org.toastit_v2.core.domain.cocktail.craftcocktail.CraftCocktail;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CraftCocktailServiceImpl implements CraftCocktailService {

    private final CraftCocktailRepository craftCocktailRepository;

    public CraftCocktail findById(Long id) {
        return craftCocktailRepository.findById(id)
                .orElseThrow(() -> new CustomCraftCocktailException(ExceptionCode.FAIL_CREATE_CRAFT_COCKTAIL));
    }

    public List<CraftCocktail> findAll() {
        return craftCocktailRepository.findAll();
    }

    public CraftCocktail save(CraftCocktail craftCocktail) {
        return craftCocktailRepository.save(craftCocktail);
    }

    public CraftCocktail update(CraftCocktail craftCocktail) {
        craftCocktailRepository.findById(craftCocktail.getId())
                .orElseThrow(() -> new CustomCraftCocktailException(ExceptionCode.USER_NOT_FOUND));

        return craftCocktailRepository.update(craftCocktail);
    }

    public void deleteById(Long id) {
        craftCocktailRepository.findById(id)
                .orElseThrow(() -> new CustomCraftCocktailException(ExceptionCode.USER_NOT_FOUND));

        craftCocktailRepository.deleteById(id);
    }

}
