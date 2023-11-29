package dev.m2t.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User extends PanacheEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private Double luckPercentage;

    @Column(nullable = false)
    private Double wantedDollars;

    @Column(nullable = false)
    private String wantedUsername;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getLuckPercentage() {
        return luckPercentage;
    }

    public void setLuckPercentage(Double luckPercentage) {
        this.luckPercentage = luckPercentage;
    }

    public Double getWantedDollars() {
        return wantedDollars;
    }

    public void setWantedDollars(Double wantedDollars) {
        this.wantedDollars = wantedDollars;
    }

    public String getWantedUsername() {
        return wantedUsername;
    }

    public void setWantedUsername(String wantedUsername) {
        this.wantedUsername = wantedUsername;
    }
}