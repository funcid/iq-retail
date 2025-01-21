package me.func.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.func.infrastructure.dao.EmailData;
import me.func.infrastructure.dao.User;
import me.func.infrastructure.exception.email.EmailAlreadyExistsException;
import me.func.infrastructure.exception.email.EmailNotFoundException;
import me.func.infrastructure.repository.EmailDataRepository;
import me.func.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmailService {
    private final EmailDataRepository emailDataRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public void updateEmail(Long userId, String oldEmail, String newEmail) {
        User user = userService.getUser(userId);
        
        EmailData emailData = user.getEmails().stream()
                .filter(e -> e.getEmail().equals(oldEmail))
                .findFirst()
                .orElseThrow(EmailNotFoundException::new);

        if (userRepository.existsByEmail(newEmail)) {
            throw new EmailAlreadyExistsException();
        }

        emailData.setEmail(newEmail);
        log.debug("Email successfully updated to {} for user {}", newEmail, userId);
    }

    public void addEmail(Long userId, String email) {
        User user = userService.getUser(userId);

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException();
        }

        EmailData emailData = new EmailData();
        emailData.setEmail(email);
        emailData.setUser(user);
        user.getEmails().add(emailData);

        log.debug("Email {} successfully added to user {}", email, userId);
    }

    public void deleteEmail(Long userId, String email) {
        emailDataRepository.deleteByUserIdAndEmail(userId, email);
        log.debug("Email {} successfully deleted from user {}", email, userId);
    }
} 