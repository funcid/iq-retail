package me.func.infrastructure.exception.user;

import me.func.infrastructure.exception.ApiException;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException() {
        super("User not found");
    }
}
