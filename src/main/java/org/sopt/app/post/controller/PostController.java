package org.sopt.app.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.app.post.entity.Category;
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

@Tag(name="게시물 관리", description = "게시물 CRUD 관련 API")
@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {  // 의존성 주입
        this.postService = postService;
    }
    @Operation(summary = "게시물 생성", description = "게시물을 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createPost(
            @RequestBody(required = true) PostCreateRequest request
    ){
        postService.createPost(request);
        return ApiResponse.success(SuccessCode.RESOURCE_CREATED, null);
    }
    @Operation(summary = "게시물 목록 조회", description = "게시물 목록(번호, 제목)을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Post>>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        List<Post> posts = postService.getPosts(page, size);
        return ApiResponse.success(SuccessCode.RESOURCE_RETRIVED, posts);
    }
    @Operation(summary = "게시물 상세 조회", description = "게시물 ID를 통해 게시물 정보를 조회합니다.")
    @GetMapping(path="/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(
            @PathVariable(name="postId") Long postId
    ){
        PostResponse post = postService.getPost(postId);
        return ApiResponse.success(SuccessCode.RESOURCE_RETRIVED, post);
    }
    @Operation(summary = "카테고리별 게시물 조회", description = "카테고리를 통해 카테고리 별로 게시물 정보를 조회합니다.")
    @GetMapping(path="/categories")
    public ResponseEntity<ApiResponse<List<Post>>> getPostByCategory(
            @RequestParam(defaultValue = "FREE") Category category
    ){
        List<Post> posts = postService.getPostsByCategory(category);
        return ApiResponse.success(SuccessCode.RESOURCE_RETRIVED, posts);

    }
    @Operation(summary = "게시물 수정", description = "게시물 ID를 통해 게시물 정보를 수정합니다.")
    @PatchMapping(path="/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
            @PathVariable(name="postId") Long postId,
            @RequestBody(required = true) PostUpdateRequest request
    ){
        postService.updatePost(postId, request);
        return ApiResponse.success(SuccessCode.RESOURCE_UPDATED, null);
    }
    @Operation(summary = "게시물 삭제", description = "게시물 ID를 통해 게시물을 삭제합니다.")
    @DeleteMapping(path="/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable(name="postId") Long postId
    ) {
        postService.deletePost(postId);
        return ApiResponse.success(SuccessCode.RESOURCE_DELETED, null);
    }
}
