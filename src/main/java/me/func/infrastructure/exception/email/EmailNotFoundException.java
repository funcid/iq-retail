package me.func.infrastructure.exception.email;

import me.func.infrastructure.exception.ApiException;

public class EmailNotFoundException extends ApiException {
    public EmailNotFoundException() {
        super("Email not found");
    }
}
