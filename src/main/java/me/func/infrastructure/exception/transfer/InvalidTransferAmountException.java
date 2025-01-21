package me.func.infrastructure.exception.transfer;

import me.func.infrastructure.exception.ApiException;

public class InvalidTransferAmountException extends ApiException {
    public InvalidTransferAmountException() {
        super("Transfer amount must be positive");
    }
}
