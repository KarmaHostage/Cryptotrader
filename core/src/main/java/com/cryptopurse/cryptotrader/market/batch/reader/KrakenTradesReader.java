package com.cryptopurse.cryptotrader.market.batch.reader;

import com.cryptopurse.cryptotrader.api.market.KrakenMarketService;
import com.cryptopurse.cryptotrader.market.domain.KrakenImportConfiguration;
import com.cryptopurse.cryptotrader.market.service.KrakenImportConfigurationService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("kraken-trades-reader")
@StepScope
public class KrakenTradesReader implements ItemReader<Trade> {

    @Autowired
    private KrakenMarketService krakenMarketService;
    @Autowired
    private KrakenImportConfigurationService krakenImportConfigurationServiceImpl;

    private List<Trade> trades = new ArrayList<>();
    private Long configurationId;

    @Autowired
    public KrakenTradesReader(@Value("#{jobParameters['configurationId']}") Long configurationId) {
        this.configurationId = configurationId;
    }

    @PostConstruct
    public void fetchTrades() {
        Optional<KrakenImportConfiguration> config = krakenImportConfigurationServiceImpl.findOne(configurationId);
        if (config.isPresent()) {
            KrakenImportConfiguration krakenImportConfiguration = config.get();
            if (krakenImportConfiguration.getLastImportId() != null) {
                Trades trades = krakenMarketService.getTrades(new CurrencyPair(krakenImportConfiguration.getCurrencyPair()), krakenImportConfiguration.getLastImportId());
                this.trades = trades.getTrades();
                krakenImportConfigurationServiceImpl.update(krakenImportConfiguration.getId(), trades.getlastID());
            } else {
                Trades trades = krakenMarketService.getTrades(new CurrencyPair(krakenImportConfiguration.getCurrencyPair()));
                this.trades = trades.getTrades();
                krakenImportConfigurationServiceImpl.update(krakenImportConfiguration.getId(), trades.getlastID());
            }
        }
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
