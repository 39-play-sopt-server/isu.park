package org.sopt.app.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.global.common.BaseEntity;
import org.sopt.global.common.ErrorCode;
import org.sopt.global.exception.CustomException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean withdrawn = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role = Role.USER;

    public Member(
            String name,
            String email,
            String password
    ) {
        validate(email, password);
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = Role.USER;      // 가입 경로로는 항상 USER
    }

    public void validate(String email, String password) {
        if(email == null || email.isEmpty()){
            throw new CustomException(ErrorCode.MEMBER_EMAIL_EMPTY);
        }
        if(password == null || password.isEmpty()){
            throw new CustomException(ErrorCode.MEMBER_PW_EMPTY);
        }
    }
    public void anonymize(  // 회원 탈퇴시, 익명화 처리
            String anonymousName,
            String anonymousEmail,
            String encodedRandomPassword
    ){
        this.name = anonymousName;
        this.email = anonymousEmail;
        this.password = encodedRandomPassword;
        this.withdrawn = true;
    }
    public boolean isAdmin() {
        return Role.ADMIN.equals(role);
    }
}
