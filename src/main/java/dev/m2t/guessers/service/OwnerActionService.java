package dev.m2t.guessers.service;

import dev.m2t.guessers.model.OwnerActions;
import dev.m2t.guessers.model.Room;
import dev.m2t.guessers.model.enums.OwnerActionsEnum;
import dev.m2t.guessers.repository.OwnerActionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import dev.m2t.guessers.model.User;

@Service
public class OwnerActionService {
    private final OwnerActionsRepository ownerActionsRepository;
    private static final Logger logger = LoggerFactory.getLogger(OwnerActionService.class);

    public OwnerActionService(OwnerActionsRepository userActionsRepository) {
        this.ownerActionsRepository = userActionsRepository;
    }

    public void saveOwnerAction(OwnerActionsEnum action, String actionDescription, User user, Room room) {
        logger.info("Saving user action: {}", action);
        OwnerActions ownerActions = new OwnerActions();
        ownerActions.setAction(action);
        ownerActions.setActor(user);
        ownerActions.setActionDescription(actionDescription);
        ownerActions.setRoom(room);
        ownerActionsRepository.save(ownerActions);
        logger.info("User action saved: {}", ownerActions);
    }

    public void removeAllOwnerActionsInRoom(Room room) {
        logger.info("Removing all user actions in room: {}", room);
        ownerActionsRepository.deleteByRoom(room);
        logger.info("All user actions in room removed: {}", room);
    }
}
