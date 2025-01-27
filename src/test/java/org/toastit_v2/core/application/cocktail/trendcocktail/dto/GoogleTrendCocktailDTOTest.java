/*
package org.toastit_v2.core.application.cocktail.trendcocktail.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.toastit_v2.core.application.cocktail.trendcocktail.service.utilly.DateCalculator;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")
class GoogleTrendCocktailDTOTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private String query = "마티니";

    @Test
    void googleTrendAPIConvertDTO() throws IOException {

        ClassPathResource classPathResource = new ClassPathResource("googleResponseExample.json");
        Map<String, Object> googleAPIResponse = objectMapper.readValue(classPathResource.getInputStream(), Map.class);

        GoogleTrendCocktailDTO googleTrendCocktailDTO = new GoogleTrendCocktailDTO();
        GoogleTrendCocktailDTO dto = googleTrendCocktailDTO.googleTrendAPIConvertDTO(googleAPIResponse, query);


        Assertions.assertThat(dto.getQuery()).isEqualTo(query);
        Assertions.assertThat(dto.getValue()).isEqualTo(305);
        Assertions.assertThat(dto.getPreviousMonthData()).isEqualTo(DateCalculator.lastMonth());
    }

}
*/
