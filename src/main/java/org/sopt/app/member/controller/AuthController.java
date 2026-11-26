package org.sopt.app.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.app.member.dto.LoginRequest;
import org.sopt.app.member.dto.LoginResponse;
import org.sopt.app.member.dto.RefreshTokenRequest;
import org.sopt.app.member.dto.SignupRequest;
import org.sopt.app.member.service.AuthService;
import org.sopt.global.common.BaseResponse;
import org.sopt.global.common.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 회원가입·로그인·토큰 갱신·로그아웃 인증&토큰 처리

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<Long>> signup(@Valid @RequestBody SignupRequest request) {
        Long memberId = authService.signup(request.name(), request.email(), request.password());
        return BaseResponse.success(SuccessCode.MEMBER_SIGNUP_SUCCESS, memberId);
    }
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse tokens = authService.login(request.email(), request.password());
        return BaseResponse.success(SuccessCode.TOKEN_ISSUE_SUCCESS, tokens);
    }
    @PostMapping("/refresh")
    public ResponseEntity<BaseResponse<LoginResponse>> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        LoginResponse tokens = authService.refreshToken(request.refreshToken());
        return BaseResponse.success(SuccessCode.TOKEN_ISSUE_SUCCESS, tokens);
    }
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(@Valid @RequestBody RefreshTokenRequest request) {
        authService.logout(request.refreshToken());
        return BaseResponse.success(SuccessCode.MEMBER_LOGOUT_SUCCESS, null);
    }
}

