package me.func.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.func.infrastructure.dao.EmailData;
import me.func.infrastructure.dao.User;
import me.func.infrastructure.repository.EmailDataRepository;
import me.func.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailDataRepository emailDataRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public void updateEmail(Long userId, String oldEmail, String newEmail) {
        User user = userService.getUser(userId);
        
        EmailData emailData = user.getEmails().stream()
                .filter(e -> e.getEmail().equals(oldEmail))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Email not found"));

        if (userRepository.existsByEmail(newEmail)) {
            throw new IllegalArgumentException("Email already exists");
        }

        emailData.setEmail(newEmail);
        log.debug("Email successfully updated to {} for user {}", newEmail, userId);
    }

    @Transactional
    public void addEmail(Long userId, String email) {
        User user = userService.getUser(userId);

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        EmailData emailData = new EmailData();
        emailData.setEmail(email);
        emailData.setUser(user);
        user.getEmails().add(emailData);

        log.debug("Email {} successfully added to user {}", email, userId);
    }

    @Transactional
    public void deleteEmail(Long userId, String email) {
        emailDataRepository.deleteByUserIdAndEmail(userId, email);
        log.debug("Email {} successfully deleted from user {}", email, userId);
    }
} 