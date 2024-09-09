package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.Guess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuessRepository extends JpaRepository<Guess, Long> {
}
