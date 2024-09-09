package dev.m2t.guessers.exception;

public class RoomUserNotFoundException extends RuntimeException{
    public RoomUserNotFoundException(String message) {
        super(message);
    }
}
