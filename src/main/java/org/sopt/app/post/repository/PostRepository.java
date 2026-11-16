package org.sopt.app.post.repository;

import org.sopt.app.post.entity.Category;
import org.sopt.app.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findByTitleContainingOrderByCreatedAtDesc(String title);
}
