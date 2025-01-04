package org.toastit_v2.feature.basecocktail.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.toastit_v2.core.common.application.code.CommonExceptionCode;
import org.toastit_v2.core.common.application.config.swagger.ExceptionCodeAnnotations;
import org.toastit_v2.feature.basecocktail.application.service.CocktailService;
import org.toastit_v2.feature.basecocktail.domain.Cocktail;
import org.toastit_v2.feature.basecocktail.web.response.CocktailResponse;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Cocktail", description = "칵테일 기본 레시피 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cocktails")
public class CocktailController {

    private final CocktailService cocktailService;

    // 통합 검색 api
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
            @Parameter(description = "검색 키워드", example = "알코올, 레몬")
            @RequestParam String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        return cocktailService.search(keyword, PageRequest.of(page, size))
                .map(CocktailResponse::from);
    }

    // id 기반 list 반환 api
    @Operation(
            summary = "ID 목록으로 칵테일 조회",
            description = "여러 칵테일 ID를 받아서 해당하는 칵테일들을 조회합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.FILED_ERROR,
            CommonExceptionCode.BAD_REQUEST,
            CommonExceptionCode.NOT_FOUND_COCKTAIL
    })
    @GetMapping("/by-ids")
    public List<CocktailResponse> getCocktailsByIds(
            @Parameter(description = "조회할 칵테일 ID 목록", example = "507f1f77bcf86cd799439011,507f1f77bcf86cd799439012")
            @RequestParam List<String> ids
    ) {
        List<ObjectId> objectIds = ids.stream()
                .map(ObjectId::new)
                .collect(Collectors.toList());

        return cocktailService.getCocktailsByIds(objectIds).stream()
                .map(CocktailResponse::from)
                .collect(Collectors.toList());
    }

    // 랜덤 조회 api
    @Operation(
            summary = "랜덤 칵테일 조회",
            description = "지정된 개수만큼 랜덤으로 칵테일을 조회합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.FILED_ERROR,
            CommonExceptionCode.BAD_REQUEST
    })
    @GetMapping("/random")
    public List<CocktailResponse> getRandom(
            @Parameter(description = "조회할 개수", example = "5")
            @RequestParam(defaultValue = "5") int count
    ) {
        return cocktailService.getRandomCocktails(count).stream()
                .map(CocktailResponse::from)
                .collect(Collectors.toList());
    }

    // 전체 목록 조회
    @Operation(
            summary = "칵테일 전체 목록 조회",
            description = "모든 칵테일을 페이징하여 조회합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.FILED_ERROR,
            CommonExceptionCode.BAD_REQUEST
    })
    @GetMapping
    public Page<CocktailResponse> getAllCocktails(
            @Parameter(description = "페이지 번호 (0부터 시작)")
            @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "페이지 크기")
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        return cocktailService.getAllCocktails(PageRequest.of(page, size))
                .map(CocktailResponse::from);
    }

    // 이름만 조회
    @Operation(
            summary = "칵테일 이름 목록 조회",
            description = "모든 칵테일의 이름 목록을 조회합니다."
    )
    @GetMapping("/names")
    public List<String> getCocktailNames() {
        return cocktailService.getCocktailNames().stream()
                .map(Cocktail::getStrDrink)
                .collect(Collectors.toList());
    }
}

