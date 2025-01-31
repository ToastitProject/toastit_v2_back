package org.toastit_v2.core.domain.cocktail.craftcocktail;

import lombok.Builder;
import lombok.Getter;
import org.toastit_v2.core.domain.member.Member;
import org.toastit_v2.core.infrastructure.persistence.cocktail.craftcocktail.CraftCocktailEntity;

@Getter
public class CraftCocktail {

    private final Long id;

    private final String title;

    private final String description;

    private final String recipe;

    private final String ingredients;

    private final Member user;

    private final boolean isDeleted;

    @Builder
    public CraftCocktail(Long id, String title, String description, String recipe, String ingredients, Member user, boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.user = user;
        this.isDeleted = isDeleted;
    }

    public CraftCocktailEntity toEntity() {
        return CraftCocktailEntity.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .recipe(this.recipe)
                .ingredients(this.ingredients)
                .user(UserEntity.from(this.user))
                .isDeleted(this.isDeleted)
                .build();
    }

}
