package com.chaukas.auth.jwt;

import com.chaukas.auth.model.ChaukasUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtEncoder jwtEncoder;

    public String generateToken(ChaukasUserDetails userDetails) {
        Instant issuedAt = Instant.now();
        Instant expireAt = issuedAt.plus(Duration.ofMinutes(15)); // TODO : Make it configurable and not hardcoded

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(String.valueOf(userDetails.getUserId()))
                .issuedAt(issuedAt)
                .expiresAt(expireAt)
                .build();

        return jwtEncoder
                .encode(JwtEncoderParameters.from(jwtClaimsSet))
                .getTokenValue();
    }
}