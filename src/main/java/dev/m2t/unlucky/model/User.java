package dev.m2t.unlucky.model;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    private String username;
    private Double balance;
    private Double luckPercentage;

    public User(String username, Double balance, Double luckPercentage) {
        this.username = username;
        this.balance = balance;
        this.luckPercentage = luckPercentage;
    }

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
}
