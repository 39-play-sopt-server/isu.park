package org.sopt.global.exception;

import org.sopt.global.common.ApiResponse;
import org.sopt.global.common.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 전역에서 발생한 예외 가로챔
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)  // ex. 존재하지 않는 게시글, 필수값 누락 ...
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ApiResponse.error(errorCode);
    }

    @ExceptionHandler(Exception.class)  // 예상치 못한 서버 예외
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        return ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
