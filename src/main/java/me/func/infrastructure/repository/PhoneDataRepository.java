package me.func.infrastructure.repository;

import me.func.infrastructure.dao.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {
    @Modifying
    @Query("DELETE FROM PhoneData p WHERE p.user.id = :userId AND p.phone = :phone")
    void deleteByUserIdAndPhone(@Param("userId") Long userId, @Param("phone") String phone);
} 