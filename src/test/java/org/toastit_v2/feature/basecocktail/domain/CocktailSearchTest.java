package org.toastit_v2.feature.basecocktail.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CocktailSearchTest {

    @Nested
    class 알코올_타입_검색 {

        @Test
        void 무알콜_검색어로_검색하면_논알코올_타입이_설정된다() {
            // given
            String[] nonAlcoholicKeywords = {"무알콜", "무알코올", "논알콜", "논알코올"};

            for (String keyword : nonAlcoholicKeywords) {
                // when
                CocktailSearch search = CocktailSearch.from(keyword);

                // then
                assertThat(search.getSearchType()).isEqualTo(SearchType.ALCOHOL_ONLY);
                assertThat(search.getAlcoholType()).isEqualTo(AlcoholType.NON_ALCOHOLIC);
            }
        }

        @Test
        void 알코올_검색어로_검색하면_알코올_타입이_설정된다() {
            // given
            String[] alcoholicKeywords = {"알콜", "알코올"};

            for (String keyword : alcoholicKeywords) {
                // when
                CocktailSearch search = CocktailSearch.from(keyword);

                // then
                assertThat(search.getSearchType()).isEqualTo(SearchType.ALCOHOL_ONLY);
                assertThat(search.getAlcoholType()).isEqualTo(AlcoholType.ALCOHOLIC);
            }
        }
    }

    @Nested
    class 재료_검색 {

        @Test
        void 여러_재료를_쉼표로_구분하여_입력하면_복수재료_타입이_설정된다() {
            // given
            String keyword = "라임,민트,설탕";

            // when
            CocktailSearch search = CocktailSearch.from(keyword);

            // then
            assertThat(search.getSearchType()).isEqualTo(SearchType.MULTIPLE_INGREDIENTS);
            assertThat(search.getIngredients())
                    .hasSize(3)
                    .containsExactly("라임", "민트", "설탕");
        }

        @Test
        void 단일_재료로_검색하면_단일키워드_타입이_설정된다() {
            // given
            String keyword = "라임";

            // when
            CocktailSearch search = CocktailSearch.from(keyword);

            // then
            assertThat(search.getSearchType()).isEqualTo(SearchType.SINGLE_KEYWORD);
            assertThat(search.getKeyword()).isEqualTo("라임");
        }
    }

    @Nested
    class 복합_검색 {

        @Test
        void 알코올타입과_재료를_함께_검색하면_모두_정상_처리된다() {
            // given
            String keyword = "무알콜,라임,민트";

            // when
            CocktailSearch search = CocktailSearch.from(keyword);

            // then
            assertThat(search.getSearchType()).isEqualTo(SearchType.MULTIPLE_INGREDIENTS);
            assertThat(search.getAlcoholType()).isEqualTo(AlcoholType.NON_ALCOHOLIC);
            assertThat(search.getIngredients())
                    .hasSize(2)
                    .containsExactly("라임", "민트");
        }
    }

    @Test
    void 빈_검색어_입력시_NONE_타입으로_처리된다() {
        // given
        String[] emptyKeywords = {"", "   ", null};

        for (String keyword : emptyKeywords) {
            // when
            CocktailSearch search = CocktailSearch.from(keyword);

            // then
            assertThat(search.getSearchType()).isEqualTo(SearchType.NONE);
            assertThat(search.getKeyword()).isNull();
        }
    }
}