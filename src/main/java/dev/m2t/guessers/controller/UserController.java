package dev.m2t.guessers.controller;

import dev.m2t.guessers.dto.BaseResponse;
import dev.m2t.guessers.dto.request.ChangePasswordRequest;
import dev.m2t.guessers.exception.UnauthorizedException;
import dev.m2t.guessers.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/invites")
    public ResponseEntity<BaseResponse> getPendingUserInvites(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(userService.getPendingUserInvites(username));
    }

    @GetMapping("/{username}")
    public ResponseEntity<BaseResponse> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }


    @PostMapping("/change-password")
    public ResponseEntity<BaseResponse> changePassword(@AuthenticationPrincipal Jwt jwt, @RequestBody ChangePasswordRequest changePasswordRequest) {
        String username = jwt.getClaimAsString("preferred_username");
        if(!username.equals(changePasswordRequest.getUsername())) {
            throw new UnauthorizedException("You are not authorized to change password for this user.");
        }
        return ResponseEntity.ok(userService.changePassword(changePasswordRequest));
    }

}
