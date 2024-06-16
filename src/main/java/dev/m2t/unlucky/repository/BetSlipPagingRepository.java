package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.BetSlip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BetSlipPagingRepository extends PagingAndSortingRepository<BetSlip, String> {
    Page<BetSlip> findAllByUsername(String username, Pageable pageable);

    @Query("{ 'roomId' : ?0, 'date' : { $gte: ?1 } }")
    Page<BetSlip> findAllByRoomIdAndDateAfter(String roomId, LocalDateTime date, Pageable pageable);
}
