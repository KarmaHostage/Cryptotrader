package com.cryptopurse.cryptotrader.papertrading.service;

import com.cryptopurse.cryptotrader.papertrading.domain.PaperTradeConfiguration;
import com.cryptopurse.cryptotrader.papertrading.repository.PaperTradeConfigurationRepository;
import com.cryptopurse.cryptotrader.usermanagement.domain.CryptotraderUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperTradeConfigurationService {

    @Autowired
    private PaperTradeConfigurationRepository paperTradeConfigurationRepository;

    public List<PaperTradeConfiguration> findByUser(final CryptotraderUser cryptotraderUser) {
        return paperTradeConfigurationRepository.findAllByUser(cryptotraderUser);
    }

    public List<PaperTradeConfiguration> findAllActive() {
        return paperTradeConfigurationRepository.findAllActive();
    }

}
