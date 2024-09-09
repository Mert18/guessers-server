package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.SingleGuess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingleGuessRepository extends JpaRepository<SingleGuess, Long> {

}
