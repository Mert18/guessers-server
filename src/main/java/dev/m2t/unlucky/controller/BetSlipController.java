package dev.m2t.unlucky.controller;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateBetSlipRequest;
import dev.m2t.unlucky.service.BetSlipService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bet-slips")
@CrossOrigin("*")
public class BetSlipController {
    private final BetSlipService betSlipService;

    public BetSlipController(BetSlipService betSlipService) {
        this.betSlipService = betSlipService;
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createBetSlip(@Valid @RequestBody CreateBetSlipRequest createBetSlipRequest, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(betSlipService.createBetSlip(createBetSlipRequest, username));
    }

    @PostMapping("/list/self")
    public ResponseEntity<BaseResponse> listUserBetSlips(@RequestBody Pageable pageable, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(betSlipService.listUserBetSlips(pageable, username));
    }

    @GetMapping("/list/room/{roomId}")
    public ResponseEntity<BaseResponse> listRoomBetSlips(@PathVariable String roomId, @RequestParam int page, @RequestParam int size,  @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));

        return ResponseEntity.ok(betSlipService.listRoomBetSlips(roomId, username, pageable));
    }
}