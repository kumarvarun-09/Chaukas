package com.chaukas.auth;

import com.chaukas.auth.dto.LoginRequest;
import com.chaukas.auth.dto.LoginResponse;
import com.chaukas.auth.dto.RegisterRequest;
import com.chaukas.auth.dto.RegisterResponse;
import com.chaukas.auth.jwt.JwtService;
import com.chaukas.auth.model.ChaukasUserDetails;
import com.chaukas.exception.UserAlreadyExistsException;
import com.chaukas.user.UserCredentialsRepository;
import com.chaukas.user.UserRepository;
import com.chaukas.user.model.User;
import com.chaukas.user.model.UserCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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

    public LoginResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.email(),
                loginRequest.password());
        authentication = authenticationManager.authenticate(authentication);

        if (authentication.isAuthenticated()) {
            String jwt = jwtService.generateToken(
                    (ChaukasUserDetails) authentication.getPrincipal()
            );
            return new LoginResponse(jwt);
        }
        return new LoginResponse("Authentication Error");
    }
//    Important boundary
//    JwtService should not:
//    query the database
//    authenticate passwords
//    know about AuthenticationManager
//    know about UserRepository
//    Its job is token operations.
}
