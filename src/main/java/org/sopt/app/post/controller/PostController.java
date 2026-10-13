package org.sopt.app.post.controller;

import org.sopt.global.common.ApiResponse;
import org.sopt.app.post.dto.PostCreateRequest;
import org.sopt.app.post.dto.PostResponse;
import org.sopt.app.post.dto.PostUpdateRequest;
import org.sopt.app.post.entity.Post;
import org.sopt.app.post.service.PostService;
import org.sopt.global.common.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {  // 의존성 주입
        this.postService = postService;
    }
    // 1. 게시글 작성
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createPost(
            @RequestBody(required = true) PostCreateRequest request
    ){
        postService.createPost(request);
        return ApiResponse.success(SuccessCode.RESOURCE_CREATED, null);
    }
    // 2. 게시글 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<Post>>> getPosts(){
        List<Post> posts = postService.getPosts();
        return ApiResponse.success(SuccessCode.RESOURCE_RETRIVED, posts);
    }
    // 3. 게시글 단건 조회 -> 상세 조회
    @GetMapping(path="/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(
            @PathVariable(name="postId") Long postId
    ){
        PostResponse post = postService.getPost(postId);
        return ApiResponse.success(SuccessCode.RESOURCE_RETRIVED,post);
    }
    // 4. 게시글 수정
    @PatchMapping(path="/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
            @PathVariable(name="postId") Long postId,
            @RequestBody(required = true) PostUpdateRequest request
    ){
        postService.updatePost(postId, request);
        return ApiResponse.success(SuccessCode.RESOURCE_RETRIVED, null);
    }
    // 5. 게시글 삭제
    @DeleteMapping(path="/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable(name="postId") Long postId
    ) {
        postService.deletePost(postId);
        return ApiResponse.success(SuccessCode.RESOURCE_DELETED, null);
    }
}
