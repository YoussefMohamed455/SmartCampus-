package com.SmartCampus.org.repositories;

import com.SmartCampus.org.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // This allows us to find a user by their username (useful for login later)
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    // Check if a username or email already exists to prevent duplicates
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}