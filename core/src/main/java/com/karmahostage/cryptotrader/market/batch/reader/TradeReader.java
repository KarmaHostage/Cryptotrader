package com.karmahostage.cryptotrader.market.batch.reader;

import com.karmahostage.cryptotrader.api.market.MarketService;
import com.karmahostage.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.karmahostage.cryptotrader.market.batch.dto.TradeStatistics;
import com.karmahostage.cryptotrader.market.domain.ImportConfiguration;
import com.karmahostage.cryptotrader.market.service.ImportConfigurationService;
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
import java.text.DecimalFormat;
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

    private static final DecimalFormat decimalFormat = new DecimalFormat("#.####");

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
            messagingTemplate.convertAndSend("/topic/krakenvolume", new TradeStatistics(formattedVolume(), formattedTradeCount(), latestPrice()));
        }
    }

    public String formattedVolume() {
        return decimalFormat.format(trades.stream()
                .map(Trade::getTradableAmount)
                .mapToDouble(BigDecimal::doubleValue)
                .sum() * 2);
    }

    public String formattedTradeCount() {
        return decimalFormat.format(trades.stream()
                .count() * 2);
    }


    public String latestPrice() {
        return trades.stream()
                .map(x -> decimalFormat.format(x.getPrice().doubleValue()))
                .reduce((a, b) -> b).orElse("unknown");
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
