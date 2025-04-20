package dev.m2t.guessers.controller;

import dev.m2t.guessers.dto.BaseResponse;
import dev.m2t.guessers.dto.request.CreateEventRequest;
import dev.m2t.guessers.dto.request.FinalizeEventRequest;
import dev.m2t.guessers.service.EventService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin("*")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create/{roomId}")
    public ResponseEntity<BaseResponse> createEvent(@RequestBody CreateEventRequest createEventRequest, @PathVariable String roomId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(eventService.createEvent(createEventRequest, username, Long.valueOf(roomId)));
    }

    @GetMapping("/list/{roomId}/active")
    public ResponseEntity<BaseResponse> listEvents(@PathVariable String roomId, @RequestParam int page, @RequestParam int size, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(eventService.listEvents(Long.parseLong(roomId), username, pageable));
    }

    @GetMapping("/list/{roomId}/completed")
    public ResponseEntity<BaseResponse> listCompletedEvents(@PathVariable String roomId, @RequestParam int page, @RequestParam int size, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(eventService.listCompletedEvents(Long.parseLong(roomId), username, pageable));
    }


    @GetMapping("/{eventId}/start/{roomId}")
    public ResponseEntity<BaseResponse> startEvent(@PathVariable String eventId, @PathVariable String roomId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(eventService.startEvent(Long.parseLong(eventId), Long.parseLong(roomId), username));
    }

    @PostMapping("/{eventId}/finalize/{roomId}")
    public ResponseEntity<BaseResponse> finalizeEvent(@RequestBody FinalizeEventRequest finalizeEventRequest, @PathVariable String eventId, @PathVariable String roomId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(eventService.finalizeEvent(finalizeEventRequest, Long.parseLong(roomId), username, Long.parseLong(eventId)));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<BaseResponse> getEvent(@PathVariable String eventId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(eventService.getEvent(Long.parseLong(eventId), username));
    }
}
