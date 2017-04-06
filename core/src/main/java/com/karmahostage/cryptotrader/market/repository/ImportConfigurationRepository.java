package com.karmahostage.cryptotrader.market.repository;

import com.karmahostage.cryptotrader.infrastructure.repository.JpaRepository;
import com.karmahostage.cryptotrader.market.domain.ImportConfiguration;

public interface ImportConfigurationRepository extends JpaRepository<ImportConfiguration, Long> {

}
