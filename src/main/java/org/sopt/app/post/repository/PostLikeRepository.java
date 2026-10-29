package org.sopt.app.post.repository;

import org.sopt.app.member.entity.Member;
import org.sopt.app.post.entity.Post;
import org.sopt.app.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    void deleteByMemberAndPost(Member member, Post post);
}
