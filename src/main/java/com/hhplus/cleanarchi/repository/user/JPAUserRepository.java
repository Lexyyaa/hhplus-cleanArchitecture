package com.hhplus.cleanarchi.repository.user;

import com.hhplus.cleanarchi.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JPAUserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);
}
