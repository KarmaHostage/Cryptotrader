package com.cryptopurse.cryptotrader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CryptoPurse {

    public static void main(String[] args) {
        SpringApplication cryptoPurse = new SpringApplication(CryptoPurse.class);
        cryptoPurse.addListeners(new ApplicationPidFileWriter("cryptopurse.pid"));
        cryptoPurse.run(args);
    }

}
