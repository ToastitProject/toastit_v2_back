package org.toastit_v2.core.ui.cocktail.trendcocktail;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.toastit_v2.common.response.SuccessResponse;
import org.toastit_v2.common.response.code.SuccessCode;
import org.toastit_v2.core.application.cocktail.trendcocktail.service.TrendCocktailService;

import java.util.List;
import java.util.Map;

@Tag(
        name = "Trend Cocktail",
        description = "트렌드 칵테일 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/cocktail")
public class TrendCocktailController {

    private final TrendCocktailService trendCocktailService;

    @Operation(
            summary = "구글 트렌드 요청",
            description = "구글 트렌드를 통해 칵테일 정보 요청"
    )
    @PostMapping("/test/google/trendCocktail")
    public ResponseEntity<SuccessResponse<Map<String, Object>>> getGoogleTrend(
            @Parameter(
                    description = "검색 키워드",
                    example = "칵테일"
            )
            @RequestParam String keyword) {
        Map<String, Object> trendData = trendCocktailService.googleTrendAPIRequest(keyword);

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        trendData,
                        SuccessCode.SUCCESS.getHttpStatus(),
                        SuccessCode.SUCCESS.getMessage(),
                        SuccessCode.SUCCESS.getStatusCode()
                )
        );
    }

    @Operation(
            summary = "네이버 트렌드 요청",
            description = "네이버 트렌드를 통해 칵테일 정보 요청"
    )
    @PostMapping("/test/naver/trendCocktail")
    public ResponseEntity<SuccessResponse<String>> trendCocktailSearchResult() {
        List<String> cocktailList = List.of("모히또", "마티니");

        String result = trendCocktailService.naverTrendAPIRequest(cocktailList);

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        result,
                        SuccessCode.SUCCESS.getHttpStatus(),
                        SuccessCode.SUCCESS.getMessage(),
                        SuccessCode.SUCCESS.getStatusCode()
                )
        );
    }
}
