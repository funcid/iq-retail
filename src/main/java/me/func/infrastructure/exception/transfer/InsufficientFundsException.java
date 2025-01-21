package me.func.infrastructure.exception.transfer;

import me.func.infrastructure.exception.ApiException;

public class InsufficientFundsException extends ApiException {
    public InsufficientFundsException() {
        super("Insufficient funds");
    }
}
