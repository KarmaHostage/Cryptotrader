package com.karmahostage.cryptotrader.usermanagement.service;

import com.karmahostage.cryptotrader.usermanagement.domain.CryptotraderUser;
import com.karmahostage.cryptotrader.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CryptotraderUser> byUsername = userRepository.findByUsername(username);
        if(byUsername.isPresent()) {
            return byUsername.get();
        } else {
            throw new UsernameNotFoundException("Username was not found");
        }
    }
}