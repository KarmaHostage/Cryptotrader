package com.cryptopurse.cryptotrader.papertrading.service;

import com.cryptopurse.cryptotrader.papertrading.domain.PaperTrade;
import com.cryptopurse.cryptotrader.papertrading.domain.PaperTradeConfiguration;
import com.cryptopurse.cryptotrader.papertrading.repository.PaperTradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaperTradeService {

    @Autowired
    private PaperTradeRepository paperTradeRepository;

    public Optional<PaperTrade> findLastTrade(final PaperTradeConfiguration paperTradeConfiguration) {
        return paperTradeRepository.findTop1ByPaperTradeConfigurationOrderByPlacedAtDesc(paperTradeConfiguration);
    }

    public PaperTrade createTrade(final PaperTrade paperTrade) {
        return paperTradeRepository.save(paperTrade);
    }


}
