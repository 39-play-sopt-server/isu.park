package org.sopt.app.post.dto;

import org.sopt.app.post.entity.Category;

public record PostCreateRequest(
        String title,
        String content,
        Long memberId,
        Category category
){}
