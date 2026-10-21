package org.sopt.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.global.common.BaseResponse;
import org.sopt.global.common.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 전역에서 발생한 예외 가로챔
public class GlobalExceptionHandler {
    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomException.class)  // ex. 존재하지 않는 게시글, 필수값 누락 ...
    public ResponseEntity<BaseResponse<Void>> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        return BaseResponse.error(errorCode);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)  // 클라이언트가 Enum에 없는 값 보낼때(Category)
    public ResponseEntity<BaseResponse<Void>> handlerInvalidEnumException(HttpMessageNotReadableException e) {
        return BaseResponse.error(ErrorCode.INVALID_CATEGORY);
    }
    @ExceptionHandler(Exception.class)  // 예상치 못한 서버 예외
    public ResponseEntity<BaseResponse<Void>> handleException(Exception e) {
        log.error("상세 예외", e);
        return BaseResponse.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
