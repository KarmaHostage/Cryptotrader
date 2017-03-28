package com.cryptopurse.cryptotrader.indicators.controller;

import com.cryptopurse.cryptotrader.advice.domain.AdviceEnum;
import com.cryptopurse.cryptotrader.advice.domain.GeneralAdvice;
import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.advice.domain.StrategyType;
import com.cryptopurse.cryptotrader.advice.service.GeneralAdviceService;
import com.cryptopurse.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.cryptopurse.cryptotrader.indicators.dto.IndicationsDto;
import com.cryptopurse.cryptotrader.market.domain.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping(value = "/indicators")
public class IndicatorController {

    @Autowired
    private GeneralAdviceService generalAdviceService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap modelMap,
                        @RequestParam(name = "exchange", defaultValue = "KRAKEN") final SupportedExchanges exchange,
                        @RequestParam(name = "pair", defaultValue = "ETHEUR") final CurrencyPair pair) {

        final List<IndicationsDto> indications = Stream.of(StrategyPeriod.values())
                .map(
                        period -> new IndicationsDto(Stream.of(StrategyType.values())
                                .map(
                                        type -> getGeneralAdvice(type, period, pair, exchange)
                                ).collect(Collectors.toList()), period)
                ).collect(Collectors.toList());

        modelMap.put("indications", indications);
        return "indicators/indicators";
    }

    private GeneralAdvice getGeneralAdvice(final StrategyType type,
                                           final StrategyPeriod period,
                                           final CurrencyPair currencyPair,
                                           final SupportedExchanges exchange) {
        return generalAdviceService
                .findByCurrencyPairAndStrategyPeriodAndStrategyType(
                        currencyPair,
                        period,
                        type,
                        exchange
                ).orElseGet(() -> new GeneralAdvice()
                        .setAdvice(AdviceEnum.SOFT)
                        .setConfirmations(0)
                        .setCurrencyPair(currencyPair)
                        .setStrategyPeriod(period)
                        .setStrategyType(type)
                        .setStrategyTime(new Date()));
    }
}
