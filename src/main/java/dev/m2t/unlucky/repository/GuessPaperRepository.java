package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.GuessPaper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuessPaperRepository extends JpaRepository<GuessPaper, Long> {
}
