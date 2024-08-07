package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.SingleGuess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingleGuessRepository extends JpaRepository<SingleGuess, Long> {

}
