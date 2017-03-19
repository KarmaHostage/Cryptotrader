package com.cryptopurse.cryptotrader.indicators;

import com.cryptopurse.cryptotrader.advice.domain.AdviceEnum;
import com.cryptopurse.cryptotrader.advice.domain.KrakenGeneralAdvice;
import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.advice.domain.StrategyType;
import com.cryptopurse.cryptotrader.advice.service.KrakenGeneralAdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping(value = "/indicators")
public class IndicatorController {

    @Autowired
    private KrakenGeneralAdviceService krakenGeneralAdviceService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        modelMap.put("fiveMinSMA", getKrakenGeneralAdvice(StrategyType.SMA, StrategyPeriod.FIVE_MIN));
        modelMap.put("fiveMinDEMA", getKrakenGeneralAdvice(StrategyType.DEMA, StrategyPeriod.FIVE_MIN));
        modelMap.put("fiveMinMACD", getKrakenGeneralAdvice(StrategyType.MACD, StrategyPeriod.FIVE_MIN));
        modelMap.put("fiveMinMM", getKrakenGeneralAdvice(StrategyType.MM, StrategyPeriod.FIVE_MIN));
        modelMap.put("fiveMinCCI", getKrakenGeneralAdvice(StrategyType.CCI, StrategyPeriod.FIVE_MIN));
        modelMap.put("fiveMinRSI2", getKrakenGeneralAdvice(StrategyType.RSI2, StrategyPeriod.FIVE_MIN));

        modelMap.put("fifteenMinSMA", getKrakenGeneralAdvice(StrategyType.SMA, StrategyPeriod.FIFTEEN_MIN));
        modelMap.put("fifteenMinDEMA", getKrakenGeneralAdvice(StrategyType.DEMA, StrategyPeriod.FIFTEEN_MIN));
        modelMap.put("fifteenMinMACD", getKrakenGeneralAdvice(StrategyType.MACD, StrategyPeriod.FIFTEEN_MIN));
        modelMap.put("fifteenMinMM", getKrakenGeneralAdvice(StrategyType.MM, StrategyPeriod.FIFTEEN_MIN));
        modelMap.put("fifteenMinCCI", getKrakenGeneralAdvice(StrategyType.CCI, StrategyPeriod.FIFTEEN_MIN));
        modelMap.put("fifteenMinRSI2", getKrakenGeneralAdvice(StrategyType.RSI2, StrategyPeriod.FIFTEEN_MIN));

        modelMap.put("oneHourSMA", getKrakenGeneralAdvice(StrategyType.SMA, StrategyPeriod.ONE_HOUR));
        modelMap.put("oneHourDEMA", getKrakenGeneralAdvice(StrategyType.DEMA, StrategyPeriod.ONE_HOUR));
        modelMap.put("oneHourMACD", getKrakenGeneralAdvice(StrategyType.MACD, StrategyPeriod.ONE_HOUR));
        modelMap.put("oneHourMM", getKrakenGeneralAdvice(StrategyType.MM, StrategyPeriod.ONE_HOUR));
        modelMap.put("oneHourCCI", getKrakenGeneralAdvice(StrategyType.CCI, StrategyPeriod.ONE_HOUR));
        modelMap.put("oneHourRSI2", getKrakenGeneralAdvice(StrategyType.RSI2, StrategyPeriod.ONE_HOUR));

        modelMap.put("fourHourSMA", getKrakenGeneralAdvice(StrategyType.SMA, StrategyPeriod.FOUR_HOUR));
        modelMap.put("fourHourDEMA", getKrakenGeneralAdvice(StrategyType.DEMA, StrategyPeriod.FOUR_HOUR));
        modelMap.put("fourHourMACD", getKrakenGeneralAdvice(StrategyType.MACD, StrategyPeriod.FOUR_HOUR));
        modelMap.put("fourHourMM", getKrakenGeneralAdvice(StrategyType.MM, StrategyPeriod.FOUR_HOUR));
        modelMap.put("fourHourCCI", getKrakenGeneralAdvice(StrategyType.CCI, StrategyPeriod.FOUR_HOUR));
        modelMap.put("fourHourRSI2", getKrakenGeneralAdvice(StrategyType.RSI2, StrategyPeriod.FOUR_HOUR));

        modelMap.put("oneDaySMA", getKrakenGeneralAdvice(StrategyType.SMA, StrategyPeriod.ONE_DAY));
        modelMap.put("oneDayDEMA", getKrakenGeneralAdvice(StrategyType.DEMA, StrategyPeriod.ONE_DAY));
        modelMap.put("oneDayMACD", getKrakenGeneralAdvice(StrategyType.MACD, StrategyPeriod.ONE_DAY));
        modelMap.put("oneDayMM", getKrakenGeneralAdvice(StrategyType.MM, StrategyPeriod.ONE_DAY));
        modelMap.put("oneDayCCI", getKrakenGeneralAdvice(StrategyType.CCI, StrategyPeriod.ONE_DAY));
        modelMap.put("oneDayRSI2", getKrakenGeneralAdvice(StrategyType.RSI2, StrategyPeriod.ONE_DAY));
        return "indicators/indicators";
    }

    private KrakenGeneralAdvice getKrakenGeneralAdvice(StrategyType type, StrategyPeriod period) {
        return krakenGeneralAdviceService
                .findByCurrencyPairAndStrategyPeriodAndStrategyType(
                        "ETH/EUR",
                        period,
                        type
                ).orElseGet(() -> new KrakenGeneralAdvice()
                        .setAdvice(AdviceEnum.SOFT)
                        .setConfirmations(0)
                        .setCurrencyPair("ETH/EUR")
                        .setStrategyPeriod(period)
                        .setStrategyType(type)
                        .setStrategyTime(new Date()));
    }
}
