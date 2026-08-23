package com.chaukas.monitor;

import com.chaukas.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "monitor")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Monitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToOne
    @JoinColumn(name = "current_config_id", unique = true)
    private MonitorConfig currentConfig;
    @Enumerated(EnumType.STRING)
    private MonitorStatus status;
    private Integer consecutiveFailures;
    private boolean enabled;
    private Instant createdAt;
    private Instant lastUpdatedAt;
    private Instant nextCheckAt;

    Monitor(User user, MonitorStatus status, Integer consecutiveFailures, Instant createdAt) {
        this.user = user;
        this.status = status;
        this.consecutiveFailures = consecutiveFailures;
        this.createdAt = createdAt;
    }
}
