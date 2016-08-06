package com.cryptopurse.cryptotrader.usermanagement.utils;

import com.cryptopurse.cryptotrader.usermanagement.domain.CryptotraderUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityUtility {

    public Optional<CryptotraderUser> currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() instanceof CryptotraderUser) {
            return Optional.of((CryptotraderUser) authentication.getPrincipal());
        } else {
            return Optional.empty();
        }
    }

}