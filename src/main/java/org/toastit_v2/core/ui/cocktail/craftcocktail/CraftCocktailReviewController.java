package org.toastit_v2.core.ui.cocktail.craftcocktail;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.toastit_v2.core.application.cocktail.craftcocktail.service.CraftCocktailReviewService;
import org.toastit_v2.core.domain.cocktail.craftcocktail.CraftCocktailReview;

import java.util.List;

@Tag(name = "CraftCocktailReview", description = "칵테일 레시피 댓글 기능 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/craftcocktail")
public class CraftCocktailReviewController {

    private final CraftCocktailReviewService reviewService;

    @Operation(summary = "Add Review to Craft Cocktail", description = "칵테일 레시피에 댓글을 추가합니다.")
    @PostMapping("/{id}/reviews")
    public ResponseEntity<CraftCocktailReview> addReview(@PathVariable Long id, @RequestBody @Validated CraftCocktailReview review) {
        CraftCocktailReview saveReview = reviewService.saveReview(id, review);
        return ResponseEntity.ok(saveReview);
    }

    @Operation(summary = "Update Review", description = "댓글을 수정합니다.")
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<CraftCocktailReview> updateReview(@PathVariable Long reviewId, @RequestBody @Validated CraftCocktailReview review) {
        CraftCocktailReview updateReview = reviewService.updateReview(reviewId, review);
        return ResponseEntity.ok(updateReview);
    }

    @Operation(summary = "Delete Review", description = "댓글을 삭제합니다.")
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get Reviews by Craft Cocktail ID", description = "특정 칵테일 레시피의 댓글 목록을 조회합니다.")
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<CraftCocktailReview>> getReviews(@PathVariable Long id) {
        List<CraftCocktailReview> reviews = reviewService.getReviewsByCraftCocktailId(id);
        return ResponseEntity.ok(reviews);
    }

}
