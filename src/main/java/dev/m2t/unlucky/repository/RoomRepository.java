package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RoomRepository extends MongoRepository<Room, String> {
    @Query("{ '$or': [ { 'owner': ?0 }, { 'users': ?0 } ] }")
    List<Room> findByOwnerOrUsersContaining(String username);
}
