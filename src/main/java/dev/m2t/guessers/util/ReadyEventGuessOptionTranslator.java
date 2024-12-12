package dev.m2t.guessers.util;

import dev.m2t.guessers.dto.client.nosyapi.GuessOptionPrecedence;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ReadyEventGuessOptionTranslator {
    private Map<String, GuessOptionPrecedence> gameNames = new HashMap<>() {{
        put("799421", new GuessOptionPrecedence("Which Team Will Receive More Cards", 10));
        put("799422", new GuessOptionPrecedence("Total Cards Over/Under 6.5", 10));
        put("799423", new GuessOptionPrecedence("Total Corner Range", 9));
        put("799429", new GuessOptionPrecedence("Will There Be a Red Card", 3));
        put("798716", new GuessOptionPrecedence("Will There Be a Penalty", 3));
        put("789518", new GuessOptionPrecedence("Half-Time / Full-Time Result", 9));
        put("789522", new GuessOptionPrecedence("Over/Under 4.5", 5));
        put("789523", new GuessOptionPrecedence("Over/Under 3.5", 5));
        put("789558", new GuessOptionPrecedence("Over/Under 2.5", 5));
        put("789532", new GuessOptionPrecedence("Over/Under 1.5", 5));
        put("789529", new GuessOptionPrecedence("Both Teams to Score", 3));
        put("789567", new GuessOptionPrecedence("First Half Result", 4));
        put("796010", new GuessOptionPrecedence("Player to Score", 9));
    }};

    public GuessOptionPrecedence getGuessOptionPrecedence(String gameId) {
        return gameNames.get(gameId);
    }
}
