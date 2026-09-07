package com.chaukas.auth;

import com.chaukas.auth.model.ChaukasUserDetails;
import com.chaukas.user.UserCredentialsRepository;
import com.chaukas.user.model.UserCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

        return new ChaukasUserDetails(userCredentials.getUserId(),
                userCredentials.getUser().getEmail(),
                userCredentials.getPasswordHash(),
                List.of());
    }
}
