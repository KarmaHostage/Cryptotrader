package com.cryptopurse.cryptotrader.market.repository;

import com.cryptopurse.cryptotrader.infrastructure.repository.JpaRepository;
import com.cryptopurse.cryptotrader.market.domain.UserTrade;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserTradeRepository extends JpaRepository<UserTrade, Long> {

    @Query("select theOrder from UserTrade theOrder where closed = false")
    List<UserTrade> findAllOpenOrders();

}
