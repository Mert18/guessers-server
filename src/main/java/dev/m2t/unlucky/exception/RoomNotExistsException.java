package dev.m2t.unlucky.exception;

public class RoomNotExistsException extends RuntimeException{
    public RoomNotExistsException(String message) {
        super(message);
    }
}
