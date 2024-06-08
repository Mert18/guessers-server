package dev.m2t.unlucky.controller;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.PagingRequest;
import dev.m2t.unlucky.dto.request.CreateEventRequest;
import dev.m2t.unlucky.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping
    public ResponseEntity<BaseResponse> createEvent(@RequestBody CreateEventRequest createEventRequest, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username"); // or whatever claim holds the username
        System.out.println(username);
        if(username != "admin") {
            return ResponseEntity.badRequest().body(new BaseResponse("Only admin can create events.", false, true));
        }
        return ResponseEntity.ok(eventService.createEvent(createEventRequest));
    }

    @PostMapping("/list")
    public ResponseEntity<BaseResponse> listEvents(@RequestBody PagingRequest pagingRequest) {
        return ResponseEntity.ok(eventService.listEvents(pagingRequest));
    }
}
