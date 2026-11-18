package org.sopt.app.post.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.app.post.service.PostLikeService;
import org.sopt.global.common.BaseResponse;
import org.sopt.global.common.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/posts/")
public class PostLikeController {
    private final PostLikeService postLikeService;

    @PostMapping(path = "{postId}/likes")
    public ResponseEntity<BaseResponse<Void>> createPostLike(
            @AuthenticationPrincipal Long memberId,
            @PathVariable Long postId
    ){
        postLikeService.createPostLike(memberId, postId);
        return BaseResponse.success(SuccessCode.RESOURCE_CREATED, null);
    }

    @DeleteMapping(path = "{postId}/likes")
    public ResponseEntity<BaseResponse<Void>> cancelPostLike(
            @AuthenticationPrincipal Long memberId,
            @PathVariable Long postId
    ){
        postLikeService.cancelPostLike(memberId, postId);
        return BaseResponse.success(SuccessCode.RESOURCE_DELETED, null);

    }
}
