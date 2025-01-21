package me.func.adapter.dto.email.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author asyncTopilov ~ loli <3
 * @created 21/01/2025 - 8:26 PM
 **/
@Data
public class EmailPutRequest {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;
}
