package dev.m2t.guessers.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomStringGenerator {
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int STRING_LENGTH = 20;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder(STRING_LENGTH);
        for (int i = 0; i < STRING_LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(CHAR_POOL.length());
            sb.append(CHAR_POOL.charAt(randomIndex));
        }
        return sb.toString();
    }
}