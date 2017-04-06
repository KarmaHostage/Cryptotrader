package com.karmahostage.cryptotrader.usermanagement.controller.dto;


import org.hibernate.validator.constraints.NotEmpty;

public class RegistrationDto {

    private boolean tos;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordRepeat;

    public String getEmail() {
        return email;
    }

    public RegistrationDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegistrationDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public RegistrationDto setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
        return this;
    }

    public boolean isTos() {
        return tos;
    }

    public RegistrationDto setTos(boolean tos) {
        this.tos = tos;
        return this;
    }
}
