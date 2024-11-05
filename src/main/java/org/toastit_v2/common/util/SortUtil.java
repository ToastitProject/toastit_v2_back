package org.toastit_v2.common.util;

import org.toastit_v2.common.type.SortType;
import org.springframework.data.domain.Sort;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 정렬 방식에 따라 Sort 객체를 생성하는 유틸리티 클래스.
 */
public class SortUtil {

    // 필드 이름을 상수로 정의
    private static final String VIEW_COUNT_FIELD = "view_count";

    private static final String LIKE_COUNT_FIELD = "like_count";

    private static final String CREATE_DATE_FIELD = "create_date";

    // EnumMap을 사용하여 SortType에 따른 정렬 방식을 매핑
    private static final Map<SortType, Function<String, Sort>> sortMap = new EnumMap<>(SortType.class);

    static {
        sortMap.put(SortType.VIEWCOUNT, field -> Sort.by(Sort.Direction.DESC, field));
        sortMap.put(SortType.LIKECOUNT, field -> Sort.by(Sort.Direction.DESC, field));
        sortMap.put(SortType.OLDEST, field -> Sort.by(Sort.Direction.ASC, field));
        sortMap.put(SortType.NEWEST, field -> Sort.by(Sort.Direction.DESC, field));
    }

    /**
     * 유틸리티 클래스이므로 인스턴스화 방지를 위한 private 생성자.
     */
    private SortUtil() {
    }

    /**
     * 주어진 SortType에 따라 적절한 정렬 방식을 반환합니다.
     *
     * @param sortType 정렬 방식 (NEWEST, OLDEST, VIEWCOUNT, LIKECOUNT)
     * @return Sort 객체, 정렬 기준 필드를 포함함
     */
    public static Sort getSort(SortType sortType) {
        return sortMap.getOrDefault(sortType, field -> Sort.by(Sort.Direction.DESC, CREATE_DATE_FIELD))
                .apply(getSortField(sortType));
    }

    /**
     * 주어진 SortType에 따라 적절한 필드 이름을 반환합니다.
     *
     * @param sortType 정렬 방식
     * @return 정렬 기준 필드 이름
     */
    private static String getSortField(SortType sortType) {
        return switch (sortType) {
            case VIEWCOUNT -> VIEW_COUNT_FIELD;
            case LIKECOUNT -> LIKE_COUNT_FIELD;
            case OLDEST, NEWEST -> CREATE_DATE_FIELD;
        };
    }
}
