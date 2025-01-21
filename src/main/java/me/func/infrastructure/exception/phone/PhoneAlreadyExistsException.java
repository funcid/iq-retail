package me.func.infrastructure.exception.phone;

import me.func.infrastructure.exception.ApiException;

public class PhoneAlreadyExistsException extends ApiException {
    public PhoneAlreadyExistsException() {
        super("Phone already exists");
    }
}
