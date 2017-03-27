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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
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
                           AdviceEnum newAdvice,
                           CurrencyPair currencyPair) {
        final Optional<KrakenGeneralAdvice> previousAdvice = krakenGeneralAdviceRepository.findByCurrencyPairAndStrategyPeriodAndStrategyType(
                currencyPair,
                strategyPeriod,
                strategyType
        );

        if (previousAdvice.isPresent()) {
            krakenGeneralAdviceRepository.save(
                    previousAdvice.get()
                            .setConfirmations(
                                    calculateConfirmations(newAdvice, previousAdvice.get())
                            )
                            .setStrategyTimeFirstOccurrence(
                                    calculateStrategyTimeFirstOccurrence(newAdvice, previousAdvice.get())
                            )
                            .setStrategyTime(calculateStrategyTime(newAdvice, previousAdvice.get()))
                            .setAdvice(newAdvice)
            );
        } else {
            krakenGeneralAdviceRepository.save(
                    newAdvice(strategyType, strategyPeriod, newAdvice, currencyPair)
            );
        }
    }

    public Date calculateStrategyTime(final AdviceEnum advice, final KrakenGeneralAdvice previousAdvice) {
        if (previousAdvice.getAdvice().equals(advice)) {
            if (periodHasPassed(previousAdvice)) {
                return new Date();
            } else {
                return previousAdvice.getStrategyTime();
            }
        } else {
            return new Date();
        }
    }

    public KrakenGeneralAdvice newAdvice(final StrategyType strategyType, final StrategyPeriod strategyPeriod, final AdviceEnum advice, final CurrencyPair currencyPair) {
        return new KrakenGeneralAdvice()
                .setStrategyType(strategyType)
                .setStrategyPeriod(strategyPeriod)
                .setConfirmations(0)
                .setAdvice(advice)
                .setStrategyTime(new Date())
                .setStrategyTimeFirstOccurrence(new Date())
                .setCurrencyPair(currencyPair);
    }

    public Date calculateStrategyTimeFirstOccurrence(final AdviceEnum advice,
                                                     final KrakenGeneralAdvice previousAdvice) {
        return previousAdvice.getAdvice().equals(advice) ? previousAdvice.getStrategyTimeFirstOccurrence() : new Date();
    }

    public int calculateConfirmations(final AdviceEnum advice, final KrakenGeneralAdvice previousAdvice) {
        if (previousAdvice.getAdvice().equals(advice) && periodHasPassed(previousAdvice)) {
            if (periodHasPassed(previousAdvice)) {
                return (previousAdvice.getConfirmations() + 1);
            } else {
                return previousAdvice.getConfirmations();
            }
        } else {
            return 0;
        }
    }

    private boolean periodHasPassed(final KrakenGeneralAdvice previousAdvice) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime threshold = now.minus(previousAdvice.getStrategyPeriod().getTimeframeInSeconds(), ChronoUnit.SECONDS);
        return (threshold.isAfter(LocalDateTime.ofInstant(previousAdvice.getStrategyTime().toInstant(), ZoneId.systemDefault())));
    }

}
