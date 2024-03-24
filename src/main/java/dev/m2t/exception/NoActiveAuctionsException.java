package dev.m2t.exception;


public class NoActiveAuctionsException extends RuntimeException{
    public NoActiveAuctionsException(String message) {
        super(message);
    }
}
