package me.func.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.func.infrastructure.dao.Account;
import me.func.infrastructure.repository.AccountRepository;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceUpdateService {

    private final AccountRepository accountRepository;
    private final AccountBalanceUpdateService accountBalanceUpdateService;

    @Scheduled(fixedRate = 30000)
    @Transactional
    @SchedulerLock
    public void updateBalances() {
        List<Account> accounts = accountRepository.findAll();
        log.debug("Found {} accounts to update", accounts.size());
        
        for (Account account : accounts) {
            try {
                accountBalanceUpdateService.updateAccountBalance(account);
            } catch (Exception e) {
                log.error("Error updating balance for account {}", account.getId(), e);
            }
        }

        log.info("Completed balance update for {} accounts", accounts.size());
    }
} 