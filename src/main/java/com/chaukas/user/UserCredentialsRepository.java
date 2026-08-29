package com.chaukas.user;

import com.chaukas.user.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
    Optional<UserCredentials> findByUserEmail(String email); // Find credentials belonging to the user with this email
}
