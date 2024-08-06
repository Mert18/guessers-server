package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.GuessPaper;
import dev.m2t.unlucky.model.Room;
import dev.m2t.unlucky.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GuessPaperPagingRepository extends PagingAndSortingRepository<GuessPaper, Long> {
    Page<GuessPaper> findAllByUser(User user, Pageable pageable);

    Page<GuessPaper> findAllByRoom(Room room, Pageable pageable);
}
