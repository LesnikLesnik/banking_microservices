package com.example.gateway.exception;

public class DepositServiceNullAccountIdException extends RuntimeException{
    public DepositServiceNullAccountIdException(String message) {
        super(message);
    }
}
