package org.toastit_v2.core.infrastructure.persistence.cocktail.basecocktail.mongodb.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.toastit_v2.core.infrastructure.persistence.cocktail.basecocktail.mongodb.CocktailDocument;
import org.toastit_v2.core.domain.cocktail.basecocktail.CocktailSearch;
import java.util.List;

public interface CustomCocktailRepository {

    Page<CocktailDocument> search(CocktailSearch searchCondition, Pageable pageable);

    List<CocktailDocument> findRandom(int count);

    List<CocktailDocument> findAllNames();

}
