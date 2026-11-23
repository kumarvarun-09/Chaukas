package com.chaukas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class JwtConfiguration {

    @Bean
    public RSAPublicKey rsaPublicKey(@Value("${jwt.public-key}") String encodedPublicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decodedPublicKey = Base64.getDecoder()
                .decode(encodedPublicKey);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return (RSAPublicKey) keyFactory
                .generatePublic(new X509EncodedKeySpec(decodedPublicKey));
    }

    @Bean
    public RSAPrivateKey rsaPrivateKey(@Value("${jwt.private-key}") String encodedPrivateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decodedPrivateKey = Base64.getDecoder()
                .decode(encodedPrivateKey);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return (RSAPrivateKey) keyFactory
                .generatePrivate(new PKCS8EncodedKeySpec(decodedPrivateKey));
    }

    @Bean
    public JwtEncoder jwtEncoder(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        return NimbusJwtEncoder.withKeyPair(publicKey, privateKey)
                .algorithm(SignatureAlgorithm.RS256)
                .build();
    }
    @Bean
    public JwtDecoder jwtDecoder(RSAPublicKey publicKey){
        return NimbusJwtDecoder.withPublicKey(publicKey)
                .signatureAlgorithm(SignatureAlgorithm.RS256)
                .build();
    }
}
