package org.sopt.app.post.repository;

import org.sopt.app.post.entity.Category;
import org.sopt.app.post.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository {
    void save(Post post); // 저장
    List<Post> findAll();  // 목록 조회
    List<Post> findWithPaging(int page, int size);
    List<Post> findByCategory(Category category);
    Optional<Post> findById(Long id);  // 단건 조회
    void deleteById(Long id);  // 삭제
}
