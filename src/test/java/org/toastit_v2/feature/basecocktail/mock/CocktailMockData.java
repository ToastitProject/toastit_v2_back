package org.toastit_v2.feature.basecocktail.mock;

import org.bson.types.ObjectId;
import org.toastit_v2.feature.basecocktail.domain.Cocktail;

public class CocktailMockData {

    public static Cocktail mojito = Cocktail.builder()
            .id(new ObjectId("507f1f77bcf86cd799439011"))
            .strDrink("모히토")
            .strAlcoholic("알코올")
            .strCategory("칵테일")
            .strGlass("하이볼 글라스")
            .strIngredient1("화이트 럼")
            .strIngredient2("라임")
            .strIngredient3("설탕")
            .strIngredient4("민트")
            .strIngredient5("탄산수")
            .strInstructions("라임과 설탕을 섞어 라임시럽을 만들고, 민트를 넣어 향을 우려낸 후 럼과 탄산수를 넣어주세요.")
            .strMeasure1("45ml")
            .strMeasure2("1개")
            .strMeasure3("2티스푼")
            .strMeasure4("4-5잎")
            .strMeasure5("적당량")
            .build();

    public static Cocktail ginTonic = Cocktail.builder()
            .id(new ObjectId("507f1f77bcf86cd799439012"))
            .strDrink("진토닉")
            .strAlcoholic("알코올")
            .strCategory("칵테일")
            .strGlass("하이볼 글라스")
            .strIngredient1("진")
            .strIngredient2("토닉워터")
            .strIngredient3("라임")
            .strInstructions("진과 토닉워터를 1:2 비율로 섞고 라임을 넣어주세요.")
            .strMeasure1("45ml")
            .strMeasure2("90ml")
            .strMeasure3("1/4개")
            .build();

    public static Cocktail virginMojito = Cocktail.builder()
            .id(new ObjectId("507f1f77bcf86cd799439013"))
            .strDrink("논알콜 모히토")
            .strAlcoholic("무알콜")
            .strCategory("논알콜 칵테일")
            .strGlass("하이볼 글라스")
            .strIngredient1("라임")
            .strIngredient2("설탕")
            .strIngredient3("민트")
            .strIngredient4("탄산수")
            .strInstructions("라임과 설탕을 섞어 라임시럽을 만들고, 민트를 넣어 향을 우려낸 후 탄산수를 넣어주세요.")
            .strMeasure1("1개")
            .strMeasure2("2티스푼")
            .strMeasure3("4-5잎")
            .strMeasure4("적당량")
            .build();
}