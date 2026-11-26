package org.sopt.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

// 공통 응답 템플릿 - 모든 API 응답 형식 통일
@Getter
@RequiredArgsConstructor
public class BaseResponse<T> {
    private final int status;
    private final boolean success;  // 성공 여부
    private final String message;  // 안내 메시지
    private final T data;  // 전달 데이터

    // 성공 시 응답
    public static <T> ResponseEntity<BaseResponse<T>> success(SuccessCode successCode,T data) {
        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(new BaseResponse<>(
                        successCode.getHttpStatus().value(),
                        true,
                        successCode.getMessage(),
                        data
                ));
    }
    // 실패 응답 - data 없음
    public static <T> ResponseEntity<BaseResponse<T>> error(ErrorCode errorCode){
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new BaseResponse<>(
                        errorCode.getHttpStatus().value(),
                        false,
                        errorCode.getMessage(),
                        null
                ));
    }
    // 실패 응답 - data 없음
    public static <T> ResponseEntity<BaseResponse<T>> error(ErrorCode errorCode, T data){
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new BaseResponse<>(
                        errorCode.getHttpStatus().value(),
                        false,
                        errorCode.getMessage(),
                        data
                ));
    }
    public static ResponseEntity<BaseResponse<Void>> error(ErrorCode errorCode, String customMessage) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new BaseResponse<>(errorCode.getHttpStatus().value(), false, customMessage, null));
    }


}
