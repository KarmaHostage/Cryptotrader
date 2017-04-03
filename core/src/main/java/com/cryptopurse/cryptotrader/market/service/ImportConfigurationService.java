package com.cryptopurse.cryptotrader.market.service;

import com.cryptopurse.cryptotrader.market.domain.ImportConfiguration;
import com.cryptopurse.cryptotrader.market.repository.ImportConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ImportConfigurationService {

    @Autowired
    private ImportConfigurationRepository importConfigurationRepository;

    public List<ImportConfiguration> getImportConfigurations() {
        return importConfigurationRepository.findAll();
    }

    public Optional<ImportConfiguration> findOne(long id) {
        return importConfigurationRepository.findOne(id);
    }

    @Transactional
    public void update(final Long id, final Long lastImportId) {
        Optional<ImportConfiguration> config = findOne(id);
        config.ifPresent(configuration -> importConfigurationRepository.save(configuration.setLastImportId(lastImportId)
                .setLastImportTimestamp(new Date())
        ));
    }

    @Transactional
    public void save(final ImportConfiguration importConfiguration) {
        importConfigurationRepository.save(importConfiguration);
    }
}
