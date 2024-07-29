package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.Guess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuessRepository extends JpaRepository<Guess, Long> {
}
