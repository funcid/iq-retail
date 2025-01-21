package me.func.adapter.controller;

import lombok.RequiredArgsConstructor;
import me.func.adapter.dto.user.UserDto;
import me.func.adapter.mapper.UserMapper;
import me.func.domain.service.UserService;
import me.func.infrastructure.dao.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/search")
    public ResponseEntity<Page<UserDto>> searchUsers(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<User> users = userService.searchUsers(dateOfBirth, phone, name, email, PageRequest.of(page, size));
        return ResponseEntity.ok(users.map(userMapper::toDto));
    }
} 