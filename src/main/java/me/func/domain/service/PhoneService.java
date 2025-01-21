package me.func.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.func.infrastructure.dao.PhoneData;
import me.func.infrastructure.dao.User;
import me.func.infrastructure.exception.phone.PhoneAlreadyExistsException;
import me.func.infrastructure.exception.phone.PhoneNotFoundException;
import me.func.infrastructure.repository.PhoneDataRepository;
import me.func.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PhoneService {
    private final PhoneDataRepository phoneDataRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public void updatePhone(Long userId, String oldPhone, String newPhone) {
        User user = userService.getUser(userId);
        
        PhoneData phoneData = user.getPhones().stream()
                .filter(p -> p.getPhone().equals(oldPhone))
                .findFirst()
                .orElseThrow(PhoneNotFoundException::new);

        if (userRepository.existsByPhone(newPhone)) {
            throw new PhoneAlreadyExistsException();
        }

        phoneData.setPhone(newPhone);
        log.debug("Phone successfully updated to {} for user {}", newPhone, userId);
    }

    public void addPhone(Long userId, String phone) {
        User user = userService.getUser(userId);

        if (userRepository.existsByPhone(phone)) {
            throw new PhoneAlreadyExistsException();
        }

        PhoneData phoneData = new PhoneData();
        phoneData.setPhone(phone);
        phoneData.setUser(user);
        user.getPhones().add(phoneData);

        log.debug("Phone {} successfully added to user {}", phone, userId);
    }

    public void deletePhone(Long userId, String phone) {
        phoneDataRepository.deleteByUserIdAndPhone(userId, phone);
        log.debug("Phone {} successfully deleted from user {}", phone, userId);
    }
} 