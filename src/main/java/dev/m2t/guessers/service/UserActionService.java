package dev.m2t.guessers.service;

import dev.m2t.guessers.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import dev.m2t.guessers.model.UserActions;
import dev.m2t.guessers.repository.UserActionsRepository;
import dev.m2t.guessers.model.enums.UserActionsEnum;
import dev.m2t.guessers.model.User;

import java.util.List;

@Service
public class UserActionService {
    private final UserActionsRepository userActionsRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserActionService.class);

    public UserActionService(UserActionsRepository userActionsRepository) {
        this.userActionsRepository = userActionsRepository;
    }

    public void saveUserAction(UserActionsEnum action, String actionDescription, User user, Room room) {
        logger.info("Saving user action: {}", action);
        UserActions userActions = new UserActions();
        userActions.setAction(action);
        userActions.setActor(user);
        userActions.setActionDescription(actionDescription);
        userActions.setRoom(room);
        userActionsRepository.save(userActions);
        logger.info("User action saved: {}", userActions);
    }

    public void removeAllUserActionsInRoom(Room room) {
        logger.info("Removing all user actions in room: {}", room);
        userActionsRepository.deleteByRoom(room);
        logger.info("All user actions in room removed: {}", room);
    }
}
