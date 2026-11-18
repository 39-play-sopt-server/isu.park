package org.sopt.app.member.repository;

import org.sopt.app.member.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);  // 요청에 포함된 refresh token이 DB에 저장된 토큰인지 조회
    Optional<RefreshToken> findByMember_Id(Long memberId);
    void deleteByToken(String token);
    void deleteByMember_Id(Long memberId);
}
