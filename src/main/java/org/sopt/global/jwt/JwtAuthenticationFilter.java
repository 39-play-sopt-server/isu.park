package org.sopt.global.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        if (token != null) {
            try {
                Long memberId = jwtProvider.getMemberId(token);

                // 인증 완료된 Authentication 생성 후 SecurityContext에 저장
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(memberId, null, List.of());  // principal, credentials, authorities
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException | IllegalArgumentException e) {
                // 유효하지 않은 토큰이면 인증 정보를 저장하지 않음
                // 예외 작성해도 restControllerAdvice에서 예외 잡아서 처리해주지 않음 -> Filter는 Dispatcher 밖에 존재함, RestControllerAdvice는 Dispathcer 안에서 작동함
                // 이걸 해결할 수 있는 2가지 방법 -> 추가적 ExceptionHandler 만들거나 내부 ExceptionHandler로 전달해줘서 거기서 처리하게 구현하기
            }
        }
        filterChain.doFilter(request, response);  // 다음 필터로 넘어가기 위해서
    }

    private String resolveToken(HttpServletRequest request) {  // 토큰 추출
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);   // 헤더 전문 뽑아서 prefix 문자열 파싱을 통해 실제 토큰값 추출
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}