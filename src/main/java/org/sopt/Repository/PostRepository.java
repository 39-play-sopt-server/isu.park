package org.sopt.Repository;

import org.sopt.Model.Post;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository {
    void save(Post post); // 저장
    List<Post> findAll();  // 목록 조회
    Optional<Post> findById(int id);  // 단건 조회
    void deleteById(int id);  // 삭제
}
