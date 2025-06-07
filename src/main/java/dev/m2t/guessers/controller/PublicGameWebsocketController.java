package dev.m2t.guessers.controller;

import dev.m2t.guessers.dto.request.JoinPickOneAndHopeRoomRequest;
import dev.m2t.guessers.service.PickOneAndHopeService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class PublicGameWebsocketController {
    private final PickOneAndHopeService pickOneAndHopeService;
    private final SimpMessagingTemplate messagingTemplate;

    public PublicGameWebsocketController(PickOneAndHopeService pickOneAndHopeService,
                                         SimpMessagingTemplate messagingTemplate) {
        this.pickOneAndHopeService = pickOneAndHopeService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/join")
    public void handleJoin(JoinPickOneAndHopeRoomRequest joinRoomRequest, SimpMessageHeaderAccessor headers, Principal principal) {
        String username = principal.getName();

        joinRoomRequest.setUsername(username);
        joinRoomRequest.setSessionId(headers.getSessionId());
        pickOneAndHopeService.tryMatch(joinRoomRequest).ifPresent(room -> {
            for (JoinPickOneAndHopeRoomRequest p : room.getPlayers()) {
                messagingTemplate.convertAndSendToUser(p.getUsername(), "/queue/room", room);
            }

            pickOneAndHopeService.play(room);
        });
    }

    @MessageMapping("/cancel")
    public void cancelSearch(Principal principal) {
        String username = principal.getName();
        pickOneAndHopeService.cancelSearch(username);
    }

}