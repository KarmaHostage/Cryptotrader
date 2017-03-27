package com.cryptopurse.cryptotrader.market.repository;

import com.cryptopurse.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.cryptopurse.cryptotrader.infrastructure.repository.JpaRepository;
import com.cryptopurse.cryptotrader.market.domain.CurrencyPair;
import com.cryptopurse.cryptotrader.market.domain.UserTrade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTradeRepository extends JpaRepository<UserTrade, Long> {

    @Query("select theOrder from UserTrade theOrder where closed = false")
    List<UserTrade> findAllOpenOrders();

    @Query("select theOrder from UserTrade theOrder where closed = false and currencyPair = :pair and exchange = :exchange")
    List<UserTrade> findAllOpen(@Param("pair") CurrencyPair pair, @Param("exchange") SupportedExchanges exchange);
}
