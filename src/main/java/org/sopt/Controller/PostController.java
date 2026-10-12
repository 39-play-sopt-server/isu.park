package org.sopt.Controller;

import org.sopt.Dto.ApiResponse;
import org.sopt.Dto.PostCreateRequest;
import org.sopt.Dto.PostUpdateRequest;
import org.sopt.Entity.Post;
import org.sopt.Service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController vs @Controller의 차이?
@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {  // 의존성 주입
        this.postService = postService;
    }
    // 1. 게시글 작성
    @PostMapping
    public ApiResponse<Void> createPost(
            @RequestBody(required = true) PostCreateRequest request
    ){
        try {
            postService.createPost(request);
            return ApiResponse.success("게시글이 작성되었습니다.");
        } catch(Exception e){
            return ApiResponse.error(e.getMessage());
        }
    }
    // 2. 게시글 목록 조회
    @GetMapping
    public ApiResponse<List<Post>> getPosts(){
        try {
            List<Post> posts = postService.getPosts();
            return ApiResponse.success(posts);
        } catch(Exception e){
            return ApiResponse.error(e.getMessage());
        }
    }
    // 3. 게시글 단건 조회
    @GetMapping(path="/{postId}")
    public ApiResponse<Post> getPost(
            @PathVariable(name="postId") Long postId
    ){
        try {
            Post post = postService.getPost(postId);
            return ApiResponse.success(post);
        } catch(Exception e){
            return ApiResponse.error(e.getMessage());
        }
    }
    // 4. 게시글 수정
    @PatchMapping(path="/{postId}")
    public ApiResponse<Void> updatePost(
            @PathVariable(name="postId") Long postId,
            @RequestBody(required = true) PostUpdateRequest request
    ){
        try {
            postService.updatePost(postId, request);
            return ApiResponse.success("게시글이 수정되었습니다.");
        } catch(Exception e){
            return ApiResponse.error(e.getMessage());
        }
    }
    // 5. 게시글 삭제
    @DeleteMapping(path="/{postId}")
    public ApiResponse<?> deletePost(
            @PathVariable(name="postId") Long postId
    ) {
        try{
            postService.deletePost(postId);
            return ApiResponse.success("게시글이 삭제되었습니다.");
        } catch(Exception e){
            return ApiResponse.error(e.getMessage());
        }
    }
}
