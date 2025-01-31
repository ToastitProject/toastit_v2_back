package org.toastit_v2.core.ui.cocktail.basecocktail.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.toastit_v2.common.annotation.swagger.ApiExceptionResponse;
import org.toastit_v2.common.annotation.swagger.ApiRequestBody;
import org.toastit_v2.common.annotation.swagger.ApiSuccessResponse;
import org.toastit_v2.common.response.SuccessResponse;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.common.response.code.SuccessCode;
import org.toastit_v2.core.application.cocktail.basecocktail.service.CocktailService;
import org.toastit_v2.core.ui.cocktail.basecocktail.payload.request.CocktailCreateRequest;
import org.toastit_v2.core.ui.cocktail.basecocktail.payload.response.CocktailResponse;

@Tag(
        name = "Cocktail Management",
        description = "칵테일 관리 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/cocktails")
public class ManagementController {

    private final CocktailService cocktailService;

    @Operation(
            summary = "칵테일 생성",
            description = "새로운 칵테일을 생성합니다."
    )
    @ApiSuccessResponse(SuccessCode.SUCCESS)
    @ApiExceptionResponse({
            ExceptionCode.INVALID_COCKTAIL_NAME,
            ExceptionCode.INVALID_COCKTAIL_ALCOHOLIC,
            ExceptionCode.INVALID_COCKTAIL_INGREDIENT,
            ExceptionCode.INVALID_COCKTAIL_INSTRUCTION
    })
    @PostMapping
    public ResponseEntity<SuccessResponse<CocktailResponse>> createCocktail(
            @ApiRequestBody(
                    description = "칵테일 생성 요청 정보",
                    content = @Content(
                            schema = @Schema(implementation = CocktailCreateRequest.class)
                    )
            )
            @Valid @RequestBody final CocktailCreateRequest request
    ) {
        CocktailResponse response = CocktailResponse.from(
                cocktailService.createCocktail(request.toDomain())
        );

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        response,
                        SuccessCode.SUCCESS.getHttpStatus(),
                        SuccessCode.SUCCESS.getMessage(),
                        SuccessCode.SUCCESS.getStatusCode()
                )
        );
    }

}
