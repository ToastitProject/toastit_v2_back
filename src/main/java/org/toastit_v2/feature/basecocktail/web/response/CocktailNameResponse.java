package org.toastit_v2.feature.basecocktail.web.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.toastit_v2.feature.basecocktail.domain.Cocktail;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CocktailNameResponse {
    private final String id;
    private final String name;

    @Builder
    private CocktailNameResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CocktailNameResponse from(Cocktail cocktail) {
        return CocktailNameResponse.builder()
                .id(cocktail.getId().toString())
                .name(cocktail.getStrDrink())
                .build();
    }
}