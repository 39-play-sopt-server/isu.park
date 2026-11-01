package org.sopt.app.member.controller;

import org.sopt.app.member.dto.MemberCreateRequest;
import org.sopt.app.member.service.MemberService;
import org.sopt.global.common.BaseResponse;
import org.sopt.global.common.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/members")
public class MemberController {
    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @PostMapping
    public ResponseEntity<BaseResponse<Void>> createMember(
            @RequestBody(required = true) MemberCreateRequest request) {
        memberService.createMember(request);
        return BaseResponse.success(SuccessCode.RESOURCE_CREATED, null);
    }
    @DeleteMapping(path= "/{memberId}")
    public ResponseEntity<BaseResponse<Void>> deleteMember(
            @PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return BaseResponse.success(SuccessCode.RESOURCE_DELETED, null);
    }
}
