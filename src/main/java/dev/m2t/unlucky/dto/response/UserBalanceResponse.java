package dev.m2t.unlucky.dto.response;

public class UserBalanceResponse {
    private String username;
    private Double balance;

    public UserBalanceResponse() {

    }

    public UserBalanceResponse(String username, Double balance) {
        this.username = username;
        this.balance = balance;
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
}
