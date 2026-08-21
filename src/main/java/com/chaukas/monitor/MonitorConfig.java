package com.chaukas.monitor;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "monitor_version")
@Getter
@Setter
public class MonitorConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "monitor_id")
    private Monitor monitor;
    private Integer version;
    private String name;
    private String url;
    private Integer expectedStatusCode;
    private Long timeoutInMillis;
    private Integer failureThreshold;
    private Integer reminderAfterFailures;
    private Instant createdAt;
    private Instant endedAt;

    public MonitorConfig(Monitor monitor, Integer version, String name, String url,
                          Integer expectedStatusCode, Long timeoutInMillis,
                          Integer failureThreshold, Integer reminderAfterFailures,
                          Instant createdAt) {
        this.monitor = monitor;
        this.version = version;
        this.name = name;
        this.url = url;
        this.expectedStatusCode = expectedStatusCode;
        this.timeoutInMillis = timeoutInMillis;
        this.failureThreshold = failureThreshold;
        this.reminderAfterFailures = reminderAfterFailures;
        this.createdAt = createdAt;
    }
}
