package org.sopt.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.sopt.app.member.entity.Role;
import org.sopt.global.exception.InvalidTokenException;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;


@Component
public class JwtProvider {

    private static final String CLAIM_TYPE = "type";
    private static final String CLAIM_ROLE = "role";

    private final SecretKey secretKey;
    private final Duration accessTokenExpiration;
    private final Duration refreshTokenExpiration;
    private final Clock clock;

    public JwtProvider(JwtProperties properties, Clock clock) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.secret()));
        this.accessTokenExpiration = properties.accessTokenExpiration();
        this.refreshTokenExpiration = properties.refreshTokenExpiration();
        this.clock = clock;
    }

    // access 토큰 발급
    public String createAccessToken(Long memberId, Role role) {
        return baseBuilder(memberId, TokenType.ACCESS_TOKEN, accessTokenExpiration)
                .claim(CLAIM_ROLE, role.name())
                .compact();
    }
    // refresh 토큰 발급
    public String createRefreshToken(Long memberId) {
        return baseBuilder(memberId, TokenType.REFRESH_TOKEN, refreshTokenExpiration)
                .compact();
    }

    private JwtBuilder baseBuilder(Long memberId, TokenType tokenType, Duration expiration) {
        Instant now = clock.instant();
        return Jwts.builder()
                .id(UUID.randomUUID().toString())  // token ID
                .subject(String.valueOf(memberId))  // 토큰 주체 = memberId
                .claim(CLAIM_TYPE, tokenType.getType())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expiration)))
                .signWith(secretKey);
    }

    // 조회
    public Long getMemberIdFromAccessToken(String token) {
        return extractMemberId(validatedClaims(token, TokenType.ACCESS_TOKEN));
    }

    public Long getMemberIdFromRefreshToken(String token) {
        return extractMemberId(validatedClaims(token, TokenType.REFRESH_TOKEN));
    }

    public Role getRole(String accessToken) {
        Claims claims = validatedClaims(accessToken, TokenType.ACCESS_TOKEN);
        String role = claims.get(CLAIM_ROLE, String.class);
        if (role == null) {
            throw new InvalidTokenException("role 정보가 없는 토큰입니다.");
        }
        try {
            return Role.valueOf(role);
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException("알 수 없는 role 입니다.", e);
        }
    }

    public Instant getRefreshTokenExpiration(String token) {  // Refresh 만료 시간 DB에 저장할 때 씀
        return validatedClaims(token, TokenType.REFRESH_TOKEN).getExpiration().toInstant();
    }

    // 내부 검증 메서드
    private Claims validatedClaims(String token, TokenType expectedType) {
        Claims claims = parseClaims(token);
        if (!expectedType.matches(claims.get(CLAIM_TYPE, String.class))) {
            throw new JwtException("잘못된 토큰 종류입니다.");
        }
        return claims;
    }
    private Claims parseClaims(String token) {
        try{
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .clock(() -> Date.from(clock.instant()))
                    .build()
                    .parseSignedClaims(token)  // 서명, 만료 검증 → 실패 시 JwtException
                    .getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다.", e);
        }
    }
    private Long extractMemberId(Claims claims) {
        try{
            return Long.valueOf(claims.getSubject());
        } catch (NumberFormatException e) {
            throw new InvalidTokenException("토큰의 subject가 올바르지 않습니다.");
        }
    }
}