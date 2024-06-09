package dev.m2t.unlucky.controller;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateBetSlipRequest;
import dev.m2t.unlucky.dto.request.ListRoomBetSlipsRequest;
import dev.m2t.unlucky.service.BetSlipService;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<BaseResponse> createBetSlip(@RequestBody CreateBetSlipRequest createBetSlipRequest, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(betSlipService.createBetSlip(createBetSlipRequest, username));
    }

    @PostMapping("/list/self")
    public ResponseEntity<BaseResponse> listUserBetSlips(@RequestBody Pageable pageable, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(betSlipService.listUserBetSlips(pageable, username));
    }

    @PostMapping("/list/room")
    public ResponseEntity<BaseResponse> listRoomBetSlips(@RequestBody ListRoomBetSlipsRequest listRoomBetSlipsRequest, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(betSlipService.listRoomBetSlips(listRoomBetSlipsRequest, username));
    }
}