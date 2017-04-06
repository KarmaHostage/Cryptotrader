package com.karmahostage.cryptotrader.account.controller.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class ForgotPasswordDto {

    @NotEmpty
    private String email;

    public String getEmail() {
        return email;
    }

    public ForgotPasswordDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
