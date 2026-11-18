package org.sopt.app.post.dto;

import jakarta.validation.constraints.NotBlank;
import org.sopt.app.post.entity.Category;

public record PostCreateRequest(
        @NotBlank(message = "제목은 필수 입력 값입니다.")
        String title,
        @NotBlank(message = "본문은 필수 입력 값입니다.")
        String content,
        @NotBlank(message = "카테고리는 필수 입력 값입니다.")
        Category category
){}
