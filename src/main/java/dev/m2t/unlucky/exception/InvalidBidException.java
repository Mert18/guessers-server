package dev.m2t.unlucky.exception;

public class InvalidBidException extends RuntimeException{
    public InvalidBidException(String message) {
        super(message);
    }
}
