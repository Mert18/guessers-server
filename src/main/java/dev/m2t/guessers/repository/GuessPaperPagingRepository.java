package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.GuessPaper;
import dev.m2t.guessers.model.Room;
import dev.m2t.guessers.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GuessPaperPagingRepository extends PagingAndSortingRepository<GuessPaper, Long> {
    Page<GuessPaper> findAllByUser(User user, Pageable pageable);

    Page<GuessPaper> findAllByRoom(Room room, Pageable pageable);
}
