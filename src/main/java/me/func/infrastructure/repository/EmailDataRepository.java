package me.func.infrastructure.repository;

import me.func.infrastructure.dao.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {
    @Modifying
    @Query("DELETE FROM EmailData e WHERE e.user.id = :userId AND e.email = :email")
    void deleteByUserIdAndEmail(@Param("userId") Long userId, @Param("email") String email);
} 