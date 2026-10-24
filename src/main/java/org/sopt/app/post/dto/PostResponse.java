package org.sopt.app.post.dto;

import org.sopt.app.post.entity.Category;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String title,
        String content,
        String author,
        Category category,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Integer views
) {
}
