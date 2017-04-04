package com.cryptopurse.cryptotrader.papertrading.controller;

import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.cryptopurse.cryptotrader.market.domain.TradeHistory;
import com.cryptopurse.cryptotrader.market.service.TradehistoryService;
import com.cryptopurse.cryptotrader.market.timeseries.TimeSeriesOrderLocator;
import com.cryptopurse.cryptotrader.market.timeseries.TimeseriesBuilder;
import com.cryptopurse.cryptotrader.papertrading.controller.dto.PaperTradePerConfigurationDTo;
import com.cryptopurse.cryptotrader.papertrading.service.PaperTradeService;
import com.cryptopurse.cryptotrader.usermanagement.utils.SecurityUtility;
import eu.verdelhan.ta4j.Order;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.TradingRecord;
import eu.verdelhan.ta4j.analysis.criteria.AverageProfitCriterion;
import eu.verdelhan.ta4j.analysis.criteria.AverageProfitableTradesCriterion;
import eu.verdelhan.ta4j.analysis.criteria.LinearTransactionCostCriterion;
import eu.verdelhan.ta4j.analysis.criteria.TotalProfitCriterion;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user/papertrade")
public class PaperTradeController {

    @Autowired
    private PaperTradeService paperTradeService;
    @Autowired
    private TradehistoryService tradehistoryService;
    @Autowired
    private TimeseriesBuilder timeseriesBuilder;
    @Autowired
    private TimeSeriesOrderLocator locator;


    @RequestMapping(method = RequestMethod.GET)
    public String index(final ModelMap map) {
        if (SecurityUtility.userIsAnonymous()) {
            return "redirect:/";
        } else {
            map.put("papertrades", paperTradeService.findByUser(SecurityUtility.currentUser().get())
                    .entrySet()
                    .stream()
                    .map(entry -> new PaperTradePerConfigurationDTo(entry.getKey(), entry.getValue()))
                    .map(config -> {
                        final TimeSeries trades = getTrades(config.getConfiguration().getExchange(), config.getConfiguration().getStrategyPeriod());
                        List<Order> orders = config.getTrades()
                                .parallelStream()
                                .map(order -> locator.convertToTradingRecord(trades, order))
                                .collect(Collectors.toList());
                        final TradingRecord tradingRecord = new TradingRecord(orders.toArray(new Order[orders.size()]));
                        double totalProfit = new TotalProfitCriterion()
                                .calculate(trades, tradingRecord);
                        double transactionCosts = new LinearTransactionCostCriterion(1.00, 0.015)
                                .calculate(trades, tradingRecord);
                        double averageProfitableTrades = new AverageProfitableTradesCriterion()
                                .calculate(trades, tradingRecord);
                        double averageProfit = new AverageProfitCriterion()
                                .calculate(trades, tradingRecord);
                        return config
                                .setAverageProfit(String.valueOf(averageProfit))
                                .setAverageProfitableTrades(String.valueOf(averageProfitableTrades))
                                .setTotalProfit(String.valueOf(totalProfit))
                                .setTransactionCosts(String.valueOf(transactionCosts));

                    })
                    .sorted(Comparator.comparingLong(p -> p.getConfiguration().getId()))
                    .collect(Collectors.toList()));

            return "user/papertrades";
        }

    }

    private TimeSeries getTrades(final SupportedExchanges exchange, StrategyPeriod period) {
        List<TradeHistory> recentTrades = tradehistoryService.findRecentTrades(DateTime.now().minusDays(21).toDate(), exchange);
        return timeseriesBuilder.timeseries(recentTrades, period.getTimeframeInSeconds());
    }

}
