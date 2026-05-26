package com.projectpay.ingress_service.exception;

public class UUIDAlreadyPresentException extends RuntimeException {
    public UUIDAlreadyPresentException(String message) {
        super(message);
    }
}
