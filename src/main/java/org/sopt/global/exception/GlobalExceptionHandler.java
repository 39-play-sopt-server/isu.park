package org.sopt.global.exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.global.common.BaseResponse;
import org.sopt.global.common.ErrorCode;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import tools.jackson.databind.exc.InvalidFormatException;
import java.util.stream.Collectors;

@RestControllerAdvice // 전역에서 발생한 예외 가로챔
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 1. 비즈니스 커스텀 예외
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Void>> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("[CustomException] code={}, message={}", errorCode.name(), e.getMessage());
        return BaseResponse.error(errorCode);
    }

    // 2. @Valid 검증 실패 (@RequestBody DTO 필드 검증)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining(", "));
        log.warn("[ValidationException] {}", errorMessage);
        return BaseResponse.error(ErrorCode.INVALID_INPUT_VALUE, errorMessage);
    }

    // 3. @Validated 검증 실패 (@RequestParam, @PathVariable 직접 검증)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse<Void>> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));
        log.warn("[ConstraintViolationException] {}", errorMessage);
        return BaseResponse.error(ErrorCode.INVALID_INPUT_VALUE, errorMessage);
    }

    // 4. JSON 파싱 실패 및 Enum 변환 실패 구분 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Void>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        if (e.getCause() instanceof InvalidFormatException invalidFormatException
                && invalidFormatException.getTargetType() != null
                && invalidFormatException.getTargetType().isEnum()) {
            log.warn("[InvalidEnumException] value={}", invalidFormatException.getValue());
            return BaseResponse.error(ErrorCode.INVALID_CATEGORY);
        }
        log.warn("[HttpMessageNotReadableException] {}", e.getMessage());
        return BaseResponse.error(ErrorCode.MALFORMED_JSON_REQUEST);
    }

    // 5. 필수 쿼리 파라미터 누락 (?page= 등 누락)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<BaseResponse<Void>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String errorMessage = String.format("필수 파라미터 '%s'(%s)가 누락되었습니다.", e.getParameterName(), e.getParameterType());
        log.warn("[MissingParameter] {}", errorMessage);
        return BaseResponse.error(ErrorCode.MISSING_REQUEST_PARAMETER, errorMessage);
    }

    // 6. 파라미터 타입 불일치 (/members/abc 처럼 Long 자리에 문자열 입력)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse<Void>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String errorMessage = String.format("파라미터 '%s'의 값 '%s'이(가) 올바른 타입이 아닙니다.", e.getName(), e.getValue());
        log.warn("[TypeMismatch] {}", errorMessage);
        return BaseResponse.error(ErrorCode.INVALID_TYPE_VALUE, errorMessage);
    }

    // 7. 지원하지 않는 HTTP 메서드 (GET API에 POST 요청 등)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse<Void>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn("[MethodNotSupported] method={}", e.getMethod());
        return BaseResponse.error(ErrorCode.METHOD_NOT_ALLOWED);
    }

    // 8. 존재하지 않는 URL 요청 (Spring Boot 3.2+ / 4.x 표준 404 예외)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleNoResourceFoundException(NoResourceFoundException e) {
        log.warn("[NoResourceFound] path={}", e.getResourcePath());
        return BaseResponse.error(ErrorCode.RESOURCE_NOT_FOUND);
    }

    // 9. DB 무결성 제약 위반 (동시성 중복 가입 등 UNIQUE/FK 제약 위반)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseResponse<Void>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("[DataIntegrityViolation] DB 제약 조건 위반 발생", e);
        return BaseResponse.error(ErrorCode.DATA_INTEGRITY_VIOLATION);
    }

    // 10. 처리되지 않은 모든 최상위 서버 예외 (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleException(Exception e) {
        log.error("[UnhandledException] 예상치 못한 서버 내부 오류 발생", e);
        return BaseResponse.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    private String formatFieldError(FieldError fieldError) {
        return String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage());
    }

}
