package org.sopt.app.post.dto;

import org.sopt.app.post.entity.Post;

public record PostListResponse(
        Long id,
        String title,
        String content
) {
    public static PostListResponse from(Post post) {
        return new PostListResponse(
                post.getId(),
                post.getTitle(),
                post.getContent()
        );
    }
}
