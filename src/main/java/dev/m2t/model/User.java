package dev.m2t.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection="users")
public class User extends PanacheMongoEntity {
    private String name;
    private String identityNumber;
    private Double balance;
    private Double luckPercentage;
    private Double wantedDollars;
    private String wantedName;

    public User() {
    }

    public User(String name, String identityNumber, Double balance, Double luckPercentage, Double wantedDollars, String wantedName) {
        this.name = name;
        this.identityNumber = identityNumber;
        this.balance = balance;
        this.luckPercentage = luckPercentage;
        this.wantedDollars = wantedDollars;
        this.wantedName = wantedName;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
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

    public String getWantedName() {
        return wantedName;
    }

    public void setWantedName(String wantedName) {
        this.wantedName = wantedName;
    }
}