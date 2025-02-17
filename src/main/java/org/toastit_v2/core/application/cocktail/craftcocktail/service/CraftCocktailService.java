package org.toastit_v2.core.application.cocktail.craftcocktail.service;

import org.toastit_v2.core.domain.cocktail.craftcocktail.CraftCocktail;

import java.util.List;

public interface CraftCocktailService {

    CraftCocktail findById(Long id);

    List<CraftCocktail> findAll();

    CraftCocktail save(CraftCocktail craftCocktail);

    CraftCocktail update(CraftCocktail craftCocktail);

    void deleteById(Long id);

    void reportById(Long id);
}
