package org.toastit_v2.core.application.cocktail.craftcocktail.service;

import org.toastit_v2.core.domain.cocktail.craftcocktail.CraftCocktailReview;

import java.util.List;

public interface CraftCocktailReviewService {

    CraftCocktailReview saveReview(Long craftCocktailId, CraftCocktailReview review);

    CraftCocktailReview updateReview(Long reviewId, CraftCocktailReview review);

    void deleteReview(Long commentId);

    List<CraftCocktailReview> getReviewsByCraftCocktailId(Long craftCocktailId);

}
