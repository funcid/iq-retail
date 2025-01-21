package me.func.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.func.infrastructure.dao.Account;
import me.func.infrastructure.dao.User;
import me.func.infrastructure.exception.transfer.InsufficientFundsException;
import me.func.infrastructure.exception.transfer.InvalidTransferAmountException;
import me.func.infrastructure.exception.transfer.SameAccountTransferException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TransferService {
    private final UserService userService;

    public void transfer(Long fromUserId, Long toUserId, BigDecimal amount) {
        if (fromUserId.equals(toUserId)) {
            throw new SameAccountTransferException();
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransferAmountException();
        }

        User fromUser = userService.getUser(fromUserId);
        User toUser = userService.getUser(toUserId);

        Account fromAccount = fromUser.getAccount();
        Account toAccount = toUser.getAccount();

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        log.debug("Transferred {} from user {} to user {}", amount, fromUserId, toUserId);
    }
} 