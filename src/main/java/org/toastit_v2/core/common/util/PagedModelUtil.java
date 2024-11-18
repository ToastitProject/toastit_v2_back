package org.toastit_v2.core.common.util;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

/**
 * 페이징된 응답을 생성하기 위한 유틸리티 클래스. HATEOAS 기반의 PagedModel 객체를 생성합니다.
 */
public class PagedModelUtil {

    /**
     * 유틸리티 클래스이므로 인스턴스화 방지를 위한 private 생성자.
     */
    private PagedModelUtil() {
    }

    /**
     * 리스트 응답을 기반으로 HATEOAS PagedModel 객체를 생성합니다.
     *
     * @param <T>       응답의 타입
     * @param responses 응답 객체 리스트
     * @param page      페이징 처리된 Page 객체
     * @param pageable  페이지 정보
     * @return HATEOAS 기반 PagedModel 객체
     */
    public static <T> PagedModel<EntityModel<T>> createPagedResponse(List<T> responses,
                                                                     Page<T> page,
                                                                     Pageable pageable
    ) {
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                pageable.getPageSize(),
                pageable.getPageNumber(),
                page.getTotalElements(),
                page.getTotalPages()
        );

        List<EntityModel<T>> entityModels = mapToEntityModels(responses);

        return PagedModel.of(entityModels, pageMetadata);
    }

    private static <T> List<EntityModel<T>> mapToEntityModels(List<T> responses) {
        return responses.stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());
    }

}
