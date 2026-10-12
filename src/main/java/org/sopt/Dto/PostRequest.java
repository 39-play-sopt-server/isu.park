package org.sopt.Dto;

import java.time.LocalDate;

public record PostRequest(
        String title,
        String content,
        String author,
        String category
){}
