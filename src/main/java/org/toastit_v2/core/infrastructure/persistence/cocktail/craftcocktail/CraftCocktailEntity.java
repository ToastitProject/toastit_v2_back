package org.toastit_v2.core.infrastructure.persistence.cocktail.craftcocktail;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.toastit_v2.core.domain.cocktail.craftcocktail.CraftCocktail;
import org.toastit_v2.feature.user.infrastructure.persistence.mysql.UserEntity;

@Entity
@Builder
@AllArgsConstructor
@Table(name = "craftcocktails")
@NoArgsConstructor
public class CraftCocktailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String recipe;

    @Column(nullable = false)
    private String ingredients;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private boolean isDeleted = false;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public static org.toastit_v2.feature.craftcockatil.infrastructure.CraftCocktailEntity model(CraftCocktail craftCocktail) {
        return org.toastit_v2.feature.craftcockatil.infrastructure.CraftCocktailEntity.builder()
                .id(craftCocktail.getId())
                .title(craftCocktail.getTitle())
                .description(craftCocktail.getDescription())
                .recipe(craftCocktail.getRecipe())
                .ingredients(craftCocktail.getIngredients())
                .user(UserEntity.from(craftCocktail.getUser()))
                .isDeleted(craftCocktail.isDeleted())
                .build();
    }

    public CraftCocktail toModel() {
        return CraftCocktail.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .recipe(this.recipe)
                .ingredients(this.ingredients)
                .user(this.user.toModel())
                .build();
    }

}
