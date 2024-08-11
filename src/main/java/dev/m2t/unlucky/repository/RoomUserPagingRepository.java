package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.RoomUser;
import dev.m2t.unlucky.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomUserPagingRepository extends JpaRepository<RoomUser, Long> {
    Page<RoomUser> findAllByUser(User user, Pageable pageable);
}
