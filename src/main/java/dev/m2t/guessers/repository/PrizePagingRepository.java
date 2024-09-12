package dev.m2t.guessers.repository;

import dev.m2t.guessers.model.Prize;
import dev.m2t.guessers.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PrizePagingRepository extends PagingAndSortingRepository<Prize, Long> {
    Page<Prize> findByRoomAndActive(Room room, boolean b, Pageable pageable);
}
