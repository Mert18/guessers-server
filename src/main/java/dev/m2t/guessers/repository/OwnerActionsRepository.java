package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.OwnerActions;
import dev.m2t.guessers.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerActionsRepository extends JpaRepository<OwnerActions, Long> {
    void deleteByRoom(Room room);
}
