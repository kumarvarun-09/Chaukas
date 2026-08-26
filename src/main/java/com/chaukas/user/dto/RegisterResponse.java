package com.chaukas.user.dto;

public record RegisterResponse(
        Long id,
        String name,
        String email,
        String phone
) {
}
