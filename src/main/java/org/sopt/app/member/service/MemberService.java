package org.sopt.app.member.service;

import org.sopt.app.member.dto.LoginResponse;
import org.sopt.app.member.entity.Member;
import org.sopt.app.member.repository.MemberRepository;
import org.sopt.global.common.ErrorCode;
import org.sopt.global.exception.CustomException;
import org.sopt.global.jwt.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    public void deleteMember(Long id) {
        memberRepository.findById(id)
                        .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_EXIST));
        memberRepository.deleteById(id);
    }
}
