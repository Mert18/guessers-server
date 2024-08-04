package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.Room;
import dev.m2t.unlucky.model.RoomUser;
import dev.m2t.unlucky.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findById(Long roomId);}
