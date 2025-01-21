package me.func.adapter.dto.phone.request;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class PhoneDeleteRequest {
    @Pattern(regexp = "^7\\d{10}$", message = "Phone number must be in format 7XXXXXXXXXX")
    private String phone;
}
