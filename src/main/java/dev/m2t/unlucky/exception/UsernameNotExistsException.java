package dev.m2t.unlucky.exception;

public class UsernameNotExistsException extends RuntimeException{
    public UsernameNotExistsException(String message) {
        super(message);
    }
}
