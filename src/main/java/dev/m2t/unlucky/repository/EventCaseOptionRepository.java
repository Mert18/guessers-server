package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.EventGuessOptionOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCaseOptionRepository extends JpaRepository<EventGuessOptionOption, Long> {
}
