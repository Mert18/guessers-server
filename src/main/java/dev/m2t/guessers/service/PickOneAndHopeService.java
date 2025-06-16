package dev.m2t.guessers.service;

import dev.m2t.guessers.dto.request.JoinPickOneAndHopeRoomRequest;
import dev.m2t.guessers.model.GameRoom;
import dev.m2t.guessers.model.game.GameEndMessage;
import dev.m2t.guessers.model.game.GameRoundMessage;
import dev.m2t.guessers.model.game.GameStartMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PickOneAndHopeService {
    private static final Logger logger = LoggerFactory.getLogger(PickOneAndHopeService.class);
    private final Map<String, List<JoinPickOneAndHopeRoomRequest>> pickOneAndHopeQueue = new HashMap<>();
    private final SimpMessagingTemplate messagingTemplate;

    public PickOneAndHopeService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public synchronized Optional<GameRoom> tryMatch(JoinPickOneAndHopeRoomRequest joinRoomRequest) {
        logger.info("User {} searching room for pick one and hope. Selected object: {}", joinRoomRequest.getUsername(), joinRoomRequest.getObject());
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

    public void cancelSearch(String username) {
        logger.info("User {} cancelled the room search.", username);
        for (List<JoinPickOneAndHopeRoomRequest> queue : pickOneAndHopeQueue.values()) {
            queue.removeIf(request -> request.getUsername().equals(username));
        }
    }

    public void play(GameRoom room) {
        Map<String, Integer> gameObjectStats = new HashMap<>();
        Map<String, Integer> playersScore = new HashMap<>();

        room.getPlayers().forEach(player -> {
            playersScore.put(player.getUsername(), 0);
        });

        List<String> gameObjects = List.of("cherry", "lemon", "onion", "pepper");

        new Thread(() -> {
            try {
                messagingTemplate.convertAndSend("/topic/room/" + room.getId(), new GameStartMessage());
                Thread.sleep(2000); // delay before starting

                Random random = new Random();

                for (int round = 1; round <= 5; round++) {
                    List<String> roundPicks = new ArrayList<>();
                    List<String> matchResults = new ArrayList<>();
                    for (int i = 0; i < 9; i++) {
                        String picked = gameObjects.get(random.nextInt(gameObjects.size()));
                        roundPicks.add(picked);
                        gameObjectStats.put(picked, gameObjectStats.getOrDefault(picked, 0) + 1);
                        if (room.getPlayers().get(0).getObject().equals(picked)) {
                            matchResults.add("left");
                        } else if(room.getPlayers().get(1).getObject().equals(picked)) {
                            matchResults.add("right");
                        } else {
                            matchResults.add("null");
                        }
                    }


                    for(JoinPickOneAndHopeRoomRequest player : room.getPlayers()) {
                        playersScore.put(player.getUsername(), gameObjectStats.getOrDefault(player.getObject(), 0));
                    }

                    messagingTemplate.convertAndSend("/topic/room/" + room.getId(),
                            new GameRoundMessage(round, roundPicks, playersScore, matchResults));

                    Thread.sleep(5000); // pause between rounds
                }

                messagingTemplate.convertAndSend("/topic/room/" + room.getId(), new GameEndMessage());

                // TODO: IMPLEMENT END GAME LOGIC, give points to winner
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
