package org.sopt.global.exception;

import org.sopt.global.common.ErrorCode;

public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode; // 부모 클래스(RuntimeException)에 에러 메시지 전달
    }
    public ErrorCode getErrorCode() {return errorCode;}
}
