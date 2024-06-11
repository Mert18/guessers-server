package dev.m2t.unlucky.exception;

public class EventNotExistsException extends RuntimeException{
    public EventNotExistsException(String message) {
        super(message);
    }
}
