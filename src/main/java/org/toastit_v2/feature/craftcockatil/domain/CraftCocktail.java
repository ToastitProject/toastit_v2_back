package org.toastit_v2.feature.craftcockatil.domain;

import lombok.Builder;
import lombok.Getter;
import org.toastit_v2.feature.craftcockatil.infrastructure.CraftCocktailEntity;
import org.toastit_v2.feature.user.domain.User;
import org.toastit_v2.feature.user.infrastructure.persistence.mysql.UserEntity;

@Getter
public class CraftCocktail {

    private final Long id;

    private final String title;

    private final String description;

    private final String recipe;

    private final String ingredients;

    private final User user;

    @Builder
    public CraftCocktail(Long id, String title, String description, String recipe, String ingredients, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.user = user;
    }

    public CraftCocktailEntity toEntity() {
        return CraftCocktailEntity.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .recipe(this.recipe)
                .ingredients(this.ingredients)
                .user(UserEntity.from(this.user))
                .build();
    }

}
