package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.SharedGuessPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface SharedGuessPaperRepository extends JpaRepository<SharedGuessPaper, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM SharedGuessPaper sgp WHERE sgp.expiryTime < CURRENT_TIMESTAMP")
    void deleteExpiredGuessPapers();

    Optional<SharedGuessPaper> findByToken(String token);
}
