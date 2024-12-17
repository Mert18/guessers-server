package dev.m2t.guessers.controller;

import dev.m2t.guessers.dto.BaseResponse;
import dev.m2t.guessers.service.SharedGuessPaperService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shared-guess-papers")
@CrossOrigin("*")
public class SharedGuessPaperController {
    private final SharedGuessPaperService sharedGuessPaperService;

    public SharedGuessPaperController(SharedGuessPaperService sharedGuessPaperService) {
        this.sharedGuessPaperService = sharedGuessPaperService;
    }

    @GetMapping("/share/{guessPaperId}")
    public ResponseEntity<BaseResponse> shareGuessPaper(@AuthenticationPrincipal Jwt jwt, @PathVariable String guessPaperId) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(sharedGuessPaperService.shareGuessPaper(username, Long.parseLong(guessPaperId)));
    }

    @GetMapping("/getByToken/{token}")
    public ResponseEntity<BaseResponse> shareGuessPaper(@PathVariable String token) {
        return ResponseEntity.ok(sharedGuessPaperService.getSharedGuessPaper(token));
    }
}
