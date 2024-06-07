package dev.m2t.unlucky.controller;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateBetSlipRequest;
import dev.m2t.unlucky.service.BetSlipService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bet-slips")
public class BetSlipController {
    private final BetSlipService betSlipService;

    public BetSlipController(BetSlipService betSlipService) {
        this.betSlipService = betSlipService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> createBetSlip(@RequestBody CreateBetSlipRequest createBetSlipRequest) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(betSlipService.createBetSlip(createBetSlipRequest));
    }

    @GetMapping("/list-public")
    public ResponseEntity<BaseResponse> getPublicBetSlips(@RequestBody Pageable pageable) {
        return ResponseEntity.ok(betSlipService.getPublicBetSlips(pageable));
    }
}
