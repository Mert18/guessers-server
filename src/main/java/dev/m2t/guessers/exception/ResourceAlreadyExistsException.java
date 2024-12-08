package dev.m2t.guessers.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String resourceName, String key, String value) {
        super(resourceName + " with " + key + " " + value + " already exists.");
    }
}
