package org.toastit_v2.feature.basecocktail.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.toastit_v2.core.common.application.code.CommonExceptionCode;
import org.toastit_v2.core.common.application.config.swagger.ExceptionCodeAnnotations;
import org.toastit_v2.feature.basecocktail.application.service.CocktailService;
import org.toastit_v2.feature.basecocktail.domain.Cocktail;
import org.toastit_v2.feature.basecocktail.web.response.CocktailResponse;

import java.util.List;

@Tag(name = "Cocktail", description = "칵테일 기본 레시피 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cocktails")
public class CocktailController {

    private final CocktailService cocktailService;

    @Operation(
            summary = "통합 검색",
            description = "키워드로 칵테일을 검색합니다. 칵테일 이름, 재료, 알코올 유무로 검색이 가능합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.FILED_ERROR,
            CommonExceptionCode.BAD_REQUEST
    })
    @GetMapping("/search")
    public Page<CocktailResponse> search(
            @Parameter(description = "검색 키워드 (예: '알코올 레몬', '진, 라임')")
            @RequestParam String keyword,
            Pageable pageable
    ) {
        return cocktailService.search(keyword, pageable)
                .map(CocktailResponse::from);
    }

    @Operation(
            summary = "랜덤 칵테일",
            description = "지정된 개수만큼 랜덤하게 칵테일을 조회합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.BAD_REQUEST
    })
    @GetMapping("/random")
    public List<CocktailResponse> getRandom(
            @Parameter(description = "조회할 칵테일 개수")
            @RequestParam(defaultValue = "5") int count
    ) {
        return cocktailService.getRandomCocktails(count)
                .stream()
                .map(CocktailResponse::from)
                .toList();
    }
}