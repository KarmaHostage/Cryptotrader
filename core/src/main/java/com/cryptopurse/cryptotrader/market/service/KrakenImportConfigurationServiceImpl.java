package com.cryptopurse.cryptotrader.market.service;

import com.cryptopurse.cryptotrader.market.domain.KrakenImportConfiguration;
import com.cryptopurse.cryptotrader.market.repository.KrakenImportConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class KrakenImportConfigurationServiceImpl implements KrakenImportConfigurationService {

    @Autowired
    private KrakenImportConfigurationRepository krakenImportConfigurationRepository;

    @Override
    public List<KrakenImportConfiguration> getImportConfigurations() {
        return krakenImportConfigurationRepository.findAll();
    }

    @Override
    public Optional<KrakenImportConfiguration> findOne(long id) {
        return krakenImportConfigurationRepository.findOne(id);
    }

    @Transactional
    @Override
    public void update(Long id, Long lastImportId) {
        Optional<KrakenImportConfiguration> config = findOne(id);
        config.ifPresent(configuration -> {
            krakenImportConfigurationRepository.save(configuration.setLastImportId(lastImportId)
                    .setLastImportTimestamp(new Date())
            );
        });
    }

}
