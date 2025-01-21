package me.func.infrastructure.exception.transfer;

import me.func.infrastructure.exception.ApiException;

public class SameAccountTransferException extends ApiException {
    public SameAccountTransferException() {
        super("Cannot transfer to the same account");
    }
}


