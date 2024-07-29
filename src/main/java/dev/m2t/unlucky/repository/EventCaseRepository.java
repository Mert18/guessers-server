package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.EventCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCaseRepository extends JpaRepository<EventCase, Long> {
}
