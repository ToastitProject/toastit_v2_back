package org.toastit_v2.feature.basecocktail.infrastructure.persistence.mongodb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.toastit_v2.feature.basecocktail.domain.CocktailSearch;

import java.util.Objects;
import java.util.stream.Stream;

import static com.mongodb.assertions.Assertions.assertFalse;
import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;

@DataMongoTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CocktailRepositoryTest {

    @Autowired
    private MongoCocktailRepository mongoRepository;

    @Test
    @DisplayName("알코올 타입으로 검색")
    void searchByAlcoholType() {
        // given
        CocktailSearch search = CocktailSearch.from("알코올");
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<CocktailDocument> result = mongoRepository.search(search, pageable);

        // then
        assertFalse(result.isEmpty());
        assertTrue(result.getContent().stream()
                .allMatch(cocktail -> cocktail.getStrAlcoholic().contains("알코올")));
    }

    @Test
    @DisplayName("재료로 검색 - 단일 재료")
    void searchBySingleIngredient() {
        // given
        CocktailSearch search = CocktailSearch.from("레몬");
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<CocktailDocument> result = mongoRepository.search(search, pageable);

        // then
        assertFalse(result.isEmpty());
        assertTrue(result.getContent().stream()
                .anyMatch(cocktail ->
                        containsIngredient(cocktail, "레몬")));
    }

    @Test
    @DisplayName("재료로 검색 - 복수 재료")
    void searchByMultipleIngredients() {
        // given
        CocktailSearch search = CocktailSearch.from("레몬, 라임");
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<CocktailDocument> result = mongoRepository.search(search, pageable);

        // then
        assertFalse(result.isEmpty());
        assertTrue(result.getContent().stream()
                .allMatch(cocktail ->
                        containsIngredient(cocktail, "레몬") &&
                                containsIngredient(cocktail, "라임")));
    }

    private boolean containsIngredient(CocktailDocument cocktail, String ingredient) {
        return Stream.of(
                        cocktail.getStrIngredient1(),
                        cocktail.getStrIngredient2(),
                        cocktail.getStrIngredient3(),
                        cocktail.getStrIngredient4(),
                        cocktail.getStrIngredient5(),
                        cocktail.getStrIngredient6()
                )
                .filter(Objects::nonNull)
                .anyMatch(ing -> ing.toLowerCase().contains(ingredient.toLowerCase()));
    }
}