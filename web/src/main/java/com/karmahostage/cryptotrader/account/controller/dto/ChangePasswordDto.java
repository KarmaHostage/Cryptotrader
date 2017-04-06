package com.karmahostage.cryptotrader.account.controller.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class ChangePasswordDto {
    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordConfirm;

    public String getPassword() {
        return password;
    }

    public ChangePasswordDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public ChangePasswordDto setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
        return this;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public ChangePasswordDto setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }
}
