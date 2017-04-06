package com.karmahostage.cryptotrader.advice.domain;

public enum StrategyPeriod {

    FIVE_MIN(300), FIFTEEN_MIN(900), ONE_HOUR(3_600), FOUR_HOUR(14_400), ONE_DAY(86_400);

    private int timeframeInSeconds;

    StrategyPeriod(int timeframeInSeconds) {
        this.timeframeInSeconds = timeframeInSeconds;
    }

    public int getTimeframeInSeconds() {
        return timeframeInSeconds;
    }
}
