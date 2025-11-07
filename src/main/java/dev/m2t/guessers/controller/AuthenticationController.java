package dev.m2t.guessers.controller;

import dev.m2t.guessers.dto.BaseResponse;
import dev.m2t.guessers.dto.request.BanUsernameRequest;
import dev.m2t.guessers.dto.request.CreateUserRequest;
import dev.m2t.guessers.dto.response.CheckUsernameResponse;
import dev.m2t.guessers.model.BannedUser;
import dev.m2t.guessers.model.Stats;
import dev.m2t.guessers.model.User;
import dev.m2t.guessers.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/create-user")
    public ResponseEntity<BaseResponse<User>> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(authenticationService.createUser(createUserRequest));
    }

    @GetMapping("/stats")
    public ResponseEntity<BaseResponse<Stats>> getStats() {
        return ResponseEntity.ok(authenticationService.getStats());
    }

    @PostMapping("/ban-username")
    public ResponseEntity<BaseResponse<BannedUser>> banUsername(@Valid @RequestBody BanUsernameRequest banUsernameRequest) {
        return ResponseEntity.ok(authenticationService.banUsername(banUsernameRequest));
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<BaseResponse<CheckUsernameResponse>> checkUsername(@PathVariable String username) {
        return ResponseEntity.ok(authenticationService.checkUsername(username));
    }
}
