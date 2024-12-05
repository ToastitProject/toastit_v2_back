package org.toastit_v2.feature.basecocktail.web.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.toastit_v2.feature.basecocktail.domain.Cocktail;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CocktailResponse {
    private final String id;
    private final String name;
    private final String alcoholic;
    private final String category;
    private final String glass;
    private final String ingredient1;
    private final String ingredient2;
    private final String ingredient3;
    private final String ingredient4;
    private final String ingredient5;
    private final String ingredient6;
    private final String ingredient7;
    private final String ingredient8;
    private final String ingredient9;
    private final String ingredient10;
    private final String ingredient11;
    private final String instructions;
    private final String measure1;
    private final String measure2;
    private final String measure3;
    private final String measure4;
    private final String measure5;
    private final String measure6;
    private final String measure7;
    private final String measure8;
    private final String measure9;
    private final String measure10;
    private final String measure11;
//    private final String imagePath;
//    private final int likeCount;

    @Builder
    private CocktailResponse(String id, String name, String alcoholic,
                             String category, String glass,
                             String ingredient1, String ingredient2, String ingredient3,
                             String ingredient4, String ingredient5, String ingredient6,
                             String ingredient7, String ingredient8, String ingredient9,
                             String ingredient10, String ingredient11,
                             String instructions,
                             String measure1, String measure2, String measure3,
                             String measure4, String measure5, String measure6,
                             String measure7, String measure8, String measure9,
                             String measure10, String measure11) {
        this.id = id;
        this.name = name;
        this.alcoholic = alcoholic;
        this.category = category;
        this.glass = glass;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.ingredient3 = ingredient3;
        this.ingredient4 = ingredient4;
        this.ingredient5 = ingredient5;
        this.ingredient6 = ingredient6;
        this.ingredient7 = ingredient7;
        this.ingredient8 = ingredient8;
        this.ingredient9 = ingredient9;
        this.ingredient10 = ingredient10;
        this.ingredient11 = ingredient11;
        this.instructions = instructions;
        this.measure1 = measure1;
        this.measure2 = measure2;
        this.measure3 = measure3;
        this.measure4 = measure4;
        this.measure5 = measure5;
        this.measure6 = measure6;
        this.measure7 = measure7;
        this.measure8 = measure8;
        this.measure9 = measure9;
        this.measure10 = measure10;
        this.measure11 = measure11;
//        this.imagePath = imagePath;
//        this.likeCount = likeCount;
    }

    public static CocktailResponse from(Cocktail cocktail) {
        return CocktailResponse.builder()
                .id(cocktail.getId().toString())
                .name(cocktail.getStrDrink())
                .alcoholic(cocktail.getStrAlcoholic())
                .category(cocktail.getStrCategory())
                .glass(cocktail.getStrGlass())
                .ingredient1(cocktail.getStrIngredient1())
                .ingredient2(cocktail.getStrIngredient2())
                .ingredient3(cocktail.getStrIngredient3())
                .ingredient4(cocktail.getStrIngredient4())
                .ingredient5(cocktail.getStrIngredient5())
                .ingredient6(cocktail.getStrIngredient6())
                .instructions(cocktail.getStrInstructions())
                .measure1(cocktail.getStrMeasure1())
                .measure2(cocktail.getStrMeasure2())
                .measure3(cocktail.getStrMeasure3())
                .measure4(cocktail.getStrMeasure4())
                .measure5(cocktail.getStrMeasure5())
                .measure6(cocktail.getStrMeasure6())
//                .imagePath(cocktail.getImagePath())
//                .likeCount(cocktail.getLikeCount())
                .build();
    }
}