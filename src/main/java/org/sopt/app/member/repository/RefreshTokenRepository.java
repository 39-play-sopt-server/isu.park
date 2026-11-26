package org.sopt.app.member.repository;

import jakarta.persistence.LockModeType;
import org.sopt.app.member.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    // 재발급 시 동일 토큰으로 동시에 들어온 요청을 직렬화
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from RefreshToken r where r.tokenHash = :tokenHash")
    Optional<RefreshToken> findByTokenHashForUpdate(@Param("tokenHash") String tokenHash);

    Optional<RefreshToken> findByMember_Id(Long memberId);
    @Modifying
    void deleteByTokenHash(String tokenHash);


    void deleteByMember_Id(Long memberId);
}
