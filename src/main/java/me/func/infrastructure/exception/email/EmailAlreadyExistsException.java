package me.func.infrastructure.exception.email;

import me.func.infrastructure.exception.ApiException;

public class EmailAlreadyExistsException extends ApiException {
    public EmailAlreadyExistsException() {
        super("Email already exists");
    }
}
