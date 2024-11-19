package org.toastit_v2.core.common.application.util;

import org.springframework.data.domain.Sort;
import org.toastit_v2.core.common.application.type.SortType;

/**
 * 정렬 방식에 따라 Sort 객체를 생성하는 유틸리티 클래스.
 */
public class SortUtil {

    private static final String VIEW_COUNT_FIELD = "view_count";
    private static final String LIKE_COUNT_FIELD = "like_count";
    private static final String CREATE_DATE_FIELD = "create_date";

    /**
     * 유틸리티 클래스이므로 인스턴스화 방지를 위한 private 생성자.
     */
    private SortUtil() {
    }

    /**
     * 주어진 {@link SortType}에 따라 적절한 {@link Sort} 객체를 반환한다.
     *
     * @param type 적용할 정렬 방식 (VIEWCOUNT, LIKECOUNT, OLDEST, NEWEST)
     * @return 필드 기준과 방향이 설정된 {@link Sort} 객체
     */
    public static Sort getSort(SortType type) {
        return switch (type) {
            case VIEWCOUNT -> Sort.by(Sort.Direction.DESC, VIEW_COUNT_FIELD);
            case LIKECOUNT -> Sort.by(Sort.Direction.DESC, LIKE_COUNT_FIELD);
            case OLDEST -> Sort.by(Sort.Direction.ASC, CREATE_DATE_FIELD);
            case NEWEST -> Sort.by(Sort.Direction.DESC, CREATE_DATE_FIELD);
        };
    }

}
