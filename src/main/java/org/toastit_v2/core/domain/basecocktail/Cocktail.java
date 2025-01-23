package org.toastit_v2.core.domain.basecocktail;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.Objects;

@Getter
public class Cocktail {
    private final ObjectId id;
    private final String strDrink;
    private final String strAlcoholic;
    private final String strCategory;
    private final String strGlass;
    private final String strIngredient1;
    private final String strIngredient2;
    private final String strIngredient3;
    private final String strIngredient4;
    private final String strIngredient5;
    private final String strIngredient6;
    private final String strIngredient7;
    private final String strIngredient8;
    private final String strIngredient9;
    private final String strIngredient10;
    private final String strIngredient11;
    private final String strInstructions;
    private final String strMeasure1;
    private final String strMeasure2;
    private final String strMeasure3;
    private final String strMeasure4;
    private final String strMeasure5;
    private final String strMeasure6;
    private final String strMeasure7;
    private final String strMeasure8;
    private final String strMeasure9;
    private final String strMeasure10;
    private final String strMeasure11;
    //     private final int likeCount;

    //        likeCount를 쓴다면 Integer likeCount를 추가
    @Builder
    public Cocktail(ObjectId id, String strDrink, String strAlcoholic, String strCategory, String strGlass,
                    String strIngredient1, String strIngredient2, String strIngredient3,
                    String strIngredient4, String strIngredient5, String strIngredient6,
                    String strIngredient7, String strIngredient8, String strIngredient9,
                    String strIngredient10, String strIngredient11,
                    String strInstructions,
                    String strMeasure1, String strMeasure2, String strMeasure3,
                    String strMeasure4, String strMeasure5, String strMeasure6,
                    String strMeasure7, String strMeasure8, String strMeasure9,
                    String strMeasure10, String strMeasure11
                    ) {

        this.id = id;
        this.strDrink = strDrink;
        this.strAlcoholic = strAlcoholic;
        this.strCategory = strCategory;
        this.strGlass = strGlass;
        this.strIngredient1 = strIngredient1;
        this.strIngredient2 = strIngredient2;
        this.strIngredient3 = strIngredient3;
        this.strIngredient4 = strIngredient4;
        this.strIngredient5 = strIngredient5;
        this.strIngredient6 = strIngredient6;
        this.strIngredient7 = strIngredient7;
        this.strIngredient8 = strIngredient8;
        this.strIngredient9 = strIngredient9;
        this.strIngredient10 = strIngredient10;
        this.strIngredient11 = strIngredient11;
        this.strInstructions = strInstructions;
        this.strMeasure1 = strMeasure1;
        this.strMeasure2 = strMeasure2;
        this.strMeasure3 = strMeasure3;
        this.strMeasure4 = strMeasure4;
        this.strMeasure5 = strMeasure5;
        this.strMeasure6 = strMeasure6;
        this.strMeasure7 = strMeasure7;
        this.strMeasure8 = strMeasure8;
        this.strMeasure9 = strMeasure9;
        this.strMeasure10 = strMeasure10;
        this.strMeasure11 = strMeasure11;
//        this.likeCount = likeCount != null ? likeCount : 0;
    }


    // equals랑 hashCode는 영속성 프레임워크에서 구현한다고 해서 구현함.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cocktail cocktail = (Cocktail) o;
        return Objects.equals(id, cocktail.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

