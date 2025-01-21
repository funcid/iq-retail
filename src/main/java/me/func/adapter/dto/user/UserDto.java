package me.func.adapter.dto.user;

import lombok.Data;
import me.func.adapter.dto.email.EmailDataDto;
import me.func.adapter.dto.phone.PhoneDataDto;
import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private List<EmailDataDto> emails;
    private List<PhoneDataDto> phones;
} 