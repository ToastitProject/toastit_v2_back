package org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb;

// mongodb에 맞도록 Entity가 아니라 Document로 생성함.
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.toastit_v2.feature.basecocktail.domain.Cocktail;

@Getter
@Document(collection = "test")
public class CocktailDocument {

    @Id
    private ObjectId id;

    @Field("strDrink")
    private String strDrink;

    @Field("strAlcoholic")
    private String strAlcoholic;

    @Field("strCategory")
    private String strCategory;

    @Field("strGlass")
    private String strGlass;

    @Field("strIngredient1")
    private String strIngredient1;

    @Field("strIngredient2")
    private String strIngredient2;

    @Field("strIngredient3")
    private String strIngredient3;

    @Field("strIngredient4")
    private String strIngredient4;

    @Field("strIngredient5")
    private String strIngredient5;

    @Field("strIngredient6")
    private String strIngredient6;

    @Field("strIngredient7")
    private String strIngredient7;

    @Field("strIngredient8")
    private String strIngredient8;

    @Field("strIngredient9")
    private String strIngredient9;

    @Field("strIngredient10")
    private String strIngredient10;

    @Field("strIngredient11")
    private String strIngredient11;

    @Field("strInstructions")
    private String strInstructions;

    @Field("strMeasure1")
    private String strMeasure1;

    @Field("strMeasure2")
    private String strMeasure2;

    @Field("strMeasure3")
    private String strMeasure3;

    @Field("strMeasure4")
    private String strMeasure4;

    @Field("strMeasure5")
    private String strMeasure5;

    @Field("strMeasure6")
    private String strMeasure6;

    @Field("strMeasure7")
    private String strMeasure7;

    @Field("strMeasure8")
    private String strMeasure8;

    @Field("strMeasure9")
    private String strMeasure9;

    @Field("strMeasure10")
    private String strMeasure10;

    @Field("strMeasure11")
    private String strMeasure11;

//    @Field("likeCount")
//    private int likeCount;

    @Builder
    public CocktailDocument(ObjectId id, String strDrink, String strAlcoholic,
                            String strCategory, String strGlass,
                            String strIngredient1, String strIngredient2,
                            String strIngredient3, String strIngredient4,
                            String strIngredient5, String strIngredient6,
                            String strIngredient7, String strIngredient8,
                            String strIngredient9, String strIngredient10, String strIngredient11,
                            String strInstructions, String strMeasure1,
                            String strMeasure2, String strMeasure3,
                            String strMeasure4, String strMeasure5,
                            String strMeasure6, String strMeasure7,
                            String strMeasure8, String strMeasure9,
                            String strMeasure10, String strMeasure11) {
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
//        this.likeCount = likeCount;
    }

    public Cocktail toDomain() {
        return Cocktail.builder()
                .id(this.id)
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
//                .likeCount(this.likeCount)
                .build();
    }


    // 실제로 쓰일지는 모르겠는데 일단 추가해둠.
    public static CocktailDocument fromDomain(Cocktail cocktail) {
        return CocktailDocument.builder()
                .id(cocktail.getId())
                .strDrink(cocktail.getStrDrink())
                .strAlcoholic(cocktail.getStrAlcoholic())
                .strCategory(cocktail.getStrCategory())
                .strGlass(cocktail.getStrGlass())
                .strIngredient1(cocktail.getStrIngredient1())
                .strIngredient2(cocktail.getStrIngredient2())
                .strIngredient3(cocktail.getStrIngredient3())
                .strIngredient4(cocktail.getStrIngredient4())
                .strIngredient5(cocktail.getStrIngredient5())
                .strIngredient6(cocktail.getStrIngredient6())
                .strIngredient7(cocktail.getStrIngredient7())
                .strIngredient8(cocktail.getStrIngredient8())
                .strIngredient9(cocktail.getStrIngredient9())
                .strIngredient10(cocktail.getStrIngredient10())
                .strIngredient11(cocktail.getStrIngredient11())
                .strInstructions(cocktail.getStrInstructions())
                .strMeasure1(cocktail.getStrMeasure1())
                .strMeasure2(cocktail.getStrMeasure2())
                .strMeasure3(cocktail.getStrMeasure3())
                .strMeasure4(cocktail.getStrMeasure4())
                .strMeasure5(cocktail.getStrMeasure5())
                .strMeasure6(cocktail.getStrMeasure6())
                .strMeasure7(cocktail.getStrMeasure7())
                .strMeasure8(cocktail.getStrMeasure8())
                .strMeasure9(cocktail.getStrMeasure9())
                .strMeasure10(cocktail.getStrMeasure10())
                .strMeasure11(cocktail.getStrMeasure11())
//                .likeCount(cocktail.getLikeCount())
                .build();
    }
}