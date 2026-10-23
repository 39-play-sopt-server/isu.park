package org.sopt.app.member.dto;

public record MemberCreateRequest(
        String name,
        String email,
        String password
) {
}
