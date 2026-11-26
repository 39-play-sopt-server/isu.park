package org.sopt.app.member.service;

import lombok.RequiredArgsConstructor;
import org.sopt.app.member.dto.AdminMemberResponse;
import org.sopt.app.member.repository.MemberRepository;
import org.sopt.global.common.ErrorCode;
import org.sopt.global.exception.CustomException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminMemberService {
    final private MemberRepository memberRepository;
    private final MemberService memberService;

    public void deleteMember(Long adminId, Long targetId) {
        if(adminId.equals(targetId)) {
            throw new CustomException(ErrorCode.CANNOT_WITHDRAW_SELF_BY_ADMIN_API);
        }
        // 회원 탈퇴와 같은 공통 처리: refresh token 폐기 + 회원 정보 익명화
        memberService.withdraw(targetId);
    }

    public List<AdminMemberResponse> findAll(Long adminId) {
        return memberRepository.findAll().stream()
                .map(member -> new AdminMemberResponse(
                        member.getId(),
                        member.getName(),
                        member.getEmail(),
                        member.getRole(),
                        member.isWithdrawn()
                ))
                .toList();
    }
}
