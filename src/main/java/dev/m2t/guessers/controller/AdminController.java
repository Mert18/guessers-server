package dev.m2t.guessers.controller;

import dev.m2t.guessers.dto.BaseResponse;
import dev.m2t.guessers.exception.UnauthorizedException;
import dev.m2t.guessers.service.AdminService;
import jakarta.ws.rs.QueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/ready-events")
    public ResponseEntity<BaseResponse> fetchReadyEventsFootball(@QueryParam("league") Integer league, @AuthenticationPrincipal Jwt jwt) throws Exception {
        String username = jwt.getClaimAsString("preferred_username");
        if(!username.equals("mert")) { // fix it, role based control
            throw new UnauthorizedException("You are not authenticated.");
        }
        return ResponseEntity.ok(adminService.fetchReadyEventsFootball(league));
    }

    @GetMapping("/ready-events/leagues")
    public ResponseEntity<BaseResponse> fetchReadyEventLeagues(@AuthenticationPrincipal Jwt jwt) throws Exception {
        String username = jwt.getClaimAsString("preferred_username");
        if(!username.equals("mert")) { // fix it, role based control
            throw new UnauthorizedException("You are not authenticated.");
        }
        return ResponseEntity.ok(adminService.fetchReadyEventLeagues());
    }
}
