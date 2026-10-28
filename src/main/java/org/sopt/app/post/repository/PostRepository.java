package org.sopt.app.post.repository;

import org.sopt.app.post.entity.Category;
import org.sopt.app.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findAll();
    List<Post> findByCategory(Category category);
    List<Post> findByTitleContainingOrderByCreatedAtDesc(String title);
    Optional<Post> findById(Long id);
    void deleteById(Long id);
}
