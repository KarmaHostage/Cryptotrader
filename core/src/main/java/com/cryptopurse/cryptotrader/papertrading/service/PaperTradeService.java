package com.cryptopurse.cryptotrader.papertrading.service;

import com.cryptopurse.cryptotrader.papertrading.domain.PaperTrade;
import com.cryptopurse.cryptotrader.papertrading.domain.PaperTradeConfiguration;
import com.cryptopurse.cryptotrader.papertrading.repository.PaperTradeRepository;
import com.cryptopurse.cryptotrader.usermanagement.domain.CryptotraderUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaperTradeService {

    @Autowired
    private PaperTradeRepository paperTradeRepository;

    @Transactional(readOnly = true)
    public Optional<PaperTrade> findLastTrade(final PaperTradeConfiguration paperTradeConfiguration) {
        return paperTradeRepository.findTop1ByPaperTradeConfigurationOrderByPlacedAtDesc(paperTradeConfiguration)
                .findFirst();
    }

    @Transactional(readOnly = true)
    public Map<PaperTradeConfiguration, List<PaperTrade>> findByUser(final CryptotraderUser user) {
        return paperTradeRepository.findPaperTradeByPaperTradeConfigurationUser(user)
                .stream()
                .collect(Collectors.groupingBy(PaperTrade::getPaperTradeConfiguration));
    }

    @Transactional
    public PaperTrade createTrade(final PaperTrade paperTrade) {
        return paperTradeRepository.save(paperTrade);
    }


}
