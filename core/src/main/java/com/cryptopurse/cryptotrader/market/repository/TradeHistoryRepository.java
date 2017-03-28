package com.cryptopurse.cryptotrader.market.repository;

import com.cryptopurse.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.cryptopurse.cryptotrader.infrastructure.repository.JpaRepository;
import com.cryptopurse.cryptotrader.market.domain.CurrencyPair;
import com.cryptopurse.cryptotrader.market.domain.TradeHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Long> {

    @Query("select trade from TradeHistory trade where time >= :startTime and exchange = :exchange")
    List<TradeHistory> findRecentTrades(@Param("startTime") Date startTime, @Param("exchange") SupportedExchanges exchange);

    @Query("select trade from TradeHistory trade where time >= :startTime and exchange = :exchange and currencyPair = :currencyPair")
    List<TradeHistory> findRecentTrades(@Param("startTime") Date startTime, @Param("exchange") SupportedExchanges exchange,
                                        @Param("currencyPair") CurrencyPair currencyPair);

    void deleteTradeHistoryByTimeBefore(@Param("time") Date time);

}
