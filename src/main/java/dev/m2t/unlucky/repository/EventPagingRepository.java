package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.Event;
import dev.m2t.unlucky.model.enums.EventStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EventPagingRepository extends PagingAndSortingRepository<Event, Long> {
    Page<Event> findByStatusInAndRoomId(List<EventStatusEnum> status, String roomId, Pageable pageable);
}
