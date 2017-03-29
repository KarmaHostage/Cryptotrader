package com.cryptopurse.cryptotrader.papertrading.repository;

import com.cryptopurse.cryptotrader.infrastructure.repository.JpaRepository;
import com.cryptopurse.cryptotrader.papertrading.domain.PaperTrade;
import com.cryptopurse.cryptotrader.papertrading.domain.PaperTradeConfiguration;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaperTradeRepository extends JpaRepository<PaperTrade, Long> {


    Optional<PaperTrade> findTop1ByPaperTradeConfigurationOrderByPlacedAtDesc(
            @Param("paperTradeConfiguration") final PaperTradeConfiguration paperTradeConfiguration
    );

}
