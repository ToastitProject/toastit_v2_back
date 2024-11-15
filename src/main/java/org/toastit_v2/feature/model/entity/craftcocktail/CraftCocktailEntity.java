package org.toastit_v2.feature.model.entity.craftcocktail;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.toastit_v2.common.entity.AuditingFields;
import org.toastit_v2.feature.domain.craftcocktail.CraftCocktail;
import org.toastit_v2.feature.domain.user.User;

@Entity
@Builder
@AllArgsConstructor
@Table(name = "craftcocktails")
@NoArgsConstructor
public class CraftCocktailEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String recipe;

    public static CraftCocktailEntity fromModel(CraftCocktail craftCocktail) {
        return CraftCocktailEntity.builder()
                .id(craftCocktail.getId())
                .title(craftCocktail.getTitle())
                .description(craftCocktail.getDescription())
                .recipe(craftCocktail.getRecipe())
                .build();
    }

    public CraftCocktail toModel() {
        return CraftCocktail.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .recipe(this.recipe)
                .build();
    }

}
