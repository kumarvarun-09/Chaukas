package com.chaukas.exception.dto;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record ErrorResponse(String message,
                            String code,
                            Instant timestamp,
                            Map<String, List<String>> details) {
}
