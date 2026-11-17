package org.sopt.app.member.service;

import lombok.RequiredArgsConstructor;
import org.sopt.app.member.repository.MemberRepository;
import org.sopt.global.common.ErrorCode;
import org.sopt.global.exception.CustomException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void deleteMember(Long id) {
        memberRepository.findById(id)
                        .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_EXIST));
        memberRepository.deleteById(id);
    }
}
