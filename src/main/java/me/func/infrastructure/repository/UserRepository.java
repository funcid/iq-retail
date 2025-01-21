package me.func.infrastructure.repository;

import me.func.infrastructure.dao.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT COUNT(e) > 0 FROM EmailData e WHERE e.email = :email")
    boolean existsByEmail(String email);

    @Query("SELECT COUNT(p) > 0 FROM PhoneData p WHERE p.phone = :phone")
    boolean existsByPhone(String phone);

    @Query("SELECT u FROM User u WHERE u.id IN " +
           "(SELECT e.user.id FROM EmailData e WHERE e.email = :login) " +
           "OR u.id IN (SELECT p.user.id FROM PhoneData p WHERE p.phone = :login)")
    Optional<User> findByEmailOrPhone(String login);

    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN u.emails e " +
            "LEFT JOIN u.phones p " +
            "WHERE (DATE(:dateOfBirth) IS NULL OR u.dateOfBirth > DATE(:dateOfBirth)) " +
            "AND (:phone IS NULL OR p.phone = :phone) " +
            "AND (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT(:name, '%'))) " +
            "AND (:email IS NULL OR e.email = :email)")
    Page<User> findUsers(
            @Param("dateOfBirth") LocalDate dateOfBirth,
            @Param("phone") String phone,
            @Param("name") String name,
            @Param("email") String email,
            Pageable pageable
    );
} 