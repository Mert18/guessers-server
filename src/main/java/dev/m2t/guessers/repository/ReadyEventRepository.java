package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.ReadyEvent;
import dev.m2t.guessers.model.enums.ReadyEventLeagueEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReadyEventRepository extends JpaRepository<ReadyEvent, String> {
    List<ReadyEvent> findByCommenceTimeBetweenAndLeague(LocalDateTime start, LocalDateTime end, ReadyEventLeagueEnum league);


}
