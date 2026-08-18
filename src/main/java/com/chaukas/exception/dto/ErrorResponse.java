package com.chaukas.exception.dto;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(String message,
                            String code,
                            Instant timestamp,
                            Map<String, String> details) {
}
