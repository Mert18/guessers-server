package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.EventGuessOptionCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCaseOptionRepository extends JpaRepository<EventGuessOptionCase, Long> {
}
