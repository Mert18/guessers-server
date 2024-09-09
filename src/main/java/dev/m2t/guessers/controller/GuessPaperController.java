package dev.m2t.guessers.controller;

import dev.m2t.guessers.dto.BaseResponse;
import dev.m2t.guessers.dto.request.CreateGuessPaperRequest;
import dev.m2t.guessers.service.GuessPaperService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/list-by-status/self")
    public ResponseEntity<BaseResponse> listSelfGuessPapers(@RequestParam int page, @RequestParam int size, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdOn").descending());
        return ResponseEntity.ok(guessPaperService.listSelfGuessPapers(username, pageable));
    }

    @GetMapping("/list-by-status/room/{roomId}")
    public ResponseEntity<BaseResponse> listRoomGuessPapers(@PathVariable String roomId, @RequestParam int page, @RequestParam int size, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdOn").descending());
        return ResponseEntity.ok(guessPaperService.listRoomGuessPapers(username, Long.parseLong(roomId), pageable));
    }
}
