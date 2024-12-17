package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import dev.m2t.guessers.model.UserActions;

import java.util.List;

public interface UserActionsRepository extends JpaRepository<UserActions, Long> {
    void deleteByRoom(Room room);
}
