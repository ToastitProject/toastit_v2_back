package org.toastit_v2.core.infrastructure.persistence.cocktail.craftcocktail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.toastit_v2.core.domain.cocktail.craftcocktail.CraftCocktailReview;

import java.util.List;

public interface CraftCocktailReviewRepository extends JpaRepository<CraftCocktailReview, Long> {

    List<CraftCocktailReview> findByCraftCocktailId(Long craftCocktailId);

}
