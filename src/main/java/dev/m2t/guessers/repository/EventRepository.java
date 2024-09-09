package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Event, Long> {

}
