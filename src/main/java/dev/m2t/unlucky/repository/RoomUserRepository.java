package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.Room;
import dev.m2t.unlucky.model.RoomUser;
import dev.m2t.unlucky.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
    Optional<RoomUser> findByRoomAndUser(Room room, User user);

    Set<RoomUser> findAllByUser(User user);
}
