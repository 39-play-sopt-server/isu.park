package org.sopt.Dto;

import org.sopt.Entity.Category;

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
