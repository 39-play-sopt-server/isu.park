package org.sopt.app.member.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.app.member.service.MemberService;
import org.sopt.global.common.BaseResponse;
import org.sopt.global.common.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    @DeleteMapping(path= "/{memberId}")  // id로 삭제
    public ResponseEntity<BaseResponse<Void>> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return BaseResponse.success(SuccessCode.RESOURCE_DELETED, null);
    }
    @DeleteMapping(path="/me")
    public ResponseEntity<BaseResponse<Void>> deleteMe(@AuthenticationPrincipal Long memberId) {
        memberService.withdraw(memberId);
        return BaseResponse.success(SuccessCode.MEMBER_WITHDRAW_SUCCESS, null);
    }

    @GetMapping(path="/me")
    public ResponseEntity<BaseResponse<Long>> me(@AuthenticationPrincipal Long memberId) { // 인증 필터를 모두 거쳐 controller 까지 요청 들어왔을 때, 자동으로 security에 등록된 principal을 가져오라는 어노테이션임
        return BaseResponse.success(SuccessCode.MEMBER_INFO_RETRIEVED, memberId);
    }


}
