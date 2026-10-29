package org.sopt.app.post.controller;

import org.sopt.app.post.service.PostLikeService;
import org.sopt.global.common.BaseResponse;
import org.sopt.global.common.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/posts/")
public class PostLikeController {
    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @PostMapping(path = "{postId}/likes")
    public ResponseEntity<BaseResponse<Void>> createPostLike(
            @PathVariable Long postId,
            @RequestParam Long memberId
    ){
        postLikeService.createPostLike(postId, memberId);
        return BaseResponse.success(SuccessCode.RESOURCE_CREATED, null);
    }

    @DeleteMapping(path = "{postId}/likes")
    public ResponseEntity<BaseResponse<Void>> cancelPostLike(
            @PathVariable Long postId,
            @RequestParam Long memberId
    ){
        postLikeService.cancelPostLike(postId, memberId);
        return BaseResponse.success(SuccessCode.RESOURCE_DELETED, null);

    }
}
