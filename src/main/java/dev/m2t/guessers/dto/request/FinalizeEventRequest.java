package dev.m2t.guessers.dto.request;

import java.util.List;

public record FinalizeEventRequest(List<Long> winnerEventGuessOptionCases) {
}
