package com.m2a.web.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {

    ROLE_ADMIN("ROLE_ADMIN", 1L),
    ROLE_USER("ROLE_USER", 2L);

    private final String name;
    private final Long id;

    RoleEnum(String name, Long id) {
        this.name = name;
        this.id = id;
    }
}