package org.sopt.Dto;

import org.sopt.Entity.Category;

public record PostCreateRequest(
        String title,
        String content,
        String author,
        Category category
){}
