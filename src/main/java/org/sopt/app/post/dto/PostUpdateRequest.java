package org.sopt.app.post.dto;

import org.sopt.app.post.entity.Category;

public record PostUpdateRequest(
        String title,
        String content,
        Category category
) {}
