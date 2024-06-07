package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface EventRepository extends MongoRepository<Event, String> {
}
