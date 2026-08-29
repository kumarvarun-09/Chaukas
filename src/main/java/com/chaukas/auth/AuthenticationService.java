package com.chaukas.auth;

import com.chaukas.exception.UserAlreadyExistsException;
import com.chaukas.user.UserCredentialsRepository;
import com.chaukas.user.UserRepository;
import com.chaukas.user.dto.RegisterRequest;
import com.chaukas.user.dto.RegisterResponse;
import com.chaukas.user.model.User;
import com.chaukas.user.model.UserCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        final String email = registerRequest.email().trim().toLowerCase(Locale.ROOT);
        if (userRepository.findByEmail(email).isPresent()) { // We have a race condition here
            throw new UserAlreadyExistsException(email);
        }
        final String hashedPassword = passwordEncoder.encode(registerRequest.password());
        User createdUser = userRepository.save(
                new User(registerRequest.name(),
                        email,
                        registerRequest.phone())
        );
        userCredentialsRepository.save(
                new UserCredentials(createdUser,
                        hashedPassword)
        );
        return new RegisterResponse(createdUser.getId(),
                createdUser.getName(),
                createdUser.getEmail(),
                createdUser.getPhone());
    }
}
