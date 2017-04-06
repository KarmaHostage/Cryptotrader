package com.karmahostage.cryptotrader.admin.dto;

import com.karmahostage.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.karmahostage.cryptotrader.market.domain.CurrencyPair;
import com.karmahostage.cryptotrader.market.domain.ImportConfiguration;

public class ImportConfigurationDto {

    private Long id;
    private CurrencyPair currencypair;
    private SupportedExchanges exchange;

    public ImportConfiguration toImportConfiguration() {
        return new ImportConfiguration()
                .setCurrencyPair(currencypair)
                .setExchange(exchange)
                .setId(id);
    }

    public void fill(final ImportConfiguration importConfiguration) {
        this.id = importConfiguration.getId();
        this.currencypair = importConfiguration.getCurrencyPair();
        this.exchange = importConfiguration.getExchange();
    }
}
