package org.sopt.Service;

import org.sopt.Dto.PostCreateRequest;
import org.sopt.Dto.PostUpdateRequest;
import org.sopt.Entity.Category;
import org.sopt.Entity.Post;
import org.sopt.Repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {   // 의존성 주입
        this.postRepository = postRepository;
    }

    // 1. 게시글 생성
    public void createPost(PostCreateRequest request) {
        // null 및 공백 문자열 검증
        if (request.title() == null || request.title().isBlank()) {
            throw new IllegalArgumentException("게시글 제목은 필수 입력 항목입니다.");
        }
        if (request.content() == null || request.content().isBlank()) {
            throw new IllegalArgumentException("게시글 본문은 필수 입력 항목입니다.");
        }
        // 검증 통과 후, 게시글 생성
        Post post = new Post(
                request.title(),
                request.content(),
                request.author(),
                LocalDate.now(), // 최초 생성시 생성일 = 수정일
                LocalDate.now(),
                request.category()
        );
        postRepository.save(post);
    }
    // 2. 게시글 목록 조회
    public List<Post> getPosts(){
        List<Post> posts = postRepository.findAll();
        if(posts.isEmpty()) {
            throw new IllegalArgumentException("게시글이 없습니다.");
        }
        return posts;
    }
    // 3. 게시글 단건 조회
    public Post getPost(int id){
        if(postRepository.findAll().isEmpty()) {
            throw new IllegalArgumentException("게시글이 없습니다.");
        }
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다"));
        return post;
    }
    // 4. 게시글 수정
    public void updatePost(int id, PostUpdateRequest request) {
        if(postRepository.findAll().isEmpty()) {
            throw new IllegalArgumentException("게시글이 없습니다.");
        }
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다"));
        post.setTitle(request.title());
        post.setContent(request.content());
        post.setAuthor(request.author());
        post.setCategory(request.category());
        post.setUpdatedAt(LocalDate.now());
        postRepository.save(post);
    }
    // 5. 게시글 삭제
    public void deletePost(int id){
        if(postRepository.findAll().isEmpty()) {
            throw new IllegalArgumentException("게시글이 없습니다.");
        }
        postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다"));
        postRepository.deleteById(id);
    }
}
