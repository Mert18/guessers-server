package dev.m2t.unlucky.controller;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.service.BetSlipService;
import dev.m2t.unlucky.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final BetSlipService betSlipService;

    public UserController(UserService userService, BetSlipService betSlipService) {
        this.userService = userService;
        this.betSlipService = betSlipService;
    }

//    @GetMapping("/balance")
//    public ResponseEntity<BaseResponse> getUserBalance(@AuthenticationPrincipal Jwt jwt) {
//        String username = jwt.getClaimAsString("preferred_username"); // or whatever claim holds the username
//        return ResponseEntity.ok(userService.getUserBalance(username));
//    }
//
//    @GetMapping("/invites")
//    public ResponseEntity<BaseResponse> getUserInvites(@AuthenticationPrincipal Jwt jwt) {
//        String username = jwt.getClaimAsString("preferred_username"); // or whatever claim holds the username
//        return ResponseEntity.ok(userService.getUserInvites(username));
//    }

}
