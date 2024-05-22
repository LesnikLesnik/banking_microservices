package com.example.gateway.exception;

public class DepositServiceException extends RuntimeException{
    public DepositServiceException(String message) {
        super(message);
    }
}
