package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.GuessPaper;
import dev.m2t.guessers.model.enums.GuessPaperStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuessPaperRepository extends JpaRepository<GuessPaper, Long> {
    List<GuessPaper> findAllByStatus(GuessPaperStatusEnum guessPaperStatusEnum);
}
