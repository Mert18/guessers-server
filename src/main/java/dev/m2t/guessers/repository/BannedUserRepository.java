package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.BannedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BannedUserRepository extends JpaRepository<BannedUser, Long> {
    Optional<BannedUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
