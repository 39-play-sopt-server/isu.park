package org.sopt.app.member.service;

import lombok.RequiredArgsConstructor;
import org.sopt.app.member.entity.Member;
import org.sopt.app.member.repository.MemberRepository;
import org.sopt.app.member.repository.RefreshTokenRepository;
import org.sopt.global.common.ErrorCode;
import org.sopt.global.exception.CustomException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public void deleteMember(Long id) {
        memberRepository.findById(id)
                        .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_EXIST));
        memberRepository.deleteById(id);
    }

    @Transactional
    public void withdraw(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_EXIST));

        String uuid = UUID.randomUUID().toString();

        String anonymousName = "withdrawn-"+ uuid;
        String anonymousEmail = "withdrawn-"+uuid+"@example.invalid";
        String encodedRandomPassword = passwordEncoder.encode(UUID.randomUUID().toString());

        refreshTokenRepository.deleteByMember_Id(memberId);  // refresh 토큰 폐기
        member.anonymize(anonymousName, anonymousEmail, encodedRandomPassword);
    }
}
