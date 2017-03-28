package com.cryptopurse.cryptotrader.market.repository;

import com.cryptopurse.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.cryptopurse.cryptotrader.infrastructure.repository.JpaRepository;
import com.cryptopurse.cryptotrader.market.domain.CurrencyPair;
import com.cryptopurse.cryptotrader.market.domain.UserTrade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserTradeRepository extends JpaRepository<UserTrade, Long> {


    Optional<UserTrade> findTop1ByCurrencyPairAndExchangeOrderByPlacedAtDesc(@Param("currencyPair") CurrencyPair currencyPair,
                                      @Param("exchange") SupportedExchanges exchange);
}
