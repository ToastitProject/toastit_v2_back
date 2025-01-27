package org.toastit_v2.core.domain.cocktail.basecocktail;

import lombok.Builder;
import org.toastit_v2.common.response.code.ExceptionCode;

public class CocktailCreate {
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

    @Builder
    public CocktailCreate(String strDrink, String strAlcoholic, String strCategory, String strGlass,
                          String strIngredient1, String strIngredient2, String strIngredient3,
                          String strIngredient4, String strIngredient5, String strIngredient6,
                          String strIngredient7, String strIngredient8, String strIngredient9,
                          String strIngredient10, String strIngredient11,
                          String strInstructions,
                          String strMeasure1, String strMeasure2, String strMeasure3,
                          String strMeasure4, String strMeasure5, String strMeasure6,
                          String strMeasure7, String strMeasure8, String strMeasure9,
                          String strMeasure10, String strMeasure11)
    {
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
    }

    // 도메인 객체로 변환하기 위해서 추가. 차후 변동될 수 있음.
    public Cocktail toDomain(){
        // 최소 데이터가 검증되어야 등록될 수 있음
        vaildate();
        return Cocktail.builder()
                .strDrink(this.strDrink)
                .strAlcoholic(this.strAlcoholic)
                .strCategory(this.strCategory)
                .strGlass(this.strGlass)
                .strIngredient1(this.strIngredient1)
                .strIngredient2(this.strIngredient2)
                .strIngredient3(this.strIngredient3)
                .strIngredient4(this.strIngredient4)
                .strIngredient5(this.strIngredient5)
                .strIngredient6(this.strIngredient6)
                .strIngredient7(this.strIngredient7)
                .strIngredient8(this.strIngredient8)
                .strIngredient9(this.strIngredient9)
                .strIngredient10(this.strIngredient10)
                .strIngredient11(this.strIngredient11)
                .strInstructions(this.strInstructions)
                .strMeasure1(this.strMeasure1)
                .strMeasure2(this.strMeasure2)
                .strMeasure3(this.strMeasure3)
                .strMeasure4(this.strMeasure4)
                .strMeasure5(this.strMeasure5)
                .strMeasure6(this.strMeasure6)
                .strMeasure7(this.strMeasure7)
                .strMeasure8(this.strMeasure8)
                .strMeasure9(this.strMeasure9)
                .strMeasure10(this.strMeasure10)
                .strMeasure11(this.strMeasure11)
                .build();
    }

    // 검증 로직
    private void vaildate(){
        // 이름이 반드시 포함되어야 함
        if(strDrink == null || strDrink.trim().isEmpty()) {
//            throw new RestApiException(ExceptionCode.INVALID_COCKTAIL_NAME);
        }

        // 알코올 타입이 반드시 입력되어야 함
        if (strAlcoholic == null || strAlcoholic.trim().isEmpty()) {
//            throw new RestApiException(CommonExceptionCode.INVALID_COCKTAIL_ALCOHOLIC);
        }

        // 재료가 반드시 포함되어야 함
        // 1개 이상이어야 하기때문에 strIngredient1만 고려
        if (strIngredient1 == null || strIngredient1.trim().isEmpty()) {
//            throw new RestApiException(CommonExceptionCode.INVALID_COCKTAIL_INGREDIENT);
        }

        // 설명이 반드시 포함되어야 함
        if (strInstructions == null || strInstructions.trim().isEmpty()) {
//            throw new RestApiException(CommonExceptionCode.INVALID_COCKTAIL_INSTRUCTION);
        }
    }
}
