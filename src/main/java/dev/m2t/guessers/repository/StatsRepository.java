package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.Stats;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StatsRepository extends JpaRepository<Stats, Long> {

    Stats findFirstByOrderByLastUpdatedDesc();
}
