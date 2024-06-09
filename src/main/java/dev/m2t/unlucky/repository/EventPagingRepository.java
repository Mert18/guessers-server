package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.Event;
import dev.m2t.unlucky.model.enums.EventStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EventPagingRepository extends PagingAndSortingRepository<Event, String> {
    Page<Event> findByStatusAndRoomId(EventStatusEnum status, String roomId, Pageable pageable);
}
