package dev.m2t.guessers.exception;

public class UsernameNotExistsException extends RuntimeException{
    public UsernameNotExistsException(String message) {
        super(message);
    }
}
