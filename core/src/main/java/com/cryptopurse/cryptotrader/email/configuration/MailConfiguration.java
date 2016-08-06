package com.cryptopurse.cryptotrader.email.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfiguration {


    @Value("${com.cryptopurse.mail.domain}")
    private String domain;
    @Value("${com.cryptopurse.mail.apikey}")
    private String apiKey;
    @Value("${com.cryptopurse.mail.sender}")
    private String sender;


    @Bean
    public net.sargue.mailgun.Configuration configurationProvider() {
        return new net.sargue.mailgun.Configuration()
                .apiKey(apiKey)
                .domain(domain)
                .from(sender);
    }
}