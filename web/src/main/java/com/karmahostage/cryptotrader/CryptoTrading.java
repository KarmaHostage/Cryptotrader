package com.karmahostage.cryptotrader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CryptoTrading {

    public static void main(String[] args) {
        final SpringApplication myApplication = new SpringApplication(CryptoTrading.class);
        myApplication.run(args);
    }

}
