package org.sopt;

import org.sopt.Controller.PostController;
import org.sopt.Repository.HashMapPostRepository;
import org.sopt.Repository.PostRepository;
import org.sopt.Service.PostService;
import org.sopt.View.PostView;

// 프로젝트의 시작점 -> 전체 애플리케이션 실행

public class Main {
    public static void main(String[] args) {
        PostView view = new PostView();
        PostRepository postRepository =  new HashMapPostRepository();
        PostService postService = new PostService(postRepository);
        PostController controller = new PostController(view, postService);
        controller.run();
    }
}