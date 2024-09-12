package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.ReadyEvent;
import dev.m2t.guessers.model.enums.ReadyEventLeagueEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface ReadyEventRepository extends JpaRepository<ReadyEvent, String> {
    List<ReadyEvent> findByCommenceTimeBetweenAndLeague(ZonedDateTime start, ZonedDateTime end, ReadyEventLeagueEnum league);


}
