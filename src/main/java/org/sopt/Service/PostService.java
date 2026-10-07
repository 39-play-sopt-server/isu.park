package org.sopt.Service;

import org.sopt.Model.Post;
import org.sopt.Repository.HashMapPostRepository;
import org.sopt.Repository.PostRepository;

import java.util.List;
import java.util.Optional;

// 비즈니스 로직 담당
public class PostService {
    private PostRepository postRepository;
    public PostService(PostRepository postRepository) {   // 의존성 주입
        this.postRepository = postRepository;
    }

    // 1. 게시글 생성
    public void createPost(String title, String content){
        // null 및 공백 문자열 검증
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("게시글 제목은 필수 입력 항목입니다.");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("게시글 본문은 필수 입력 항목입니다.");
        }
        // 검증 통과 후, 게시글 생성
        Post post = new Post(title, content);
        postRepository.save(post);
    }

    // 2. 게시글 목록 조회
    public List<Post> getPosts(){
        List<Post> posts = postRepository.findAll();
        if(posts.isEmpty()) {
            throw new NullPointerException("게시글이 없습니다.");
        }
        return posts;
    }
    // 3. 게시글 단건 조회
    public Post getPost(int id){
        if(postRepository.findAll().isEmpty()) {
            throw new NullPointerException("게시글이 없습니다.");
        }
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 게시물입니다"));
        return post;
    }
    // 4. 게시글 수정
    public void updatePost(int id, String title, String content){
        if(postRepository.findAll().isEmpty()) {
            throw new NullPointerException("게시글이 없습니다.");
        }
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 게시물입니다"));
        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);
    }
    // 5. 게시글 삭제
    public void deletePost(int id){
        if(postRepository.findAll().isEmpty()) {
            throw new NullPointerException("게시글이 없습니다.");
        }
        postRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 게시물입니다"));
        postRepository.deleteById(id);
    }
}
