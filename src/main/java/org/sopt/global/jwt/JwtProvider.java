package org.sopt.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.sopt.app.member.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {

    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    private static final String CLAIM_TYPE = "type";
    private static final String CLAIM_ROLE = "role";

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    // access 토큰 발급
    public String createAccessToken(Long memberId, Role role) {
        return createToken(memberId, TokenType.ACCESS_TOKEN, accessTokenExpiration, role);
    }
    // refresh 토큰 발급
    public String createRefreshToken(Long memberId) {
        return createToken(memberId, TokenType.REFRESH_TOKEN, refreshTokenExpiration, null);
    }

    public String createToken(Long memberId, TokenType type, long expiration, Role role) {
        Date now = new Date();

        JwtBuilder builder = Jwts.builder()
                .id(UUID.randomUUID().toString())  // token ID
                .subject(String.valueOf(memberId))  // 토큰 주체 = memberId
                .claim(CLAIM_TYPE, type.getType())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration))
                .signWith(secretKey);
        if (role != null) {
            builder.claim(CLAIM_ROLE, role.name());
        }
        return builder.compact();
    }

    public Role getRole(String accessToken) {
        String role = parseClaims(accessToken).get(CLAIM_ROLE, String.class);
        return Role.valueOf(role);
    }

    public Long getMemberId(String token, TokenType expectedType) {
        Claims claims = parseClaims(token);
        validateType(claims, expectedType);

        return Long.valueOf(claims.getSubject());
    }
    public Instant getExpiration(String token, TokenType expectedType) {
        Claims claims = parseClaims(token);
        validateType(claims, expectedType);

        return claims.getExpiration().toInstant();
    }
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)  // 서명, 만료 검증 → 실패 시 JwtException
                .getPayload();
    }
    private void validateType(Claims claims, TokenType expectedType) {
        if (!expectedType.getType().equals(claims.get(CLAIM_TYPE, String.class))) {
            throw new JwtException("잘못된 토큰 종류입니다.");
        }
    }

}