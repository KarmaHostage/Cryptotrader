package com.cryptopurse.cryptotrader.api.exception;

public class CryptotraderApiException extends RuntimeException {

    public CryptotraderApiException() {
        super();
    }

    public CryptotraderApiException(String message) {
        super(message);
    }

    public CryptotraderApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptotraderApiException(Throwable cause) {
        super(cause);
    }
}
