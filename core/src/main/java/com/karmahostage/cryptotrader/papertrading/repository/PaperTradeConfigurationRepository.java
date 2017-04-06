package com.karmahostage.cryptotrader.papertrading.repository;

import com.karmahostage.cryptotrader.infrastructure.repository.JpaRepository;
import com.karmahostage.cryptotrader.papertrading.domain.PaperTradeConfiguration;
import com.karmahostage.cryptotrader.usermanagement.domain.CryptotraderUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaperTradeConfigurationRepository extends JpaRepository<PaperTradeConfiguration, Long> {

    List<PaperTradeConfiguration> findAllByUser(@Param("user") CryptotraderUser cryptotraderUser);

    @Query("select config from PaperTradeConfiguration config where active = true")
    List<PaperTradeConfiguration> findAllActive();

}
