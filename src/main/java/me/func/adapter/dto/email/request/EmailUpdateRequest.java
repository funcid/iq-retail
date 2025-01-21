package me.func.adapter.dto.email.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class EmailUpdateRequest {
    @NotBlank(message = "Old email cannot be blank")
    @Email(message = "Invalid old email format")
    private String oldEmail;

    @NotBlank(message = "New email cannot be blank")
    @Email(message = "Invalid new email format")
    private String newEmail;
} 