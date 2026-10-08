package org.sopt.Controller;

import org.sopt.Dto.ApiResponse;
import org.sopt.Model.Post;
import org.sopt.Service.PostService;
import org.sopt.View.PostView;
import java.util.List;

// 요청 제어 및 흐름 중개
// View로부터 전달받은 명령/입력값을 알맞은 Service 메소드를 호출
public class PostController {
    private final PostView view;
    private PostService postService;
    public PostController(PostView view, PostService postService) {  // 의존성 주입
        this.view = view;
        this.postService = postService;
    }
    // 1. 게시글 작성
    public ApiResponse<Void> createPost(String title, String content, String author, String category){
        try {
            postService.createPost(title,content,author,category);
            return ApiResponse.success("게시글이 작성되었습니다.");
        } catch(Exception e){
            return ApiResponse.error(e.getMessage());
        }
    }
    // 2. 게시글 목록 조회
    public ApiResponse<List<Post>> getPosts(){
        try {
            List<Post> posts = postService.getPosts();
            return ApiResponse.success(posts);
        } catch(Exception e){
            return ApiResponse.error(e.getMessage());
        }
    }
    // 3. 게시글 단건 조회
    public ApiResponse<Post> getPost(int id){
        try {
            Post post = postService.getPost(id);
            return ApiResponse.success(post);
        } catch(Exception e){
            return ApiResponse.error(e.getMessage());
        }
    }
    // 4. 게시글 수정
    public ApiResponse<Void> updatePost(int id, String newTitle, String newContent, String newAuthor, String newCategory){
        try {
            postService.updatePost(id,newTitle,newContent,newAuthor, newCategory);
            return ApiResponse.success("게시글이 수정되었습니다.");
        } catch(Exception e){
            return ApiResponse.error(e.getMessage());
        }
    }
    // 5. 게시글 삭제
    public ApiResponse<Void> deletePost(int id) {
        try{
            postService.deletePost(id);
            return ApiResponse.success("게시글이 삭제되었습니다.");
        } catch(Exception e){
            return ApiResponse.error(e.getMessage());
        }
    }
}
