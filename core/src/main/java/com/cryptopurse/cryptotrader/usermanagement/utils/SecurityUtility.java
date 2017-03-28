package com.cryptopurse.cryptotrader.usermanagement.utils;

import com.cryptopurse.cryptotrader.usermanagement.domain.CryptotraderUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


public class SecurityUtility {

    public static Optional<CryptotraderUser> currentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() instanceof CryptotraderUser) {
            return Optional.of((CryptotraderUser) authentication.getPrincipal());
        } else {
            return Optional.empty();
        }
    }

    public static boolean userIsAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null
                || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser");
    }


}