package org.sopt.global.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// 공통 응답 템플릿 - 모든 API 응답 형식 통일
public class ApiResponse<T> {

    private final int status;
    private final boolean success;  // 성공 여부
    private final String message;  // 안내 메시지
    private final T data;  // 전달 데이터

    public ApiResponse(int status, boolean success, String message, T data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public int getStatus() { return status; }
    public boolean isSuccess() { return success; }
    public String getMessgae() { return message; }
    public T getData(){ return data; }

    // 성공 시 응답
    public static <T> ResponseEntity<ApiResponse<T>> success(
            SuccessCode successCode
            ,T data
    ) {
        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(new ApiResponse<>(
                        successCode.getHttpStatus().value(),
                        true,
                        successCode.getMessage(),
                        data
                ));
    }
    // 실패 응답 - data 없음
    public static <T> ResponseEntity<ApiResponse<T>> error(
            ErrorCode errorCode
    ){
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ApiResponse<>(
                        errorCode.getHttpStatus().value(),
                        false,
                        errorCode.getMessage(),
                        null
                ));
    }
    // 실패 응답 - data 없음
    public static <T> ResponseEntity<ApiResponse<T>> error(
            ErrorCode errorCode,
            T data
    ){
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ApiResponse<>(
                        errorCode.getHttpStatus().value(),
                        false,
                        errorCode.getMessage(),
                        data
                ));
    }

}
