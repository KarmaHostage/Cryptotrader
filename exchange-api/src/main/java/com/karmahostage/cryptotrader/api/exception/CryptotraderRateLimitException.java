package com.karmahostage.cryptotrader.api.exception;

public class CryptotraderRateLimitException extends RuntimeException {

    public CryptotraderRateLimitException() {
    }

    public CryptotraderRateLimitException(String message) {
        super(message);
    }

    public CryptotraderRateLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptotraderRateLimitException(Throwable cause) {
        super(cause);
    }
}
