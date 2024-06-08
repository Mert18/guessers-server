package dev.m2t.unlucky.controller;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateBetSlipRequest;
import dev.m2t.unlucky.service.BetSlipService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping
    public ResponseEntity<BaseResponse> createBetSlip(@RequestBody CreateBetSlipRequest createBetSlipRequest, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(betSlipService.createBetSlip(createBetSlipRequest, username));
    }

    @PostMapping("/list-public-bet-slips")
    public ResponseEntity<BaseResponse> getPublicBetSlips(@RequestBody Pageable pageable) {
        return ResponseEntity.ok(betSlipService.getPublicBetSlips(pageable));
    }

    @PostMapping("/list-user-bet-slips")
    public ResponseEntity<BaseResponse> getUserBetSlips(@RequestBody Pageable pageable, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(betSlipService.getUserBetSlips(pageable, username));
    }
}