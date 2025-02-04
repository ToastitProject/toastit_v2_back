package org.toastit_v2.core.ui.cocktail.craftcocktail;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.toastit_v2.core.application.cocktail.craftcocktail.service.CraftCocktailService;
import org.toastit_v2.core.domain.cocktail.craftcocktail.CraftCocktail;

import java.util.List;

@Tag(
        name = "CraftCocktail",
        description = "레시피 입력 기능 API"
)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/craftcocktail")
public class CraftCocktailController {

    private final CraftCocktailService craftCocktailService;

    @Operation(
            summary = "Find Craft Cocktail by ID",
            description = "특정 ID로 칵테일 레시피를 조회합니다."
    )
    @GetMapping("/{id}")
    public ResponseEntity<CraftCocktail> getById(@PathVariable Long id) {
        CraftCocktail craftCocktail = craftCocktailService.findById(id);
        return ResponseEntity.ok(craftCocktail);
    }

    @Operation(summary = "Get All Craft Cocktails", description = "모든 칵테일 레시피를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CraftCocktail>> getAll() {
        List<CraftCocktail> craftCocktails = craftCocktailService.findAll();
        return ResponseEntity.ok(craftCocktails);
    }

    @Operation(summary = "Save a New Craft Cocktail", description = "새로운 칵테일 레시피를 저장합니다.")
    @PostMapping
    public ResponseEntity<CraftCocktail> save(@RequestBody @Validated CraftCocktail craftCocktail) {
        CraftCocktail savedCocktail = craftCocktailService.save(craftCocktail);
        return ResponseEntity.ok(savedCocktail);
    }

    @Operation(summary = "Update Craft Cocktail", description = "기존 칵테일 레시피를 업데이트합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<CraftCocktail> update(@PathVariable Long id, @RequestBody @Validated CraftCocktail craftCocktail) {
        craftCocktail = CraftCocktail.builder()
                .id(id)
                .title(craftCocktail.getTitle())
                .description(craftCocktail.getDescription())
                .recipe(craftCocktail.getRecipe())
                .ingredients(craftCocktail.getIngredients())
                .user(craftCocktail.getUser())
                .build();

        CraftCocktail updatedCocktail = craftCocktailService.update(craftCocktail);
        return ResponseEntity.ok(updatedCocktail);
    }

    @Operation(summary = "Delete Craft Cocktail", description = "특정 ID의 칵테일 레시피를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        craftCocktailService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
