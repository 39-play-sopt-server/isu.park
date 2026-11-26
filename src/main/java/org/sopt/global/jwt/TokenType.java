package org.sopt.global.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {
    ACCESS_TOKEN("access"),
    REFRESH_TOKEN("refresh");

    private final String type;

    public boolean matches(String claimValue) {
        return type.equals(claimValue);
    }
}
