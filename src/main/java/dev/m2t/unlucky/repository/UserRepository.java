package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);

    List<User> findByUsernameIn(List<String> users);
}
