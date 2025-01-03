package org.toastit_v2.feature.craftcockatil.application.port;

import org.toastit_v2.feature.craftcockatil.domain.CraftCocktail;

import java.util.List;
import java.util.Optional;

public interface CraftCocktailRepository {

    Optional<CraftCocktail> findById(Long id);

    List<CraftCocktail> findAll();

    CraftCocktail save(CraftCocktail craftCocktail);

    CraftCocktail update(CraftCocktail craftCocktail);

    void deleteById(Long id);

}
