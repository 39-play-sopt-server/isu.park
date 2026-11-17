package org.sopt.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {
    // 기본적인 성공 응답
    OPERATION_SUCCESSFUL(HttpStatus.OK, "요청이 성공적으로 처리되었습니다."),

    // 리소스 관련 응답
    RESOURCE_CREATED(HttpStatus.CREATED, "리소스가 성공적으로 생성되었습니다."),
    RESOURCE_UPDATED(HttpStatus.OK, "리소스가 성공적으로 업데이트되었습니다."),
    RESOURCE_DELETED(HttpStatus.OK, "리소스가 성공적으로 삭제되었습니다."),
    RESOURCE_RETRIVED(HttpStatus.OK, "리소스가 성공적으로 조회되었습니다."),

    // 회원
    MEMBER_SIGNUP_SUCCESS(HttpStatus.CREATED,"회원가입이 성공적으로 완료되었습니다."),
    MEMBER_LOGIN_SUCCESS(HttpStatus.OK,"로그인이 성공적으로 완료되었습니다."),
    MEMBER_LOGOUT_SUCCESS(HttpStatus.OK,"로그아웃이 성공적으로 완료되었습니다."),
    MEMBER_INFO_RETRIEVED(HttpStatus.OK, "회원 정보가 성공적으로 조회되었습니다."),

    // jwt token
    TOKEN_ISSUE_SUCCESS(HttpStatus.OK,"토큰이 성공적으로 발급되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
