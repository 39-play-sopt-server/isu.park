package org.sopt.app.post.controller;

import org.sopt.app.member.entity.Member;
import org.sopt.app.post.dto.*;
import org.sopt.app.post.entity.Category;
import org.sopt.app.post.entity.Post;
import org.sopt.app.post.service.PostService;
import org.sopt.global.common.BaseResponse;
import org.sopt.global.common.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController implements PostApi {
    private final PostService postService;
    public PostController(PostService postService) {  // 의존성 주입
        this.postService = postService;
    }
    @PostMapping
    public ResponseEntity<BaseResponse<Void>> createPost(
            @RequestBody(required = true) PostCreateRequest request
    ){
        postService.createPost(request);
        return BaseResponse.success(SuccessCode.RESOURCE_CREATED, null);
    }
    @GetMapping
    public ResponseEntity<BaseResponse<List<PostListResponse>>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        List<PostListResponse> posts = postService.getPosts(page, size);
        return BaseResponse.success(SuccessCode.RESOURCE_RETRIVED, posts);
    }
    @GetMapping(path="/{postId}")
    public ResponseEntity<BaseResponse<PostResponse>> getPost(
            @PathVariable(name="postId") Long postId
    ){
        PostResponse post = postService.getPost(postId);
        return BaseResponse.success(SuccessCode.RESOURCE_RETRIVED, post);
    }
    @GetMapping(path ="/search")  // 동적 쿼리로 게시글 필터링
    public ResponseEntity<BaseResponse<List<PostResponse>>> getPostBySearch(
            @RequestParam(value="category", required = false) Category category,
            @RequestParam(value="authorId", required = false) Long authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ){
        List<PostResponse> posts = postService.getPostBySearch(category, authorId, page, size);
        return BaseResponse.success(SuccessCode.RESOURCE_RETRIVED, posts);
    }

    @PatchMapping(path="/{postId}")
    public ResponseEntity<BaseResponse<Void>> updatePost(
            @PathVariable(name="postId") Long postId,
            @RequestBody(required = true) PostUpdateRequest request
    ){
        postService.updatePost(postId, request);
        return BaseResponse.success(SuccessCode.RESOURCE_UPDATED, null);
    }
    @DeleteMapping(path="/{postId}")
    public ResponseEntity<BaseResponse<Void>> deletePost(
            @PathVariable(name="postId") Long postId
    ) {
        postService.deletePost(postId);
        return BaseResponse.success(SuccessCode.RESOURCE_DELETED, null);
    }
}
