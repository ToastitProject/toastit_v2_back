package org.toastit_v2.feature.craftcockatil.application.service;

import org.toastit_v2.feature.craftcockatil.domain.CraftCocktail;

import java.util.List;

public interface CraftCocktailService {

    CraftCocktail findById(Long id);

    List<CraftCocktail> findAll();

    CraftCocktail save(CraftCocktail craftCocktail);

    CraftCocktail update(CraftCocktail craftCocktail);

    void deleteById(Long id);
}
