package com.cryptopurse.cryptotrader.market.repository;

import com.cryptopurse.cryptotrader.infrastructure.repository.JpaRepository;
import com.cryptopurse.cryptotrader.market.domain.KrakenPlacedOrder;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KrakenPlacedOrderRepository extends JpaRepository<KrakenPlacedOrder, Long> {

    @Query("select theOrder from KrakenPlacedOrder theOrder where closed = false")
    List<KrakenPlacedOrder> findAllOpenOrders();

}
