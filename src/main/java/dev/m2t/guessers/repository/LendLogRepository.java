package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.LendLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LendLogRepository extends JpaRepository<LendLog, Long> {
}
