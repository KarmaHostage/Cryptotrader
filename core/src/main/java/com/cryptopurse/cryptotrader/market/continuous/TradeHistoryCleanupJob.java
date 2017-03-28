package com.cryptopurse.cryptotrader.market.continuous;

import com.cryptopurse.cryptotrader.market.service.TradehistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Configuration
public class TradeHistoryCleanupJob {

    private static final Logger logger = LoggerFactory.getLogger(TradeHistoryCleanupJob.class);

    @Autowired
    private TradehistoryService tradehistoryService;

    /**
     * this will run every morning at 6 (server time, which is probably GMT+1)
     */
    @Scheduled(cron = "0 0 6 * * ?")
    public void cleanup() {
        final Date aMonthAgo = Date.from(LocalDateTime.now().minus(1, ChronoUnit.MONTHS).toInstant(ZoneOffset.UTC));
        tradehistoryService.deleteByDateBefore(aMonthAgo);
    }

}
