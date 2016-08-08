package com.cryptopurse.cryptotrader.market.service;


import com.cryptopurse.cryptotrader.market.domain.KrakenImportConfiguration;

import java.util.List;
import java.util.Optional;

public interface KrakenImportConfigurationService {
    List<KrakenImportConfiguration> getImportConfigurations();

    Optional<KrakenImportConfiguration> findOne(long id);

    void update(Long id, Long lastImportId);
}
