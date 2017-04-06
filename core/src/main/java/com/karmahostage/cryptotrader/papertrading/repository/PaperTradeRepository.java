package com.karmahostage.cryptotrader.papertrading.repository;

import com.karmahostage.cryptotrader.infrastructure.repository.JpaRepository;
import com.karmahostage.cryptotrader.papertrading.domain.PaperTrade;
import com.karmahostage.cryptotrader.papertrading.domain.PaperTradeConfiguration;
import com.karmahostage.cryptotrader.usermanagement.domain.CryptotraderUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

public interface PaperTradeRepository extends JpaRepository<PaperTrade, Long> {


    @Query("select papertrade from PaperTrade papertrade where paperTradeConfiguration = :config order by placedAt desc ")
    Stream<PaperTrade> findTop1ByPaperTradeConfigurationOrderByPlacedAtDesc(
            @Param("config") final PaperTradeConfiguration paperTradeConfiguration
    );

    List<PaperTrade> findPaperTradeByPaperTradeConfigurationUser(@Param("user") CryptotraderUser user);

}
