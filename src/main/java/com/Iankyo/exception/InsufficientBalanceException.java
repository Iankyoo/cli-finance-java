package com.Iankyo.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(){
        super("Could not be transaction");
    }
}
