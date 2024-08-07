package dev.m2t.unlucky.controller;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateEventRequest;
import dev.m2t.unlucky.dto.request.FinalizeEventRequest;
import dev.m2t.unlucky.service.EventService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

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
        String username = jwt.getClaimAsString("preferred_username"); // or whatever claim holds the username
        return ResponseEntity.ok(eventService.createEvent(createEventRequest, username, Long.valueOf(roomId)));
    }

    @GetMapping("/list/{roomId}/active")
    public ResponseEntity<BaseResponse> listEvents(@PathVariable String roomId, @RequestParam int page, @RequestParam int size, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username"); // or whatever claim holds the username
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(eventService.listEvents(Long.parseLong(roomId), username, pageable));
    }

    @GetMapping("/list/{roomId}/completed")
    public ResponseEntity<BaseResponse> listCompletedEvents(@PathVariable String roomId, @RequestParam int page, @RequestParam int size, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username"); // or whatever claim holds the username
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(eventService.listCompletedEvents(Long.parseLong(roomId), username, pageable));
    }

//
//    @GetMapping("/start/{eventId}")
//    public ResponseEntity<BaseResponse> startEvent(@PathVariable String eventId, @AuthenticationPrincipal Jwt jwt) {
//        String username = jwt.getClaimAsString("preferred_username"); // or whatever claim holds the username
//        return ResponseEntity.ok(eventService.startEvent(eventId, username));
//    }

    @PostMapping("/{eventId}/finalize")
    public ResponseEntity<BaseResponse> finalizeEvent(@RequestBody FinalizeEventRequest finalizeEventRequest, @PathVariable String eventId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username"); // or whatever claim holds the username
        return ResponseEntity.ok(eventService.finalizeEvent(finalizeEventRequest, username, Long.parseLong(eventId)));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<BaseResponse> getEvent(@PathVariable String eventId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username"); // or whatever claim holds the username
        return ResponseEntity.ok(eventService.getEvent(Long.parseLong(eventId), username));
    }
}
