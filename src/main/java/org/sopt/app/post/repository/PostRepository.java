package org.sopt.app.post.repository;

import org.sopt.app.post.entity.Category;
import org.sopt.app.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll();  // 목록 조회
    List<Post> findByCategory(Category category);
    Optional<Post> findById(Long id);  // 단건 조회
    void deleteById(Long id);  // 삭제
}
