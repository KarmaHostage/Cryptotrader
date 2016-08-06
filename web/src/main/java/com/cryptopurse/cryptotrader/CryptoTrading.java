package com.cryptopurse.cryptotrader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CryptoTrading {

    public static void main(String[] args) {
        SpringApplication cryptoPurse = new SpringApplication(CryptoTrading.class);
        cryptoPurse.addListeners(new ApplicationPidFileWriter("cryptotrading.pid"));
        cryptoPurse.run(args);
    }

}
