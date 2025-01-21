package me.func.adapter.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AuthRequest {
    @NotBlank(message = "Login cannot be blank")
    private String login; // can be email or phone
    
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 500, message = "Password must be between 8 and 500 characters")
    private String password;
} 