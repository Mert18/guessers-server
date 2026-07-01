package dev.m2t.guessers.model;

import java.time.Instant;

public record LiveActivityMessage(String sentence, Instant timestamp) {
    public LiveActivityMessage(String sentence) {
        this(sentence, Instant.now());
    }
}
