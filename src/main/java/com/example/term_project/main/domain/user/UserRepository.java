package com.example.term_project.main.domain.user;

import com.example.term_project.main.domain.user.data.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);
    Optional<UserEntity> findByUserId(Long userId);

    Optional<UserEntity> findByEmail(String email);
}
