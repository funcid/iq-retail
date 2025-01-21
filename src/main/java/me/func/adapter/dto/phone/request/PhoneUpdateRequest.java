package me.func.adapter.dto.phone.request;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class PhoneUpdateRequest {
    @Pattern(regexp = "^7\\d{10}$", message = "Old phone number must be in format 7XXXXXXXXXX")
    private String oldPhone;

    @Pattern(regexp = "^7\\d{10}$", message = "New phone number must be in format 7XXXXXXXXXX")
    private String newPhone;
} 