package org.sopt.app.post.dto;

import org.sopt.app.post.entity.Category;

import java.time.LocalDate;

public record PostResponse(
        Long id,
        String title,
        String content,
        String author,
        Category category,
        LocalDate createdAt,
        LocalDate updatedAt
) {
}
