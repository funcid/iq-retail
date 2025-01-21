package me.func.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.func.infrastructure.dao.Account;
import me.func.infrastructure.repository.AccountRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceUpdateService {

    private final AccountRepository accountRepository;

    private static final BigDecimal INCREASE_RATE = new BigDecimal("0.10");
    private static final BigDecimal MAX_INCREASE_RATE = new BigDecimal("2.07");

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void updateBalances() {
        List<Account> accounts = accountRepository.findAll();
        log.debug("Found {} accounts to update", accounts.size());
        
        for (Account account : accounts) {
            try {
                updateAccountBalance(account);
            } catch (Exception e) {
                log.error("Error updating balance for account {}", account.getId(), e);
            }
        }

        log.info("Completed balance update for {} accounts", accounts.size());
    }

    private void updateAccountBalance(Account account) {
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