package org.sopt.app.member.entity;

import jakarta.persistence.*;
import org.sopt.global.common.BaseEntity;
import org.sopt.global.common.ErrorCode;
import org.sopt.global.exception.CustomException;

@Entity
public class Member extends BaseEntity {
    private String name;
    private String email;
    private String password;

    protected  Member() {}
    public Member(
            String name,
            String email,
            String password
    ) {
        validate(email, password);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void validate(String email, String password) {
        if(email == null || email.isEmpty()){
            throw new CustomException(ErrorCode.MEMBER_EMAIL_EMPTY);
        }
        if(password == null || password.isEmpty()){
            throw new CustomException(ErrorCode.MEMBER_PW_EMPTY);
        }
    }
    public String getName(){ return name;}
}
