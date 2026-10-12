package org.sopt.Dto;

import org.sopt.Entity.Category;

public record PostUpdateRequest(
        String title,
        String content,
        String author,
        Category category
) {}
