package org.sopt.app.post.service;

import jakarta.transaction.Transactional;
import org.sopt.app.post.dto.PostCreateRequest;
import org.sopt.app.post.dto.PostListResponse;
import org.sopt.app.post.dto.PostResponse;
import org.sopt.app.post.dto.PostUpdateRequest;
import org.sopt.app.post.entity.Category;
import org.sopt.app.post.entity.Post;
import org.sopt.app.post.repository.PostRepository;
import org.sopt.global.common.ErrorCode;
import org.sopt.global.exception.CustomException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {   // 의존성 주입
        this.postRepository = postRepository;
    }

    // 1. 게시글 생성
    public void createPost(PostCreateRequest request) {
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
    public List<PostListResponse> getPosts(int page, int size) {
        //List<Post> posts = postRepository.findWithPaging(page, size);
        Pageable pageable = PageRequest.of(page, size);
        List<Post> posts = postRepository.findAll(pageable).getContent();
        List<PostListResponse> responseList = new ArrayList<>();
        for (Post post : posts) {
            PostListResponse response = PostListResponse.from(post);
            responseList.add(response);
        }
        return responseList;
    }
    // 3. 게시글 단건 조회 -> 상세 조회
    @Transactional
    public PostResponse getPost(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        post.increaseViews();  // 조회수 발생 -> 엔티티에 증가 위임
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getCategory(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getViews()
        );
    }
    // 카테고리별 게시글 조회
    public List<Post> getPostsByCategory(Category category) {
        List<Post> posts = postRepository.findByCategory(category);
        return posts;
    }
    // 4. 게시글 수정
    public void updatePost(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        post.update(
                request.title(),
                request.content(),
                request.author(),
                request.category(),
                LocalDate.now()
        );
        postRepository.save(post);
    }
    // 5. 게시글 삭제
    public void deletePost(Long id){
        postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        postRepository.deleteById(id);
    }
}
