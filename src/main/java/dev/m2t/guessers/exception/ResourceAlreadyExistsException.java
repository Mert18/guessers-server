package dev.m2t.guessers.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String resourceName, String key, String value) {
        super("Resource " + resourceName + " with " + key + " " + value + " already exists.");
    }
}
