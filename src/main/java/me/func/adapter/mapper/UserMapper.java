package me.func.adapter.mapper;

import me.func.adapter.dto.user.UserDto;
import me.func.infrastructure.dao.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
} 