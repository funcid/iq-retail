package me.func.util;

import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {
    public static Long extractUserId(UserDetails userDetails) {
        return Long.parseLong(userDetails.getUsername());
    }
}