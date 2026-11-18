package org.sopt.app.member.service;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.sopt.app.member.dto.LoginResponse;
import org.sopt.app.member.entity.Member;
import org.sopt.app.member.entity.RefreshToken;
import org.sopt.app.member.repository.MemberRepository;
import org.sopt.global.common.ErrorCode;
import org.sopt.global.exception.CustomException;
import org.sopt.global.jwt.JwtProvider;
import org.sopt.global.jwt.TokenType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sopt.app.member.repository.RefreshTokenRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Long signup(String name, String email, String password) {
        if (memberRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXIST);
        }
        Member member = new Member(name, email, passwordEncoder.encode(password));
        return memberRepository.save(member).getId();
    }

    // 로그인 성공 -> access + refresh token 발급 (refresh는 DB에 저장)
    @Transactional
    public LoginResponse login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_LOGIN_INFO));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_LOGIN_INFO);
        }
        String accessToken = jwtProvider.createAccessToken(member.getId());
        String refreshToken = jwtProvider.createRefreshToken(member.getId());
        Instant expiresAt = jwtProvider.getExpiration(refreshToken, TokenType.REFRESH_TOKEN);

        // member 당 token은 하나만 유지
        refreshTokenRepository.findByMember_Id(member.getId())
                .ifPresentOrElse(
                        savedToken -> savedToken.rotate(refreshToken, expiresAt),
                        () -> refreshTokenRepository.save(
                                new RefreshToken(member, accessToken, expiresAt)
                        )
                );

        return new LoginResponse(accessToken, refreshToken);
    }
    // 토큰 검증 및 재발급
    @Transactional
    public LoginResponse refreshToken(String refreshToken) {
        final Long memberId;

        try{ // jwt 유효 검증
            memberId = jwtProvider.getMemberId(refreshToken, TokenType.REFRESH_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        // DB에서 token 검증
        RefreshToken savedToken = refreshTokenRepository.findByToken(refreshToken)
                .filter(token -> token.getExpiresAt().isAfter(Instant.now()))
                .filter(token -> token.getMember().getId().equals(memberId))
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REFRESH_TOKEN));

        // 새로 발급
        String newAccessToken = jwtProvider.createAccessToken(memberId);
        String newRefreshToken = jwtProvider.createRefreshToken(memberId);
        Instant newExpiresAt = jwtProvider.getExpiration(newRefreshToken, TokenType.REFRESH_TOKEN);

        // 이전 refresh 토큰을 새로운 것으로 대체(재사용 방지)
        savedToken.rotate(newAccessToken, newExpiresAt);

        return new LoginResponse(newAccessToken, newRefreshToken);
    }

    // 전달된 refresh token 삭제
    @Transactional
    public void logout(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}