package com.chaukas.auth;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InsufficientAuthenticationException("Authentication required");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt jwt) {
            return Long.parseLong(jwt.getSubject());
        }
        throw new InsufficientAuthenticationException("Authentication failed");
    }
}
