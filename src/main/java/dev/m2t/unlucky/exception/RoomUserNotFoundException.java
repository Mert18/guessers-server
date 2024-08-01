package dev.m2t.unlucky.exception;

public class RoomUserNotFoundException extends RuntimeException{
    public RoomUserNotFoundException(String message) {
        super(message);
    }
}
