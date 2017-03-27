package com.cryptopurse.cryptotrader.market.batch.writer;

import com.cryptopurse.cryptotrader.market.domain.CurrencyPair;
import com.cryptopurse.cryptotrader.market.domain.ImportConfiguration;
import com.cryptopurse.cryptotrader.market.domain.TradeHistory;
import com.cryptopurse.cryptotrader.market.service.ImportConfigurationService;
import com.cryptopurse.cryptotrader.market.service.TradehistoryService;
import org.knowm.xchange.dto.marketdata.Trade;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("import-writer")
@StepScope
public class TradeHistoryImportWriter implements ItemWriter<Trade> {

    @Autowired
    private TradehistoryService tradehistoryService;
    @Autowired
    private ImportConfigurationService importConfigurationServiceImpl;
    private final ImportConfiguration importConfig;


    public TradeHistoryImportWriter(@Value("#{jobParameters['configurationId']}") final Long configurationId) {
        this.importConfig = importConfigurationServiceImpl.findOne(configurationId).orElseThrow(
                () -> new IllegalArgumentException("Unable to find configuration for this job")
        );
    }

    @Override
    public void write(List<? extends Trade> items) throws Exception {
        tradehistoryService.insert(
                items.stream()
                        .map(trade -> new TradeHistory()
                                .setAmount(trade.getTradableAmount())
                                .setCurrencyPair(CurrencyPair.fromXChangeCurrency(trade.getCurrencyPair()))
                                .setOrderType(trade.getType())
                                .setTime(trade.getTimestamp())
                                .setPrice(trade.getPrice())
                                .setExchange(importConfig.getExchange())
                        )
                        .collect(Collectors.toList())
        );
    }
}
