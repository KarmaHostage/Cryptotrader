package com.cryptopurse.cryptotrader.market.service;

import com.cryptopurse.cryptotrader.market.domain.KrakenPlacedOrder;

import java.util.List;

public interface KrakenPlacedOrderService {

    List<KrakenPlacedOrder> findAllOpen();

}
