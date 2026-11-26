package org.sopt.global.jwt;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.time.Duration;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties (
        @NotBlank
        String secret,
        @NotNull
        @DurationMin(seconds = 1)
        Duration accessTokenExpiration,
        @NotNull
        @DurationMin(seconds = 1)
        Duration refreshTokenExpiration
){
    public JwtProperties {
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("jwt.secret 은 필수입니다.");
        }
        if (accessTokenExpiration == null || accessTokenExpiration.isNegative() || accessTokenExpiration.isZero()) {
            throw new IllegalArgumentException("jwt.access-token-expiration가 올바르지 않습니다.");
        }
        if (refreshTokenExpiration == null || refreshTokenExpiration.isNegative() || refreshTokenExpiration.isZero()) {
            throw new IllegalArgumentException("jwt.refresh-token-expiration가 올바르지 않습니다.");
        }
    }
}
