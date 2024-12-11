package dev.m2t.guessers.exception;

public class ResourceNotFoundException extends RuntimeException {
    public <T> ResourceNotFoundException(String resourceName, String key, T value) {
        super(resourceName + " with " + key + " " + value + " not found.");
    }
}
