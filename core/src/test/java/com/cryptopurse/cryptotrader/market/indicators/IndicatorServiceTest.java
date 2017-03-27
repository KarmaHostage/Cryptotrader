package com.cryptopurse.cryptotrader.market.indicators;

import com.cryptopurse.cryptotrader.market.domain.TradeHistory;
import com.cryptopurse.cryptotrader.market.domain.TradeHistoryFixture;
import com.cryptopurse.cryptotrader.market.dto.StrategyWrapper;
import com.cryptopurse.cryptotrader.market.service.StrategyService;
import com.cryptopurse.cryptotrader.market.timeseries.TimeseriesBuilder;
import eu.verdelhan.ta4j.TimeSeries;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class IndicatorServiceTest {

    @InjectMocks
    private StrategyService indicatorService;
    private TimeseriesBuilder builder = new TimeseriesBuilder();

    @Test
    public void createSmaIndicator() throws Exception {
        TradeHistory lastTrade = TradeHistoryFixture.aTrade();
        TradeHistory middleTrade = TradeHistoryFixture.aTrade().setTime(DateTime.now().minusMinutes(4).toDate())
                .setPrice(new BigDecimal(8));
        TradeHistory firstTrade = TradeHistoryFixture.aTrade().setTime(DateTime.now().minusMinutes(10).toDate())
                .setPrice(new BigDecimal(12));
        TimeSeries timeseries = builder.timeseries(Arrays.asList(firstTrade, middleTrade, lastTrade), 300);

        StrategyWrapper indicator = indicatorService.smaIndicator(timeseries);
        assertThat(indicator).isNotNull();

        boolean shouldEnter = indicator.getStrategy().shouldEnter(timeseries.getEnd());
        assertThat(shouldEnter).isFalse();
    }


}