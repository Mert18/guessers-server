package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.Prize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrizeRepository extends JpaRepository<Prize, Long> {
}
