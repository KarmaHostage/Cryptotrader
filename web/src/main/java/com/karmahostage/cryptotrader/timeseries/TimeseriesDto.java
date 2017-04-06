package com.karmahostage.cryptotrader.timeseries;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TimeseriesDto implements Serializable {
    private Double open;
    private Double closed;
    private Double volume;
    private Double low;
    private Double high;
    private LocalDateTime begintime;
    private LocalDateTime endtime;


    public TimeseriesDto() {
    }

    public TimeseriesDto(final Double open,
                         final Double closed,
                         final Double low,
                         final Double high,
                         final Double volume,
                         final LocalDateTime begintime,
                         final LocalDateTime endtime) {
        this.open = open;
        this.closed = closed;
        this.low = low;
        this.high = high;
        this.volume = volume;
        this.begintime = begintime;
        this.endtime = endtime;
    }

    public Double getOpen() {
        return open;
    }

    public TimeseriesDto setOpen(final Double open) {
        this.open = open;
        return this;
    }

    public Double getClosed() {
        return closed;
    }

    public TimeseriesDto setClosed(final Double closed) {
        this.closed = closed;
        return this;
    }

    public Double getVolume() {
        return volume;
    }

    public TimeseriesDto setVolume(final Double volume) {
        this.volume = volume;
        return this;
    }

    public LocalDateTime getBegintime() {
        return begintime;
    }

    public TimeseriesDto setBegintime(final LocalDateTime begintime) {
        this.begintime = begintime;
        return this;
    }

    public LocalDateTime getEndtime() {
        return endtime;
    }

    public TimeseriesDto setEndtime(final LocalDateTime endtime) {
        this.endtime = endtime;
        return this;
    }

    public Double getLow() {
        return low;
    }

    public TimeseriesDto setLow(final Double low) {
        this.low = low;
        return this;
    }

    public Double getHigh() {
        return high;
    }

    public TimeseriesDto setHigh(final Double high) {
        this.high = high;
        return this;
    }
}
