package com.chaukas.user.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "user_credentials")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserCredentials {
    @Id
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @MapsId
    private User user;
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at")
    private Instant updatedAt;

    public UserCredentials(User user, String passwordHash) {
        this.user = user;
        this.passwordHash = passwordHash;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
