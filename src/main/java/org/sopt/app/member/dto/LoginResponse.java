package org.sopt.app.member.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken
) { }
