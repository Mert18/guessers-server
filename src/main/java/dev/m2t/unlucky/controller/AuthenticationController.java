package dev.m2t.unlucky.controller;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateUserRequest;
import dev.m2t.unlucky.service.AuthenticationService;
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
    public ResponseEntity<BaseResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(authenticationService.createUser(createUserRequest));
    }
}
