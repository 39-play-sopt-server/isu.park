package org.sopt.Controller;

import org.sopt.Dto.ApiResponse;
import org.sopt.Dto.PostRequest;
import org.sopt.Model.Post;
import org.sopt.Service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController vs @Controller의 차이?
@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {
    private PostService postService;
    public PostController(PostService postService) {  // 의존성 주입
        this.postService = postService;
    }
    // 1. 게시글 작성
    @PostMapping
    public ApiResponse<Void> createPost(
            @RequestBody(required = true) PostRequest request
            //String title, String content, String author, String category
    ){
        try {
            postService.createPost(request.title(), request.author(), request.category(), request.content());
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
            @PathVariable Integer postId
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
            @PathVariable Integer postId,
            @RequestBody(required = true) PostRequest request
    ){
        try {
            postService.updatePost(postId,request.title(), request.author(), request.category(), request.content());
            return ApiResponse.success("게시글이 수정되었습니다.");
        } catch(Exception e){
            return ApiResponse.error(e.getMessage());
        }
    }
    // 5. 게시글 삭제
    @DeleteMapping(path="/{postId}")
    public ApiResponse<Void> deletePost(
            @PathVariable Integer postId
    ) {
        try{
            postService.deletePost(postId);
            return ApiResponse.success("게시글이 삭제되었습니다.");
        } catch(Exception e){
            return ApiResponse.error(e.getMessage());
        }
    }
}
