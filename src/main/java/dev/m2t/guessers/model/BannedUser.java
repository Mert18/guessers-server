package dev.m2t.guessers.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "banned_user")
public class BannedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private LocalDateTime bannedAt;

    public BannedUser() {
        this.bannedAt = LocalDateTime.now();
    }

    public BannedUser(String username) {
        this.username = username;
        this.bannedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getBannedAt() {
        return bannedAt;
    }

    public void setBannedAt(LocalDateTime bannedAt) {
        this.bannedAt = bannedAt;
    }
}
