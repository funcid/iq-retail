package me.func.domain.service;

import lombok.extern.slf4j.Slf4j;
import me.func.infrastructure.dao.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
public class AccountBalanceUpdateService {

    private static final BigDecimal INCREASE_RATE = new BigDecimal("0.10");
    private static final BigDecimal MAX_INCREASE_RATE = new BigDecimal("2.07");

    void updateAccountBalance(Account account) {
        BigDecimal currentBalance = account.getBalance();
        BigDecimal initialBalance = account.getInitialBalance();
        BigDecimal maxAllowedBalance = initialBalance.multiply(BigDecimal.ONE.add(MAX_INCREASE_RATE));

        if (currentBalance.compareTo(maxAllowedBalance) >= 0) {
            log.debug("Account {} already at max allowed balance, skipping update", account.getId());
            return;
        }

        BigDecimal newBalance = currentBalance.multiply(BigDecimal.ONE.add(INCREASE_RATE))
                .setScale(2, RoundingMode.HALF_UP);

        if (newBalance.compareTo(maxAllowedBalance) > 0) {
            log.debug("Account {} reached max allowed balance", account.getId());
            newBalance = maxAllowedBalance;
        }

        log.debug("Account {} balance updated: {} -> {}", account.getId(), currentBalance, newBalance);
        account.setBalance(newBalance);
    }
}
