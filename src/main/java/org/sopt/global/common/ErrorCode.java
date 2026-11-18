package org.sopt.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 공통 에러
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리소스를 찾지 못했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다."),

    // Common (400, 404, 405, 409, 500)
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "요청 파라미터의 타입이 올바르지 않습니다."),
    MISSING_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다."),
    MALFORMED_JSON_REQUEST(HttpStatus.BAD_REQUEST, "요청 본문 형식이 올바르지 않습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메서드입니다."),
    DATA_INTEGRITY_VIOLATION(HttpStatus.CONFLICT, "데이터 무결성 제약 조건을 위반했습니다."),

    // 게시물 입력값 검증 에러
    POST_TITLE_EMPTY(HttpStatus.BAD_REQUEST, "게시글 제목은 필수 입력 항목입니다."),
    POST_CONTENT_EMPTY(HttpStatus.BAD_REQUEST,"게시글 본문은 필수 입력 항목입니다."),
    POST_CATEGORY_EMPTY(HttpStatus.BAD_REQUEST,"게시글 카테고리는 필수 입력 항목입니다."),
    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "유효하지 않은 카테고리입니다."),

    // 게시물
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다."),
    POST_FORBIDDEN(HttpStatus.FORBIDDEN, "이 게시글에 대한 권한이 없습니다."),
    POST_ALREADY_LIKED(HttpStatus.CONFLICT, "이미 좋아요를 누른 게시글입니다."),

    // 회원
    MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    EMAIL_ALREADY_EXIST(HttpStatus.NOT_FOUND, "이미 가입된 이메일입니다."),
    MEMBER_EMAIL_EMPTY(HttpStatus.BAD_REQUEST, "회원의 이메일은 필수 입력 항목입니다."),
    MEMBER_PW_EMPTY(HttpStatus.BAD_REQUEST, "회원의 비밀번호는 필수 입력 항목입니다."),

    // auth
    INVALID_LOGIN_INFO(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."),

    // jwt
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않거나 만료된 Refresh Token입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
