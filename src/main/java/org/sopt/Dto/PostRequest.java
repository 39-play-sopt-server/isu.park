package org.sopt.Dto;

public record PostRequest(
        String title,
        String content,
        String author,
        String category
){}
