package org.sopt.Dto;

public record PostCreateRequest(
        String title,
        String content,
        String author,
        String category
){}
