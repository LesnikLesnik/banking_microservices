package com.example.gateway.exception;

public class DepositServiceNullBillIdException extends RuntimeException{
    public DepositServiceNullBillIdException(String message) {
        super(message);
    }
}
