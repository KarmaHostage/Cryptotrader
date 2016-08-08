package com.cryptopurse.cryptotrader.market.repository;

import com.cryptopurse.cryptotrader.infrastructure.repository.JpaRepository;
import com.cryptopurse.cryptotrader.market.domain.KrakenTrade;

public interface KrakenTradeRepository extends JpaRepository<KrakenTrade, Long> {
}
