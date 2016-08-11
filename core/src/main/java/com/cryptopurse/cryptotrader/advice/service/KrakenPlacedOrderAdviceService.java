package com.cryptopurse.cryptotrader.advice.service;

import com.cryptopurse.cryptotrader.advice.domain.KrakenPlacedOrderAdvice;
import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.advice.domain.StrategyType;
import com.cryptopurse.cryptotrader.advice.repository.KrakenPlacedOrderAdviceRepository;
import com.cryptopurse.cryptotrader.market.domain.KrakenPlacedOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class KrakenPlacedOrderAdviceService {

    @Autowired
    private KrakenPlacedOrderAdviceRepository krakenPlacedOrderAdviceRepository;

    @Transactional
    public void giveAdvice(KrakenPlacedOrder order,
                           StrategyType strategyType,
                           boolean shouldExit,
                           StrategyPeriod strategyPeriod) {
        Optional<KrakenPlacedOrderAdvice> byKrakenPlacedOrderAndStrategyType = krakenPlacedOrderAdviceRepository.findByKrakenPlacedOrderAndStrategyTypeAndStrategyPeriod(order, strategyType, strategyPeriod);
        if (shouldExit) {
            System.out.println("EXIT your position of " + order.getAmount() + "ETH of $" + order.getPrice() + " (" + strategyType + "-" + strategyPeriod + ")");
        } else {
            System.out.println("HOLD your position of " + order.getAmount() + "ETH of $" + order.getPrice() + " (" + strategyType + "-" + strategyPeriod + ")");
        }
        if (byKrakenPlacedOrderAndStrategyType.isPresent()) {
            KrakenPlacedOrderAdvice krakenPlacedOrderAdvice = byKrakenPlacedOrderAndStrategyType.get();
            krakenPlacedOrderAdviceRepository.save(
                    krakenPlacedOrderAdvice
                            .setAdviceTime(new Date())
                            .setExit(shouldExit)
            );
        } else {
            krakenPlacedOrderAdviceRepository.save(
                    new KrakenPlacedOrderAdvice()
                            .setAdviceTime(new Date())
                            .setExit(shouldExit)
                            .setKrakenPlacedOrder(order)
                            .setStrategyPeriod(strategyPeriod)
                            .setStrategyType(strategyType)
            );
        }
    }

}
