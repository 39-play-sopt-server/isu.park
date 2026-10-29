package org.sopt.app.member.service;

import org.sopt.app.member.dto.MemberCreateRequest;
import org.sopt.app.member.entity.Member;
import org.sopt.app.member.repository.MemberRepository;
import org.sopt.global.common.ErrorCode;
import org.sopt.global.exception.CustomException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    // 회원 생성
    public void createMember(MemberCreateRequest request) {
        Member member = new Member(
                request.name(),
                request.email(),
                request.password()
        );
        memberRepository.save(member);
    }
    // 회원 삭제
    public void deleteMember(Long id) {
        memberRepository.findById(id)
                        .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_EXIST));
        memberRepository.deleteById(id);
    }
}
