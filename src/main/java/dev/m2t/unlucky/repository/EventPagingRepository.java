package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.Event;
import dev.m2t.unlucky.model.Room;
import dev.m2t.unlucky.model.enums.EventStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EventPagingRepository extends PagingAndSortingRepository<Event, Long> {
    Page<Event> findByStatusInAndRoom(List<EventStatusEnum> notStarted, Room room, Pageable pageable);
}
