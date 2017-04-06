package com.karmahostage.cryptotrader.infrastructure.exception;

public class CryptoTraderException extends RuntimeException {

    public CryptoTraderException(String message) {
        super(message);
    }

    public CryptoTraderException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptoTraderException(Throwable cause) {
        super(cause);
    }

    public CryptoTraderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
