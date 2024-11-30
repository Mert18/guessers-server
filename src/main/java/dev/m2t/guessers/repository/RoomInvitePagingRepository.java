package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.RoomInvite;
import dev.m2t.guessers.model.User;
import dev.m2t.guessers.model.enums.RoomInviteStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomInvitePagingRepository extends JpaRepository<RoomInvite, Long> {
    Page<RoomInvite> findAllByUserAndStatus(User user, RoomInviteStatusEnum roomInviteStatusEnum, Pageable pageable);
}
