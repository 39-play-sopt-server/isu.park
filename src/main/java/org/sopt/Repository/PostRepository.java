package org.sopt.Repository;

import org.sopt.Entity.Post;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository {
    void save(Post post); // 저장
    List<Post> findAll();  // 목록 조회
    Optional<Post> findById(Long id);  // 단건 조회
    void deleteById(Long id);  // 삭제
}
