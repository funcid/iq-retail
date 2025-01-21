package me.func.domain.service;

import lombok.RequiredArgsConstructor;
import me.func.adapter.dto.auth.AuthRequest;
import me.func.adapter.dto.auth.AuthResponse;
import me.func.infrastructure.dao.User;
import me.func.infrastructure.exception.user.UserNotFoundException;
import me.func.infrastructure.repository.UserRepository;
import me.func.infrastructure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        
        User user = userRepository.findByEmailOrPhone(request.getLogin())
                .orElseThrow(UserNotFoundException::new);
        
        String token = jwtService.generateToken(user.getId());

        log.debug("User {} successfully authenticated", user.getId());

        return new AuthResponse(token);
    }
} 