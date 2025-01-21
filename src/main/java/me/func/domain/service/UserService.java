package me.func.domain.service;

import lombok.RequiredArgsConstructor;
import me.func.infrastructure.dao.User;
import me.func.infrastructure.exception.user.UserNotFoundException;
import me.func.infrastructure.repository.UserRepository;
import me.func.adapter.mapper.UserMapper;
import me.func.infrastructure.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import me.func.adapter.dto.user.UserDto;

import java.time.LocalDate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public Page<User> searchUsers(LocalDate dateOfBirth, String phone, String name, String email, Pageable pageable) {
        Page<User> users = userRepository.findUsers(dateOfBirth, phone, name, email, pageable);
        log.debug("Found {} users matching criteria", users.getTotalElements());
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrPhone(login)
                .orElseThrow(UserNotFoundException::new);
        return new UserDetailsImpl(user);
    }

    public UserDetails loadUserById(Long userId) {
        User user = getUser(userId);
        return new UserDetailsImpl(user);
    }
}