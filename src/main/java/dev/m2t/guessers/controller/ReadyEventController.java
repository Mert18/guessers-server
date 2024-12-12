package dev.m2t.guessers.controller;

import dev.m2t.guessers.model.ReadyEvent;
import dev.m2t.guessers.service.ReadyEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ready-events")
@CrossOrigin("*")
public class ReadyEventController {
    private final ReadyEventService readyEventService;

    public ReadyEventController(ReadyEventService readyEventService) {
        this.readyEventService = readyEventService;
    }


    @GetMapping("/upcoming/{league}")
    public ResponseEntity<List<ReadyEvent>> getUpcomingReadyEvents(@PathVariable Integer league, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        List<ReadyEvent> readyEvents = readyEventService.getUpcomingReadyEvents(league, username);
        return ResponseEntity.ok(readyEvents);
    }

}
