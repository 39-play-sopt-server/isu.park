package org.sopt.app.post.repository;

import org.sopt.app.post.entity.Category;
import org.sopt.app.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> findPostsByDynamicFilter(Category category, Long authorId, Pageable pageable);
}
