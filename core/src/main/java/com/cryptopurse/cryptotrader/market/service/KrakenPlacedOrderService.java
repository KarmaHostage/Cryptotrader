package com.cryptopurse.cryptotrader.market.service;

import com.cryptopurse.cryptotrader.market.domain.KrakenPlacedOrder;
import com.cryptopurse.cryptotrader.market.repository.KrakenPlacedOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KrakenPlacedOrderService {

    @Autowired
    private KrakenPlacedOrderRepository krakenPlacedOrderRepository;

    public List<KrakenPlacedOrder> findAllOpen() {
        return krakenPlacedOrderRepository.findAllOpenOrders();
    }
}
