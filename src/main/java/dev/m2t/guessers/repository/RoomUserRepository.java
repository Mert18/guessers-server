package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.Room;
import dev.m2t.guessers.model.RoomUser;
import dev.m2t.guessers.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
    Optional<RoomUser> findByRoomAndUser(Room room, User user);

    Set<RoomUser> findAllByUser(User user);

    List<RoomUser> findAllByRoom(Room room);
}
