package org.toastit_v2.core.application.cocktail.craftcocktail.port;

import org.toastit_v2.core.domain.cocktail.craftcocktail.CraftCocktail;

import java.util.List;
import java.util.Optional;

public interface CraftCocktailRepository {

    Optional<CraftCocktail> findById(Long id);

    List<CraftCocktail> findAll();

    CraftCocktail save(CraftCocktail craftCocktail);

    CraftCocktail update(CraftCocktail craftCocktail);

    void deleteById(Long id);

    void reportById(Long id);

}
