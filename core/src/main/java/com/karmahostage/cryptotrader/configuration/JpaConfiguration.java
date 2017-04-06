package com.karmahostage.cryptotrader.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.karmahostage.cryptotrader")
@Configuration
public class JpaConfiguration { }