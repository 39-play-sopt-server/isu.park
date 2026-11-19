package org.sopt.app.member.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.app.member.dto.AdminMemberResponse;
import org.sopt.app.member.entity.Member;
import org.sopt.app.member.service.AdminMemberService;
import org.sopt.global.common.BaseResponse;
import org.sopt.global.common.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminMemberController {
    private final AdminMemberService adminMemberService;

    @DeleteMapping(path= "/{memberId}")  // id로 삭제
    public ResponseEntity<BaseResponse<Void>> deleteMember(
            @AuthenticationPrincipal Long adminId,
            @PathVariable Long memberId) {
        adminMemberService.deleteMember(adminId, memberId);
        return BaseResponse.success(SuccessCode.MEMBER_WITHDRAW_SUCCESS, null);
    }
    @GetMapping(path="/memberAll")
    public ResponseEntity<BaseResponse<List<AdminMemberResponse>>> getAllMembers(@AuthenticationPrincipal Long adminId){
        List<AdminMemberResponse> members = adminMemberService.findAll(adminId);
        return BaseResponse.success(SuccessCode.MEMBER_INFO_RETRIEVED, members);
    }
}
