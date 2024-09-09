package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.Room;
import dev.m2t.guessers.model.RoomInvite;
import dev.m2t.guessers.model.User;
import dev.m2t.guessers.model.enums.RoomInviteStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomInviteRepository extends JpaRepository<RoomInvite, Long> {
    Optional<RoomInvite> findByRoomAndUser(Room room, User user);

    List<RoomInvite> findAllByUserAndStatus(User user, RoomInviteStatusEnum roomInviteStatusEnum);
}
