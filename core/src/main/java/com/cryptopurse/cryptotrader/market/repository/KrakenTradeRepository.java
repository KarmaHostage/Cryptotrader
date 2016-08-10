package com.cryptopurse.cryptotrader.market.repository;

import com.cryptopurse.cryptotrader.infrastructure.repository.JpaRepository;
import com.cryptopurse.cryptotrader.market.domain.KrakenTrade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface KrakenTradeRepository extends JpaRepository<KrakenTrade, Long> {

    @Query("select trade from KrakenTrade trade where time >= :startTime")
    List<KrakenTrade> findRecentTrades(@Param("startTime") Date startTime);

}
