package com.karmahostage.cryptotrader.usermanagement.domain;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cryptotrader_user")
public class CryptotraderUser implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "account_non_expired", nullable = false,
            columnDefinition = "boolean default true")
    private boolean accountNonExpired = true;

    @Column(name = "account_non_locked", nullable = false,
            columnDefinition = "boolean default true")
    private boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired", nullable = false,
            columnDefinition = "boolean default true")
    private boolean credentialsNonExpired = true;

    @Column(name = "enabled", nullable = false,
            columnDefinition = "boolean default true")
    private boolean enabled = true;

    @Column(name = "email")
    private String email;

    @Column(name = "activationcode")
    private String activationCode;

    @Column(name = "password_reset_code")
    private String passwordResetCode;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {
                    @JoinColumn
                            (
                                    name = "user_id",
                                    referencedColumnName = "id"
                            )
            },
            inverseJoinColumns =
                    {
                            @JoinColumn
                                    (
                                            name = "role_id",
                                            referencedColumnName = "id"
                                    )
                    }
    )
    private Set<Role> authorities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public CryptotraderUser setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public CryptotraderUser setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public CryptotraderUser setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public CryptotraderUser setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
        return this;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public CryptotraderUser setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
        return this;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public CryptotraderUser setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
        return this;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public CryptotraderUser setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }


    public String getEmail() {
        return email;
    }

    public CryptotraderUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public CryptotraderUser setActivationCode(String activationCode) {
        this.activationCode = activationCode;
        return this;
    }

    @Override
    public Set<Role> getAuthorities() {
        return authorities;
    }

    public CryptotraderUser setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
        return this;
    }

    public String getPasswordResetCode() {
        return passwordResetCode;
    }

    public CryptotraderUser setPasswordResetCode(String passwordResetCode) {
        this.passwordResetCode = passwordResetCode;
        return this;
    }

    public boolean isSame(final CryptotraderUser user) {
        return this.getId().equals(user.getId()) && this.getEmail().equals(user.getEmail());
    }
}
