package me.func.infrastructure.repository;

import me.func.infrastructure.dao.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
} 