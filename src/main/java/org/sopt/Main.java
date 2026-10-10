package org.sopt;

import org.sopt.Controller.PostController;
import org.sopt.Dto.ApiResponse;
import org.sopt.Model.Post;
import org.sopt.Repository.HashMapPostRepository;
import org.sopt.Repository.PostRepository;
import org.sopt.Service.PostService;
import org.sopt.View.PostView;

import java.util.List;

// 프로젝트의 시작점 -> 전체 애플리케이션 실행
public class Main {
    public static void main(String[] args) {
        // 클라이언트 객체 생성
        PostView postView = new PostView();

        // 서버 객체 생성
        PostRepository postRepository =  new HashMapPostRepository();
        PostService postService = new PostService(postRepository);
        PostController postController = new PostController(postService);

        while(true){
            postView.printMenu();
            int command = postView.readCommand();
            switch (command) {
                case 1 -> {  // 게시물 생성
                    String title = postView.readTitle();
                    String content = postView.readContent();
                    String author = postView.readAuthor();
                    String category = postView.readCategory();
                    ApiResponse<?> response = postController.createPost(title, content, author, category);
                    postView.renderResponse(response);
                }
                case 2 -> {  // 게시물 목록 조회
                    ApiResponse<List<Post>> response = postController.getPosts();
                    postView.renderPostListResponse(response);
                }
                case 3 -> {  // 게시물 단건 조회
                    int id = postView.readPostNumber("조회할 게시글 번호: ");
                    ApiResponse<Post> response = postController.getPost(id);
                    postView.renderPostResponse(response);
                }
                case 4 ->{  // 게시물 수정
                    int id = postView.readPostNumber("수정할 게시글 번호: ");
                    String newTitle = postView.readTitle();
                    String newContent = postView.readContent();
                    String newAuthor = postView.readAuthor();
                    String newCategory = postView.readCategory();
                    ApiResponse<?> response = postController.updatePost(id, newTitle, newContent, newAuthor, newCategory);
                    postView.renderResponse(response);
                }
                case 5 -> {  // 게시물 삭제
                    int id = postView.readPostNumber("삭제할 게시글 번호: ");
                    ApiResponse<?> response = postController.deletePost(id);
                    postView.renderResponse(response);
                }
                case 6 -> {
                    postView.printMessage("프로그램을 종료합니다.");
                    return;
                }
                default -> postView.printMessage("잘못된 입력입니다.");
            }
        }
    }
}