package com.cryptopurse.cryptotrader.account.controller.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class PasswordResetDto {

    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordConfirm;

    public String getPassword() {
        return password;
    }

    public PasswordResetDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public PasswordResetDto setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
        return this;
    }
}
