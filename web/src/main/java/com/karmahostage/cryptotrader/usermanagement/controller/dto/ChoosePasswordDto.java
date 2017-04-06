package com.karmahostage.cryptotrader.usermanagement.controller.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class ChoosePasswordDto {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordConfirm;

    public String getPassword() {
        return password;
    }

    public ChoosePasswordDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public ChoosePasswordDto setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ChoosePasswordDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
