package com.chaukas.monitor.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

public record CreateMonitorRequest(
        @NotBlank
        @Size(max = 255)
        String name,
        @NotBlank
        @URL
        String url,
        @NotNull
        @Min(100)
        @Max(599)
        Integer expectedStatusCode,
        @NotNull
        @Positive
        Long timeoutInMillis,
        @NotNull
        @Min(60)
        Long checkIntervalInSeconds,
        @NotNull
        @Min(1)
        Integer failureThreshold,
        @NotNull
        @Min(1)
        Integer reminderAfterFailures
) {
}
