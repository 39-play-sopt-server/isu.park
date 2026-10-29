package org.sopt.app.post.service;

import jakarta.transaction.Transactional;
import org.sopt.app.member.entity.Member;
import org.sopt.app.member.repository.MemberRepository;
import org.sopt.app.post.dto.*;
import org.sopt.app.post.entity.Category;
import org.sopt.app.post.entity.Post;
import org.sopt.app.post.repository.PostRepository;
import org.sopt.global.common.ErrorCode;
import org.sopt.global.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    public PostService(PostRepository postRepository, MemberRepository memberRepository) {   // 의존성 주입
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }
    @Transactional
    public void createPost(PostCreateRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_EXIST));
        Post post = new Post(
                request.title(),
                request.content(),
                member,
                request.category()
        );
        postRepository.save(post);
    }
    public List<PostListResponse> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        List<Post> posts = postRepository.findAll(pageable).getContent();
        List<PostListResponse> responseList = new ArrayList<>();
        for (Post post : posts) {
            PostListResponse response = PostListResponse.from(post);
            responseList.add(response);
        }
        return responseList;
    }
    @Transactional
    public PostResponse getPost(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        post.increaseViews();  // 조회수 발생 -> 엔티티에 증가 위임
        return post.toResponse();
    }
    // 동적 쿼리 -> 게시글 필터링
    public List<PostResponse> getPostBySearch(Category category, Long authorId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Post> postPage = postRepository.findPostsByDynamicFilter(category, authorId, pageable);

        List<PostResponse> responseList = new ArrayList<>();
        for (Post post : postPage.getContent()) {
            responseList.add(post.toResponse());
        }
        return responseList;

    }
    // 제목으로 게시글 검색
    public List<PostResponse> getPostsByTitle(String title) {
        List<Post> posts = postRepository.findByTitleContainingOrderByCreatedAtDesc(title);
        List<PostResponse> responseList = new ArrayList<>();
        for (Post post : posts) {
            responseList.add(post.toResponse());
        }
        return responseList;
    }
    public void updatePost(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        post.update(
                request.title(),
                request.content(),
                request.category()
        );
        postRepository.save(post);
    }
    @Transactional
    public void deletePost(Long id){
        postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        postRepository.deleteById(id);
    }
}
