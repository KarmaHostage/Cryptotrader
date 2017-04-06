package com.karmahostage.cryptotrader.advice.service;

import com.karmahostage.cryptotrader.advice.domain.AdviceEnum;
import com.karmahostage.cryptotrader.advice.domain.GeneralAdvice;
import com.karmahostage.cryptotrader.advice.domain.StrategyPeriod;
import com.karmahostage.cryptotrader.advice.domain.StrategyType;
import com.karmahostage.cryptotrader.advice.repository.GeneralAdviceRepository;
import com.karmahostage.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.karmahostage.cryptotrader.market.domain.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class GeneralAdviceService {

    @Autowired
    private GeneralAdviceRepository generalAdviceRepository;

    public Optional<GeneralAdvice> findByCurrencyPairAndStrategyPeriodAndStrategyType(
            CurrencyPair currencyPair,
            StrategyPeriod strategyPeriod,
            StrategyType strategyType,
            SupportedExchanges exchange) {
        return generalAdviceRepository.findByCurrencyPairAndStrategyPeriodAndStrategyTypeAndExchange(
                currencyPair,
                strategyPeriod,
                strategyType,
                exchange
        );
    }

    @Transactional
    public void giveAdvice(StrategyType strategyType,
                           StrategyPeriod strategyPeriod,
                           AdviceEnum newAdvice,
                           CurrencyPair currencyPair,
                           SupportedExchanges exchange) {

        final Optional<GeneralAdvice> previousAdvice = generalAdviceRepository.findByCurrencyPairAndStrategyPeriodAndStrategyTypeAndExchange(
                currencyPair,
                strategyPeriod,
                strategyType,
                exchange
        );

        if (previousAdvice.isPresent()) {
            generalAdviceRepository.save(
                    previousAdvice.get()
                            .setConfirmations(
                                    calculateConfirmations(newAdvice, previousAdvice.get())
                            )
                            .setStrategyTimeFirstOccurrence(
                                    calculateStrategyTimeFirstOccurrence(newAdvice, previousAdvice.get())
                            )
                            .setStrategyTime(calculateStrategyTime(newAdvice, previousAdvice.get()))
                            .setAdvice(newAdvice)
                            .setExchange(SupportedExchanges.KRAKEN)
            );
        } else {
            generalAdviceRepository.save(
                    newAdvice(strategyType, strategyPeriod, newAdvice, currencyPair, exchange)
            );
        }
    }

    public Date calculateStrategyTime(final AdviceEnum advice, final GeneralAdvice previousAdvice) {
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

    public GeneralAdvice newAdvice(final StrategyType strategyType, final StrategyPeriod strategyPeriod, final AdviceEnum advice, final CurrencyPair currencyPair, final SupportedExchanges exchange) {
        return new GeneralAdvice()
                .setStrategyType(strategyType)
                .setStrategyPeriod(strategyPeriod)
                .setConfirmations(0)
                .setAdvice(advice)
                .setStrategyTime(new Date())
                .setStrategyTimeFirstOccurrence(new Date())
                .setCurrencyPair(currencyPair)
                .setExchange(exchange);
    }

    public Date calculateStrategyTimeFirstOccurrence(final AdviceEnum advice,
                                                     final GeneralAdvice previousAdvice) {
        return previousAdvice.getAdvice().equals(advice) ? previousAdvice.getStrategyTimeFirstOccurrence() : new Date();
    }

    public int calculateConfirmations(final AdviceEnum advice, final GeneralAdvice previousAdvice) {
        if (previousAdvice.getAdvice().equals(advice)) {
            if (periodHasPassed(previousAdvice)) {
                return (previousAdvice.getConfirmations() + 1);
            } else {
                return previousAdvice.getConfirmations();
            }
        } else {
            return 0;
        }
    }

    private boolean periodHasPassed(final GeneralAdvice previousAdvice) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime threshold = now.minus(previousAdvice.getStrategyPeriod().getTimeframeInSeconds(), ChronoUnit.SECONDS);
        return (threshold.isAfter(LocalDateTime.ofInstant(previousAdvice.getStrategyTime().toInstant(), ZoneId.systemDefault())));
    }

}
