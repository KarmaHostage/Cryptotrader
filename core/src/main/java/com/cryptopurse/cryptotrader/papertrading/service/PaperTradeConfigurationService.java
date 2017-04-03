package com.cryptopurse.cryptotrader.papertrading.service;

import com.cryptopurse.cryptotrader.papertrading.domain.PaperTradeConfiguration;
import com.cryptopurse.cryptotrader.papertrading.repository.PaperTradeConfigurationRepository;
import com.cryptopurse.cryptotrader.usermanagement.domain.CryptotraderUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaperTradeConfigurationService {

    @Autowired
    private PaperTradeConfigurationRepository paperTradeConfigurationRepository;

    @Transactional(readOnly = true)
    public Optional<PaperTradeConfiguration> findOne(final Long id) {
        return paperTradeConfigurationRepository.findOne(id);
    }

    @Transactional
    public void save(final PaperTradeConfiguration paperTradeConfiguration,
                     final CryptotraderUser user) {
        if (paperTradeConfiguration.isNew()) {
            saveNew(paperTradeConfiguration, user);
        } else {
            update(paperTradeConfiguration, user);
        }
    }

    private void update(final PaperTradeConfiguration paperTradeConfiguration, final CryptotraderUser user) {
        final PaperTradeConfiguration oldConfiguration = paperTradeConfigurationRepository.getOne(paperTradeConfiguration.getId());
        if (!oldConfiguration.getUser().isSame(user)) {
            throw new IllegalArgumentException("Unable to update configuration, you are not the owner of this configuration");
        } else {
            paperTradeConfigurationRepository.save(
                    paperTradeConfiguration
            );
        }
    }

    private void saveNew(final PaperTradeConfiguration paperTradeConfiguration, final CryptotraderUser user) {
        paperTradeConfigurationRepository.save(
                paperTradeConfiguration
                        .setUser(user)
                        .setCreationTime(new Date())
        );
    }

    @Transactional(readOnly = true)
    public List<PaperTradeConfiguration> findByUser(final CryptotraderUser cryptotraderUser) {
        return paperTradeConfigurationRepository.findAllByUser(cryptotraderUser);
    }

    @Transactional(readOnly = true)
    public List<PaperTradeConfiguration> findAllActive() {
        return paperTradeConfigurationRepository.findAllActive();
    }

}
