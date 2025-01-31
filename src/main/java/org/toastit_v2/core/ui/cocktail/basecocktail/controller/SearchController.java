package org.toastit_v2.core.ui.cocktail.basecocktail.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.toastit_v2.common.annotation.swagger.ApiExceptionResponse;
import org.toastit_v2.common.annotation.swagger.ApiSuccessResponse;
import org.toastit_v2.common.response.SuccessResponse;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.common.response.code.SuccessCode;
import org.toastit_v2.core.application.cocktail.basecocktail.service.CocktailService;
import org.toastit_v2.core.domain.cocktail.basecocktail.Cocktail;
import org.toastit_v2.core.ui.cocktail.basecocktail.payload.response.CocktailResponse;
import java.util.List;
import java.util.stream.Collectors;

@Tag(
        name = "Cocktail Search",
        description = "칵테일 검색 및 조회 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/cocktails")
public class SearchController {

    private final CocktailService cocktailService;

    @Operation(
            summary = "통합 검색",
            description = "키워드로 칵테일을 검색합니다. 칵테일 이름, 재료, 알코올 유무로 검색이 가능합니다."
    )
    @ApiSuccessResponse(SuccessCode.SUCCESS)
    @ApiExceptionResponse({
            ExceptionCode.NOT_FOUND_COCKTAIL
    })
    @GetMapping("/search")
    public ResponseEntity<SuccessResponse<Page<CocktailResponse>>> search(
            @Parameter(description = "검색 키워드", example = "알코올, 레몬")
            @RequestParam final String keyword,
            @Parameter(description = "페이지 번호", example = "0")
            @RequestParam(name = "page", defaultValue = "0") final int page,
            @Parameter(description = "페이지 크기", example = "20")
            @RequestParam(name = "size", defaultValue = "20") final int size
    ) {
        Page<CocktailResponse> responses = cocktailService.search(keyword, PageRequest.of(page, size))
                .map(CocktailResponse::from);

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        responses,
                        SuccessCode.SUCCESS.getHttpStatus(),
                        SuccessCode.SUCCESS.getMessage(),
                        SuccessCode.SUCCESS.getStatusCode()
                )
        );
    }

    @Operation(
            summary = "ID 목록으로 칵테일 조회",
            description = "여러 칵테일 ID를 받아서 해당하는 칵테일들을 조회합니다."
    )
    @ApiSuccessResponse(SuccessCode.SUCCESS)
    @ApiExceptionResponse({
            ExceptionCode.NOT_FOUND_COCKTAIL
    })
    @GetMapping("/by-ids")
    public ResponseEntity<SuccessResponse<List<CocktailResponse>>> getCocktailsByIds(
            @Parameter(description = "조회할 칵테일 ID 목록",
                    example = "507f1f77bcf86cd799439011,507f1f77bcf86cd799439012")
            @RequestParam final List<String> ids
    ) {
        List<CocktailResponse> responses = cocktailService.getCocktailsByIds(
                        ids.stream()
                                .map(ObjectId::new)
                                .collect(Collectors.toList())
                ).stream()
                .map(CocktailResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        responses,
                        SuccessCode.SUCCESS.getHttpStatus(),
                        SuccessCode.SUCCESS.getMessage(),
                        SuccessCode.SUCCESS.getStatusCode()
                )
        );
    }

    @Operation(
            summary = "칵테일 전체 목록 조회",
            description = "모든 칵테일을 페이징하여 조회합니다."
    )
    @ApiSuccessResponse(SuccessCode.SUCCESS)
    @ApiExceptionResponse({
            ExceptionCode.NOT_FOUND_COCKTAIL
    })
    @GetMapping
    public ResponseEntity<SuccessResponse<Page<CocktailResponse>>> getAllCocktails(
            @Parameter(description = "페이지 번호", example = "0")
            @RequestParam(name = "page", defaultValue = "0") final int page,
            @Parameter(description = "페이지 크기", example = "20")
            @RequestParam(name = "size", defaultValue = "20") final int size
    ) {
        Page<CocktailResponse> responses = cocktailService.getAllCocktails(PageRequest.of(page, size))
                .map(CocktailResponse::from);

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        responses,
                        SuccessCode.SUCCESS.getHttpStatus(),
                        SuccessCode.SUCCESS.getMessage(),
                        SuccessCode.SUCCESS.getStatusCode()
                )
        );
    }

    @Operation(
            summary = "칵테일 이름 목록 조회",
            description = "모든 칵테일의 이름 목록을 조회합니다."
    )
    @ApiSuccessResponse(SuccessCode.SUCCESS)
    @GetMapping("/names")
    public ResponseEntity<SuccessResponse<List<String>>> getCocktailNames() {
        List<String> responses = cocktailService.getCocktailNames().stream()
                .map(Cocktail::getStrDrink)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        responses,
                        SuccessCode.SUCCESS.getHttpStatus(),
                        SuccessCode.SUCCESS.getMessage(),
                        SuccessCode.SUCCESS.getStatusCode()
                )
        );
    }

    @Operation(
            summary = "랜덤 칵테일 조회",
            description = "지정된 개수만큼 랜덤으로 칵테일을 조회합니다."
    )
    @ApiSuccessResponse(SuccessCode.SUCCESS)
    @ApiExceptionResponse({
            ExceptionCode.INVALID_COCKTAIL_COUNT
    })
    @GetMapping("/random")
    public ResponseEntity<SuccessResponse<List<CocktailResponse>>> getRandom(
            @Parameter(description = "조회할 개수", example = "5")
            @RequestParam(defaultValue = "5") final int count
    ) {
        List<CocktailResponse> responses = cocktailService.getRandomCocktails(count).stream()
                .map(CocktailResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        responses,
                        SuccessCode.SUCCESS.getHttpStatus(),
                        SuccessCode.SUCCESS.getMessage(),
                        SuccessCode.SUCCESS.getStatusCode()
                )
        );
    }

}
