package org.sopt.app.member.dto;

import org.sopt.app.member.entity.Role;

public record AdminMemberResponse(
        Long memberId,
        String name,
        String email,
        Role role,
        boolean withdrawn
) { }
