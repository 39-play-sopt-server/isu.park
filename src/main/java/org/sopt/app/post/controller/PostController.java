package org.sopt.app.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.app.post.entity.Category;
import org.sopt.app.post.dto.PostCreateRequest;
import org.sopt.app.post.dto.PostResponse;
import org.sopt.app.post.dto.PostUpdateRequest;
import org.sopt.app.post.entity.Post;
import org.sopt.app.post.service.PostService;
import org.sopt.global.common.BaseResponse;
import org.sopt.global.common.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="게시물 관리", description = "게시물 CRUD 관련 API")
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
    public ResponseEntity<BaseResponse<List<Post>>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        List<Post> posts = postService.getPosts(page, size);
        return BaseResponse.success(SuccessCode.RESOURCE_RETRIVED, posts);
    }
    @GetMapping(path="/{postId}")
    public ResponseEntity<BaseResponse<PostResponse>> getPost(
            @PathVariable(name="postId") Long postId
    ){
        PostResponse post = postService.getPost(postId);
        return BaseResponse.success(SuccessCode.RESOURCE_RETRIVED, post);
    }
    @GetMapping(path="/categories")
    public ResponseEntity<BaseResponse<List<Post>>> getPostByCategory(
            @RequestParam(defaultValue = "FREE") Category category
    ){
        List<Post> posts = postService.getPostsByCategory(category);
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
