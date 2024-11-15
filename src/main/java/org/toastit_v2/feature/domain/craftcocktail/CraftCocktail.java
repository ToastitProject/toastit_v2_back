package org.toastit_v2.feature.domain.craftcocktail;

import lombok.Builder;
import lombok.Getter;
import org.toastit_v2.feature.model.entity.craftcocktail.CraftCocktailEntity;

@Getter
public class CraftCocktail {

    private final Long id;

    private final String title;

    private String description;

    private String recipe;

    @Builder
    public CraftCocktail(Long id, String title, String description, String recipe) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.recipe = recipe;
    }

    public CraftCocktailEntity toEntity() {
        return CraftCocktailEntity.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .recipe(this.recipe)
                .build();
    }

}
