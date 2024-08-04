package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.Room;
import dev.m2t.unlucky.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface RoomPagingRepository extends PagingAndSortingRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.isPublic = true AND r.id NOT IN (SELECT ru.room.id FROM RoomUser ru WHERE ru.user.id = :userId)")
    Page<Room> findAllPublicRoomsExcludingUser(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT r FROM Room r WHERE r.isPublic = true AND r.id NOT IN (SELECT ru.room.id FROM RoomUser ru WHERE ru.user.id = :userId) AND r.name LIKE %:query%")
    Page<Room> findRoomsExcludingUser(@Param("userId") Long userId, @Param("query") String query, Pageable pageable);
}
