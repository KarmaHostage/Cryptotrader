package com.cryptopurse.cryptotrader.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.cryptopurse.cryptotrader")
@Configuration
public class JpaConfiguration { }