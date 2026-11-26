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

    @DeleteMapping(path="/me")
    public ResponseEntity<BaseResponse<Void>> deleteMe(@AuthenticationPrincipal Long memberId) {
        memberService.withdraw(memberId);
        return BaseResponse.success(SuccessCode.MEMBER_WITHDRAW_SUCCESS, null);
    }

    @GetMapping(path="/me")
    public ResponseEntity<BaseResponse<Long>> me(@AuthenticationPrincipal Long memberId) {
        return BaseResponse.success(SuccessCode.MEMBER_INFO_RETRIEVED, memberId);
    }


}
