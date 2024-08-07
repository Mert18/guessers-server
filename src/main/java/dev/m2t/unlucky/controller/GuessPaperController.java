package dev.m2t.unlucky.controller;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateGuessPaperRequest;
import dev.m2t.unlucky.dto.request.ListGuessPapersRequest;
import dev.m2t.unlucky.service.GuessPaperService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guess-papers")
@CrossOrigin("*")
public class GuessPaperController {
    private final GuessPaperService guessPaperService;

    public GuessPaperController(GuessPaperService guessPaperService) {
        this.guessPaperService = guessPaperService;
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createGuessPaper(@RequestBody CreateGuessPaperRequest createGuessPaperRequest, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(guessPaperService.createGuessPaper(createGuessPaperRequest, username));
    }

    @PostMapping("/list-by-status/self")
    public ResponseEntity<BaseResponse> listSelfGuessPapers(@RequestBody ListGuessPapersRequest listGuessPapersRequest, @RequestParam int page, @RequestParam int size, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(guessPaperService.listSelfGuessPapers(listGuessPapersRequest, username, pageable));
    }

    @PostMapping("/list-by-status/room/{roomId}")
    public ResponseEntity<BaseResponse> listRoomGuessPapers(@RequestBody ListGuessPapersRequest listGuessPapersRequest, @PathVariable String roomId, @RequestParam int page, @RequestParam int size, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(guessPaperService.listRoomGuessPapers(listGuessPapersRequest, username, Long.parseLong(roomId), pageable));
    }
}