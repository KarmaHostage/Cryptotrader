package com.cryptopurse.cryptotrader.advice.service;

import com.cryptopurse.cryptotrader.advice.domain.AdviceEnum;
import com.cryptopurse.cryptotrader.advice.domain.KrakenGeneralAdvice;
import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.advice.domain.StrategyType;
import com.cryptopurse.cryptotrader.advice.repository.KrakenGeneralAdviceRepository;
import com.cryptopurse.cryptotrader.market.domain.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class KrakenGeneralAdviceService {

    @Autowired
    private KrakenGeneralAdviceRepository krakenGeneralAdviceRepository;

    public Optional<KrakenGeneralAdvice> findByCurrencyPairAndStrategyPeriodAndStrategyType(
            CurrencyPair currencyPair,
            StrategyPeriod strategyPeriod,
            StrategyType strategyType) {
        return krakenGeneralAdviceRepository.findByCurrencyPairAndStrategyPeriodAndStrategyType(
                currencyPair,
                strategyPeriod,
                strategyType
        );
    }

    @Transactional
    public void giveAdvice(StrategyType strategyType,
                           StrategyPeriod strategyPeriod,
                           AdviceEnum advice,
                           CurrencyPair currencyPair) {
        Optional<KrakenGeneralAdvice> byCurrencyPairAndStrategyPeriodAndStrategyType = krakenGeneralAdviceRepository.findByCurrencyPairAndStrategyPeriodAndStrategyType(
                currencyPair,
                strategyPeriod,
                strategyType
        );

        if (byCurrencyPairAndStrategyPeriodAndStrategyType.isPresent()) {
            krakenGeneralAdviceRepository.save(
                    byCurrencyPairAndStrategyPeriodAndStrategyType.get()
                            .setConfirmations(byCurrencyPairAndStrategyPeriodAndStrategyType.get().getAdvice().equals(advice) ? byCurrencyPairAndStrategyPeriodAndStrategyType.get().getConfirmations() + 1 : 0)
                            .setStrategyTime(byCurrencyPairAndStrategyPeriodAndStrategyType.get().getAdvice().equals(advice) ? byCurrencyPairAndStrategyPeriodAndStrategyType.get().getStrategyTime() : new Date())
                            .setAdvice(advice)
            );
        } else {
            krakenGeneralAdviceRepository.save(
                    new KrakenGeneralAdvice()
                            .setStrategyType(strategyType)
                            .setStrategyPeriod(strategyPeriod)
                            .setConfirmations(0)
                            .setAdvice(advice)
                            .setStrategyTime(new Date())
                            .setCurrencyPair(currencyPair)
            );
        }
    }

}
