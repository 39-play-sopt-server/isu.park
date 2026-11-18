package org.sopt.app.post.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.app.member.entity.Member;
import org.sopt.app.member.repository.MemberRepository;
import org.sopt.app.post.entity.Post;
import org.sopt.app.post.entity.PostLike;
import org.sopt.app.post.repository.PostLikeRepository;
import org.sopt.app.post.repository.PostRepository;
import org.sopt.global.common.ErrorCode;
import org.sopt.global.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;

    public void createPostLike(Long postId, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomException(ErrorCode.POST_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_EXIST));

        if(postLikeRepository.existsByMember_IdAndPost_Id(memberId, postId)){
            throw new CustomException(ErrorCode.POST_ALREADY_LIKED);
        }
        PostLike postLike = new PostLike(post, member);
        postLikeRepository.save(postLike);
        post.increaseLikes();
    }
    public void cancelPostLike(Long postId, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomException(ErrorCode.POST_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_EXIST));
        postLikeRepository.deleteByMemberAndPost(member,post);
        post.decreaseLikes();
    }

}
