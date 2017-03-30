package com.cryptopurse.cryptotrader.papertrading.service;

import com.cryptopurse.cryptotrader.papertrading.domain.PaperTradeConfiguration;
import com.cryptopurse.cryptotrader.papertrading.repository.PaperTradeConfigurationRepository;
import com.cryptopurse.cryptotrader.usermanagement.domain.CryptotraderUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaperTradeConfigurationService {

    @Autowired
    private PaperTradeConfigurationRepository paperTradeConfigurationRepository;

    @Transactional(readOnly = true)
    public List<PaperTradeConfiguration> findByUser(final CryptotraderUser cryptotraderUser) {
        return paperTradeConfigurationRepository.findAllByUser(cryptotraderUser);
    }

    @Transactional(readOnly = true)
    public List<PaperTradeConfiguration> findAllActive() {
        return paperTradeConfigurationRepository.findAllActive();
    }

}
