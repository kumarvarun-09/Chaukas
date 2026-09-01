package com.chaukas.auth.dto;

public record RegisterResponse(
        Long id,
        String name,
        String email,
        String phone
) {
}
