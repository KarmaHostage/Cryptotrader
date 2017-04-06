package com.karmahostage.cryptotrader.advice.service;

import com.karmahostage.cryptotrader.advice.domain.StrategyPeriod;
import com.karmahostage.cryptotrader.advice.domain.StrategyType;
import com.karmahostage.cryptotrader.advice.domain.UserTradeAdvice;
import com.karmahostage.cryptotrader.advice.repository.UserTradeAdviceRepository;
import com.karmahostage.cryptotrader.usertrading.UserTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class UserTradeAdviceService {

    @Autowired
    private UserTradeAdviceRepository krakenPlacedOrderAdviceRepository;

    @Transactional
    public void giveAdvice(UserTrade order,
                           StrategyType strategyType,
                           boolean shouldExit,
                           StrategyPeriod strategyPeriod) {
        final Optional<UserTradeAdvice> byKrakenPlacedOrderAndStrategyType = krakenPlacedOrderAdviceRepository.findByUserTradeAndStrategyTypeAndStrategyPeriod(order, strategyType, strategyPeriod);
        if (shouldExit) {
            System.out.println("EXIT your position of " + order.getAmount() + "ETH of $" + order.getPrice() + " (" + strategyType + "-" + strategyPeriod + ")");
        } else {
            System.out.println("HOLD your position of " + order.getAmount() + "ETH of $" + order.getPrice() + " (" + strategyType + "-" + strategyPeriod + ")");
        }
        if (byKrakenPlacedOrderAndStrategyType.isPresent()) {
            UserTradeAdvice krakenPlacedOrderAdvice = byKrakenPlacedOrderAndStrategyType.get();
            krakenPlacedOrderAdviceRepository.save(
                    krakenPlacedOrderAdvice
                            .setAdviceTime(new Date())
                            .setExit(shouldExit)
            );
        } else {
            krakenPlacedOrderAdviceRepository.save(
                    new UserTradeAdvice()
                            .setAdviceTime(new Date())
                            .setExit(shouldExit)
                            .setUserTrade(order)
                            .setStrategyPeriod(strategyPeriod)
                            .setStrategyType(strategyType)
            );
        }
    }

}
