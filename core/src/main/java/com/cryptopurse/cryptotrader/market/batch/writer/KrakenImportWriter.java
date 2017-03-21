package com.cryptopurse.cryptotrader.market.batch.writer;

import com.cryptopurse.cryptotrader.market.domain.CurrencyPair;
import com.cryptopurse.cryptotrader.market.domain.KrakenTrade;
import com.cryptopurse.cryptotrader.market.service.KrakenTradeService;
import org.knowm.xchange.dto.marketdata.Trade;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("kraken-import-writer")
@StepScope
public class KrakenImportWriter implements ItemWriter<Trade> {

    @Autowired
    private KrakenTradeService krakenTradeService;

    @Override
    public void write(List<? extends Trade> items) throws Exception {
        krakenTradeService.insert(
                items.stream()
                        .map(trade -> new KrakenTrade()
                                .setAmount(trade.getTradableAmount())
                                .setCurrencyPair(CurrencyPair.fromXChangeCurrency(trade.getCurrencyPair()))
                                .setOrderType(trade.getType())
                                .setTime(trade.getTimestamp())
                                .setPrice(trade.getPrice()))
                        .collect(Collectors.toList())
        );
    }
}
