package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoomPagingRepository extends PagingAndSortingRepository<Room, Long> {
    Page<Room> findAllByIsPublic(Pageable pageable, boolean isPublic);
}
