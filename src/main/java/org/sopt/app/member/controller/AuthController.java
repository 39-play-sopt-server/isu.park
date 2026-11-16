package org.sopt.app.member.controller;

import jakarta.validation.Valid;
import org.sopt.app.member.dto.LoginRequest;
import org.sopt.app.member.dto.LoginResponse;
import org.sopt.app.member.dto.SignupRequest;
import org.sopt.app.member.service.AuthService;
import org.sopt.global.common.BaseResponse;
import org.sopt.global.common.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<Long>> signup(@Valid @RequestBody SignupRequest request) {
        Long memberId = authService.signup(request.name(), request.email(), request.password());
        return BaseResponse.success(SuccessCode.MEMBER_SIGNUP_SUCCESS, memberId);
    }
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        String accessToken = authService.login(request.email(), request.password());
        return BaseResponse.success(SuccessCode.TOKEN_ISSUE_SUCCESS, LoginResponse.from(accessToken));
    }
}

