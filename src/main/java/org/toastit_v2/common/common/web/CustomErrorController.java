package org.toastit_v2.common.common.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.toastit_v2.common.common.application.code.CommonExceptionCode;
import org.toastit_v2.common.common.application.util.response.ExceptionResponseUtil;

@Controller
public class CustomErrorController {

    /**
     * HTTP 에러 상태 코드에 따라 적절한 예외 응답을 반환합니다.
     * `/error` 엔드포인트로 접근할 경우 호출되며, 발생한 에러를
     * 표준화된 응답 형식으로 변환합니다.
     *
     * @param request HTTP 요청 객체로, 에러에 대한 세부 정보(예: HTTP 상태 코드)를 포함합니다.
     *                상태 코드는 {@link RequestDispatcher#ERROR_STATUS_CODE}를 통해 가져옵니다.
     * @return 감지된 HTTP 상태 코드에 따른 표준화된 예외 응답을 포함한 {@link ResponseEntity}.
     */
    @RequestMapping("/error")
    public ResponseEntity<Object> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        return switch (status.toString()) {
            case "403" -> ExceptionResponseUtil.handleResponseForException(CommonExceptionCode.FORBIDDEN);
            case "404" -> ExceptionResponseUtil.handleResponseForException(CommonExceptionCode.NOT_FOUND);
            case "405" -> ExceptionResponseUtil.handleResponseForException(CommonExceptionCode.METHOD_NOT_ALLOWED);
            case "415" -> ExceptionResponseUtil.handleResponseForException(CommonExceptionCode.FILED_ERROR);
            default -> ExceptionResponseUtil.handleResponseForException(CommonExceptionCode.INTERNAL_SERVER_ERROR);
        };
    }

}
