package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Event, Long> {

}
