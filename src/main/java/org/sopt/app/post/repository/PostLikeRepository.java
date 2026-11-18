package org.sopt.app.post.repository;

import org.sopt.app.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Long deleteByMember_IdAndPost_Id(Long memberId, Long postId);
    boolean existsByMember_IdAndPost_Id(Long memberId, Long postId);
}
