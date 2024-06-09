package dev.m2t.unlucky.controller;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateRoomRequest;
import dev.m2t.unlucky.dto.request.InviteUserRequest;
import dev.m2t.unlucky.dto.request.JoinRoomRequest;
import dev.m2t.unlucky.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin("*")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createRoom(@Valid @RequestBody CreateRoomRequest createRoomRequest, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(roomService.createRoom(createRoomRequest, username));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<BaseResponse> getRoom(@PathVariable String roomId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(roomService.getRoom(roomId, username));
    }

    @GetMapping("/{roomId}/accept")
    public ResponseEntity<BaseResponse> acceptRoomInvite(@PathVariable String roomId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(roomService.acceptRoomInvite(roomId, username));
    }

    @GetMapping("/{roomId}/reject")
    public ResponseEntity<BaseResponse> rejectRoomInvite(@PathVariable String roomId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(roomService.rejectRoomInvite(roomId, username));
    }

    @PostMapping("/leave")
    public ResponseEntity<BaseResponse> leaveRoom(@RequestBody JoinRoomRequest joinRoomRequest, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(roomService.leaveRoom(joinRoomRequest, username));
    }

    @PostMapping("/delete")
    public ResponseEntity<BaseResponse> deleteRoom(@RequestBody JoinRoomRequest joinRoomRequest, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(roomService.deleteRoom(joinRoomRequest, username));
    }

    @PostMapping("/{roomId}/invite")
    public ResponseEntity<BaseResponse> inviteUser(@RequestBody InviteUserRequest inviteUserRequest, @PathVariable String roomId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(roomService.inviteUser(inviteUserRequest, username, roomId));
    }

    @GetMapping("/list/self")
    public ResponseEntity<BaseResponse> listSelfRooms(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(roomService.listSelfRooms(username));
    }

    @GetMapping("/{roomId}/list/bet-slips")
    public ResponseEntity<BaseResponse> listBetSlips(@PathVariable String roomId, @AuthenticationPrincipal Jwt jwt, @RequestParam int page, @RequestParam int size) {
        String username = jwt.getClaimAsString("preferred_username");
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(roomService.listRoomBetSlips(roomId, username, pageable));
    }

    @GetMapping("/{roomId}/owner")
    public ResponseEntity<BaseResponse> isOwner(@PathVariable String roomId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(roomService.isOwner(roomId, username));
    }
}
