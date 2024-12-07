package dev.m2t.guessers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.m2t.guessers.model.UserActions;

public interface UserActionsRepository extends JpaRepository<UserActions, Long> {

}
