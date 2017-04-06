package com.karmahostage.cryptotrader.usermanagement.domain;

public enum RoleEnum {

    USER_ROLE(1L, "ROLE_USER");


    private final Long id;
    private final String userRole;

    RoleEnum(final Long id, final String userRole) {
        this.id = id;
        this.userRole = userRole;
    }

    public Long getId() {
        return id;
    }

    public String getUserRole() {
        return userRole;
    }
}