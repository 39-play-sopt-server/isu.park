package org.sopt.global.common;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // 공통 에러
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리소스를 찾지 못했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다."),

    // 게시물 입력값 검증 에러
    POST_TITLE_EMPTY(HttpStatus.BAD_REQUEST, "게시글 제목은 필수 입력 항목입니다."),
    POST_CONTENT_EMPTY(HttpStatus.BAD_REQUEST,"게시글 본문은 필수 입력 항목입니다."),
    POST_CATEGORY_EMPTY(HttpStatus.BAD_REQUEST,"게시글 카테고리는 필수 입력 항목입니다."),
    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "유효하지 않은 카테고리입니다."),

    // 게시물 조회
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
    public HttpStatus getHttpStatus() { return httpStatus; }
    public String getMessage() { return message; }
}
