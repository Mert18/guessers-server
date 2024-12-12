package dev.m2t.guessers.dto.client.nosyapi;

public class GuessOptionPrecedence {
    private String name;
    private int precedence;

    public GuessOptionPrecedence(String name, int precedence) {
        this.name = name;
        this.precedence = precedence;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrecedence() {
        return precedence;
    }

    public void setPrecedence(int precedence) {
        this.precedence = precedence;
    }
}