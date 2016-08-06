package com.cryptopurse.cryptotrader.infrastructure.exception;

public class CryptopurseException extends RuntimeException {

    public CryptopurseException(String message) {
        super(message);
    }

    public CryptopurseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptopurseException(Throwable cause) {
        super(cause);
    }

    public CryptopurseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
