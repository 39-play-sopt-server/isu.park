package org.sopt.Controller;

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

    public void run(){
        while(true){
            view.printMenu();
            int command = view.readCommand();
            switch (command) {
                case 1 -> createPost();
                case 2 -> getPosts();
                case 3 -> getPost();
                case 4 -> updatePost();
                case 5 -> deletePost();
                case 6 -> {
                    view.printMessage("프로그램을 종료합니다.");
                    return;
                }
                default -> view.printMessage("잘못된 입력입니다.");
            }
        }
    }

    // 1. 게시글 작성
    public void createPost(){
        String title = view.readTitle();
        String content = view.readContent();
        try {
            postService.createPost(title,content);
            view.printMessage("게시글이 작성되었습니다.");
        } catch(IllegalArgumentException e){
            view.printMessage(e.getMessage());
        }
    }
    // 2. 게시글 목록 조회
    public void getPosts(){
        try {
            List<Post> posts = postService.getPosts();
            for(int i = 0; i < posts.size(); i++){
                view.printMessage((i+1)+". "+posts.get(i).getTitle());
            }
        } catch(Exception e){
            view.printMessage(e.getMessage());
        }
    }
    // 3. 게시글 단건 조회
    public void getPost(){
        try {
            int index = view.readPostNumber("조회할 게시글 번호: ");
            Post post = postService.getPost(index);
            view.printPost(post);
        } catch(Exception e){
            view.printMessage(e.getMessage());
        }
    }
    // 4. 게시글 수정
    public void updatePost(){
        try {
            int index = view.readPostNumber("수정할 게시글 번호: ");  // 게시물 검증을 먼저해야하는데
            String newTitle = view.readTitle();
            String newContent = view.readContent();
            postService.updatePost(index,newTitle,newContent);
            view.printMessage("게시글이 수정되었습니다.");
        } catch(Exception e){
            view.printMessage(e.getMessage());
        }
    }
    // 5. 게시글 삭제
    public void deletePost() {
        try{
            int index = view.readPostNumber("삭제할 게시글 번호: ");
            postService.deletePost(index);
            view.printMessage("게시글이 삭제되었습니다.");
        } catch(Exception e){
            view.printMessage(e.getMessage());
        }
    }
}
