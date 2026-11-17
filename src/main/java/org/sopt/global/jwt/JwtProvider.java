package org.sopt.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {

    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    private static final String CLAIM_TYPE = "type";

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String createAccessToken(Long memberId) {
        return createToken(memberId, TokenType.ACCESS_TOKEN, accessTokenExpiration);
    }
    public String createRefreshToken(Long memberId) {
        return createToken(memberId, TokenType.REFRESH_TOKEN, refreshTokenExpiration);
    }

    public String createToken(Long memberId, TokenType type, long expiration) {
        Date now = new Date();

        return Jwts.builder()
                .id(UUID.randomUUID().toString())  // token ID
                .subject(String.valueOf(memberId))  // 누구의 토큰인지
                .claim(CLAIM_TYPE, type.getType())
                .issuedAt(now)                       // 발급 시각
                .expiration(new Date(now.getTime() + expiration)) // 만료 시각
                .signWith(secretKey)                 // 서명
                .compact();
    }

    public Long getMemberId(String token, TokenType expectedType) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)  // 서명, 만료 검증 → 실패 시 JwtException
                .getPayload();

        if (!expectedType.equals(claims.get(CLAIM_TYPE, String.class))) {
            throw new JwtException("잘못된 토큰 종류입니다.");
        }

        return Long.valueOf(claims.getSubject());
    }
}