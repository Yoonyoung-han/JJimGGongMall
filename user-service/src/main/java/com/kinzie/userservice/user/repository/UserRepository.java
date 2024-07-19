package com.kinzie.userservice.user.repository;

import com.kinzie.userservice.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByAccountName(String username);

    Optional<User> findByEmail(String target);
}

