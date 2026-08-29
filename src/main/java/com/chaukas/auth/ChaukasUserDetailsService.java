package com.chaukas.auth;

import com.chaukas.user.UserCredentialsRepository;
import com.chaukas.user.model.UserCredentials;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ChaukasUserDetailsService implements UserDetailsService {

    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final String finalEmail = email.trim().toLowerCase(Locale.ROOT);
        UserCredentials userCredentials =
                userCredentialsRepository.findByUserEmail(finalEmail)
                        .orElseThrow(() -> new UsernameNotFoundException(finalEmail));

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public @Nullable String getPassword() {
                return userCredentials.getPasswordHash();
            }

            @Override
            public String getUsername() {
                return userCredentials.getUser().getEmail();
            }
        };
    }
}
