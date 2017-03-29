package com.cryptopurse.cryptotrader.market.batch.reader;

import com.cryptopurse.cryptotrader.api.market.MarketService;
import com.cryptopurse.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.cryptopurse.cryptotrader.market.domain.ImportConfiguration;
import com.cryptopurse.cryptotrader.market.service.ImportConfigurationService;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component("trades-reader")
@StepScope
public class TradeReader implements ItemReader<Trade> {


    @Autowired
    private ImportConfigurationService importConfigurationServiceImpl;
    @Autowired
    private Map<SupportedExchanges, ? extends MarketService> marketServicesPerSuppportedExchange;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private List<Trade> trades = new ArrayList<>();
    private Long configurationId;

    @Autowired
    public TradeReader(@Value("#{jobParameters['configurationId']}") Long configurationId) {
        this.configurationId = configurationId;
    }

    @PostConstruct
    public void fetchTrades() {
        if (configurationId == null) {
            return;
        }

        final Optional<ImportConfiguration> config = importConfigurationServiceImpl.findOne(configurationId);

        if (config.isPresent()) {
            final MarketService marketService = getMarketService(config.get().getExchange());
            ImportConfiguration importConfiguration = config.get();
            if (importConfiguration.getLastImportId() != null) {
                final Trades trades = marketService.getTrades(importConfiguration.getCurrencyPair().getXchangePair(), importConfiguration.getLastImportId());
                this.trades = trades.getTrades();
                importConfigurationServiceImpl.update(importConfiguration.getId(), trades.getlastID());
            } else {
                final Trades trades = marketService.getTrades(importConfiguration.getCurrencyPair().getXchangePair());
                this.trades = trades.getTrades();
                importConfigurationServiceImpl.update(importConfiguration.getId(), trades.getlastID());
            }
            messagingTemplate.convertAndSend("/topic/krakenvolume", trades.stream()
                    .map(Trade::getTradableAmount)
                    .mapToLong(BigDecimal::longValue)
                    .sum()
            );
        }
    }

    private MarketService getMarketService(final SupportedExchanges exchange) {
        return marketServicesPerSuppportedExchange.get(exchange);
    }

    @Override
    public Trade read() throws Exception {
        if (!trades.isEmpty()) {
            return trades.remove(0);
        } else {
            return null;
        }
    }
}
