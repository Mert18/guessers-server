package dev.m2t.guessers.service;

import dev.m2t.guessers.dto.request.JoinPickOneAndHopeRoomRequest;
import dev.m2t.guessers.model.GameRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PublicGameService {
    private static final Logger logger = LoggerFactory.getLogger(PublicGameService.class);
    private final Map<String, List<JoinPickOneAndHopeRoomRequest>> pickOneAndHopeQueue = new HashMap<>();

    private final ConcurrentHashMap<String, GameRoom> activeRooms;

    public PublicGameService() {
        this.activeRooms = new ConcurrentHashMap<>();
    }

    public synchronized Optional<GameRoom> tryMatch(JoinPickOneAndHopeRoomRequest joinRoomRequest) {
        pickOneAndHopeQueue.computeIfAbsent(joinRoomRequest.getObject(), k -> new ArrayList<>())
                .add(joinRoomRequest);

        List<JoinPickOneAndHopeRoomRequest> potentialMatch = new ArrayList<>();
        Set<String> usedObjects = new HashSet<>();

        for (Map.Entry<String, List<JoinPickOneAndHopeRoomRequest>> entry : pickOneAndHopeQueue.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                JoinPickOneAndHopeRoomRequest p = entry.getValue().get(0);
                if (usedObjects.add(p.getObject())) {
                    potentialMatch.add(p);
                }
            }
            if (potentialMatch.size() == 2) break;
        }

        if (potentialMatch.size() == 2) {
            for (JoinPickOneAndHopeRoomRequest p : potentialMatch) {
                pickOneAndHopeQueue.get(p.getObject()).remove(p);
            }

            GameRoom room = new GameRoom(UUID.randomUUID().toString(), potentialMatch);
            return Optional.of(room);
        }

        return Optional.empty();
    }

    public synchronized void cancelSearch(String userId) {
        for (List<JoinPickOneAndHopeRoomRequest> queue : pickOneAndHopeQueue.values()) {
            queue.removeIf(request -> request.getUsername().equals(userId));
        }
    }
}
