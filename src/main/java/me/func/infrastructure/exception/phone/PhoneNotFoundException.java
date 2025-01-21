package me.func.infrastructure.exception.phone;

import me.func.infrastructure.exception.ApiException;

public class PhoneNotFoundException extends ApiException {
    public PhoneNotFoundException() {
        super("Phone not found");
    }
}
