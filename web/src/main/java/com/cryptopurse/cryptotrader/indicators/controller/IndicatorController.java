package com.cryptopurse.cryptotrader.indicators.controller;

import com.cryptopurse.cryptotrader.advice.domain.AdviceEnum;
import com.cryptopurse.cryptotrader.advice.domain.KrakenGeneralAdvice;
import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.advice.domain.StrategyType;
import com.cryptopurse.cryptotrader.advice.service.KrakenGeneralAdviceService;
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
    private KrakenGeneralAdviceService krakenGeneralAdviceService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap modelMap,
                        @RequestParam(value = "currencypair", defaultValue = "ETHEUR") CurrencyPair currencyPair) {


        List<IndicationsDto> indications = Stream.of(StrategyPeriod.values())
                .map(
                        period -> new IndicationsDto(Stream.of(StrategyType.values())
                                .map(
                                        type -> getKrakenGeneralAdvice(type, period, currencyPair)
                                ).collect(Collectors.toList()), period)
                ).collect(Collectors.toList());

        modelMap.put("indications", indications);

        modelMap.put("fiveMinSMA", getKrakenGeneralAdvice(StrategyType.SMA, StrategyPeriod.FIVE_MIN, currencyPair));
        modelMap.put("fiveMinDEMA", getKrakenGeneralAdvice(StrategyType.DEMA, StrategyPeriod.FIVE_MIN, currencyPair));
        modelMap.put("fiveMinMACD", getKrakenGeneralAdvice(StrategyType.MACD, StrategyPeriod.FIVE_MIN, currencyPair));
        modelMap.put("fiveMinMM", getKrakenGeneralAdvice(StrategyType.MM, StrategyPeriod.FIVE_MIN, currencyPair));
        modelMap.put("fiveMinCCI", getKrakenGeneralAdvice(StrategyType.CCI, StrategyPeriod.FIVE_MIN, currencyPair));
        modelMap.put("fiveMinRSI2", getKrakenGeneralAdvice(StrategyType.RSI2, StrategyPeriod.FIVE_MIN, currencyPair));

        modelMap.put("fifteenMinSMA", getKrakenGeneralAdvice(StrategyType.SMA, StrategyPeriod.FIFTEEN_MIN, currencyPair));
        modelMap.put("fifteenMinDEMA", getKrakenGeneralAdvice(StrategyType.DEMA, StrategyPeriod.FIFTEEN_MIN, currencyPair));
        modelMap.put("fifteenMinMACD", getKrakenGeneralAdvice(StrategyType.MACD, StrategyPeriod.FIFTEEN_MIN, currencyPair));
        modelMap.put("fifteenMinMM", getKrakenGeneralAdvice(StrategyType.MM, StrategyPeriod.FIFTEEN_MIN, currencyPair));
        modelMap.put("fifteenMinCCI", getKrakenGeneralAdvice(StrategyType.CCI, StrategyPeriod.FIFTEEN_MIN, currencyPair));
        modelMap.put("fifteenMinRSI2", getKrakenGeneralAdvice(StrategyType.RSI2, StrategyPeriod.FIFTEEN_MIN, currencyPair));

        modelMap.put("oneHourSMA", getKrakenGeneralAdvice(StrategyType.SMA, StrategyPeriod.ONE_HOUR, currencyPair));
        modelMap.put("oneHourDEMA", getKrakenGeneralAdvice(StrategyType.DEMA, StrategyPeriod.ONE_HOUR, currencyPair));
        modelMap.put("oneHourMACD", getKrakenGeneralAdvice(StrategyType.MACD, StrategyPeriod.ONE_HOUR, currencyPair));
        modelMap.put("oneHourMM", getKrakenGeneralAdvice(StrategyType.MM, StrategyPeriod.ONE_HOUR, currencyPair));
        modelMap.put("oneHourCCI", getKrakenGeneralAdvice(StrategyType.CCI, StrategyPeriod.ONE_HOUR, currencyPair));
        modelMap.put("oneHourRSI2", getKrakenGeneralAdvice(StrategyType.RSI2, StrategyPeriod.ONE_HOUR, currencyPair));

        modelMap.put("fourHourSMA", getKrakenGeneralAdvice(StrategyType.SMA, StrategyPeriod.FOUR_HOUR, currencyPair));
        modelMap.put("fourHourDEMA", getKrakenGeneralAdvice(StrategyType.DEMA, StrategyPeriod.FOUR_HOUR, currencyPair));
        modelMap.put("fourHourMACD", getKrakenGeneralAdvice(StrategyType.MACD, StrategyPeriod.FOUR_HOUR, currencyPair));
        modelMap.put("fourHourMM", getKrakenGeneralAdvice(StrategyType.MM, StrategyPeriod.FOUR_HOUR, currencyPair));
        modelMap.put("fourHourCCI", getKrakenGeneralAdvice(StrategyType.CCI, StrategyPeriod.FOUR_HOUR, currencyPair));
        modelMap.put("fourHourRSI2", getKrakenGeneralAdvice(StrategyType.RSI2, StrategyPeriod.FOUR_HOUR, currencyPair));

        modelMap.put("oneDaySMA", getKrakenGeneralAdvice(StrategyType.SMA, StrategyPeriod.ONE_DAY, currencyPair));
        modelMap.put("oneDayDEMA", getKrakenGeneralAdvice(StrategyType.DEMA, StrategyPeriod.ONE_DAY, currencyPair));
        modelMap.put("oneDayMACD", getKrakenGeneralAdvice(StrategyType.MACD, StrategyPeriod.ONE_DAY, currencyPair));
        modelMap.put("oneDayMM", getKrakenGeneralAdvice(StrategyType.MM, StrategyPeriod.ONE_DAY, currencyPair));
        modelMap.put("oneDayCCI", getKrakenGeneralAdvice(StrategyType.CCI, StrategyPeriod.ONE_DAY, currencyPair));
        modelMap.put("oneDayRSI2", getKrakenGeneralAdvice(StrategyType.RSI2, StrategyPeriod.ONE_DAY, currencyPair));
        return "indicators/indicators";
    }

    private KrakenGeneralAdvice getKrakenGeneralAdvice(final StrategyType type,
                                                       final StrategyPeriod period,
                                                       final CurrencyPair currencyPair) {
        return krakenGeneralAdviceService
                .findByCurrencyPairAndStrategyPeriodAndStrategyType(
                        currencyPair,
                        period,
                        type
                ).orElseGet(() -> new KrakenGeneralAdvice()
                        .setAdvice(AdviceEnum.SOFT)
                        .setConfirmations(0)
                        .setCurrencyPair(currencyPair)
                        .setStrategyPeriod(period)
                        .setStrategyType(type)
                        .setStrategyTime(new Date()));
    }
}
