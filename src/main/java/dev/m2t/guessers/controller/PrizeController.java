package dev.m2t.guessers.controller;

import dev.m2t.guessers.dto.BaseResponse;
import dev.m2t.guessers.dto.request.CreatePrizeRequest;
import dev.m2t.guessers.service.PrizeService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prizes")
@CrossOrigin("*")
public class PrizeController {
    private final PrizeService prizeService;

    public PrizeController(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createPrize(@RequestBody CreatePrizeRequest createPrizeRequest, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(prizeService.createPrize(createPrizeRequest, username));
    }

    @GetMapping("/list/{roomId}")
    public ResponseEntity<BaseResponse> listPrizes(@RequestParam int page, @RequestParam int size, @RequestParam boolean active, @PathVariable String roomId) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(prizeService.listPrizes(pageable, active, Long.valueOf(roomId)));
    }

    @GetMapping("/buy/{prizeId}")
    public ResponseEntity<BaseResponse> buyPrize(@PathVariable String prizeId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(prizeService.buyPrize(Long.valueOf(prizeId), username));
    }

}
