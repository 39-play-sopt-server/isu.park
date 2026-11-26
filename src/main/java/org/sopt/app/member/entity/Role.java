package org.sopt.app.member.entity;

public enum Role {
    USER, ADMIN;

    public String authority() {
        return "ROLE_" + name();
    }
}
