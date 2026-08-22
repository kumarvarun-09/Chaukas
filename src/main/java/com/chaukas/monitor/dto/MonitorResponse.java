package com.chaukas.monitor.dto;

import com.chaukas.monitor.MonitorStatus;

import java.time.Instant;

public record MonitorResponse(
        Long monitorId,
        Long currentConfigId,
        MonitorStatus status,
        Integer consecutiveFailures,
        boolean enabled,
        Instant createdAt,
        Instant lastUpdatedAt,
        Instant nextCheckAt,
        Integer version,
        String name,
        String url,
        Integer expectedStatusCode,
        Long timeoutInMillis,
        Long checkIntervalInSeconds,
        Integer failureThreshold,
        Integer reminderAfterFailures
) {
}
