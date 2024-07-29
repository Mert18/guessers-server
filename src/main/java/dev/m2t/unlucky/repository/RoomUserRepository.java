package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
}
