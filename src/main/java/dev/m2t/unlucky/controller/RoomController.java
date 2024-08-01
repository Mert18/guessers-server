package dev.m2t.unlucky.controller;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateRoomRequest;
import dev.m2t.unlucky.dto.request.InviteUserRequest;
import dev.m2t.unlucky.dto.request.JoinRoomRequest;
import dev.m2t.unlucky.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        return ResponseEntity.ok(roomService.getRoom(Long.parseLong(roomId), username));
    }

    @GetMapping("/{roomId}/accept")
    public ResponseEntity<BaseResponse> acceptRoomInvite(@PathVariable String roomId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(roomService.acceptRoomInvite(Long.parseLong(roomId), username));
    }

    @GetMapping("/{roomId}/reject")
    public ResponseEntity<BaseResponse> rejectRoomInvite(@PathVariable String roomId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(roomService.rejectRoomInvite(Long.parseLong(roomId), username));
    }

    @PostMapping("/leave")
    public ResponseEntity<BaseResponse> leaveRoom(@PathVariable String roomId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(roomService.leaveRoom(Long.parseLong(roomId), username));
    }

    @PostMapping("/delete")
    public ResponseEntity<BaseResponse> deleteRoom(@PathVariable String roomId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(roomService.deleteRoom(Long.parseLong(roomId), username));
    }

    @PostMapping("/{roomId}/invite")
    public ResponseEntity<BaseResponse> inviteUser(@PathVariable String invitedUsername, @PathVariable String roomId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(roomService.inviteUser(invitedUsername, Long.parseLong(roomId), username));
    }

    @GetMapping("/list/self")
    public ResponseEntity<BaseResponse> listSelfRooms(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(roomService.listSelfRooms(username));
    }

    @GetMapping("/list/public")
    public ResponseEntity<BaseResponse> listPublicRooms(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
        return ResponseEntity.ok(roomService.listPublicRooms(pageable));
    }

    @GetMapping("/{roomId}/join")
    public ResponseEntity<BaseResponse> joinPublicRoom(@PathVariable String roomId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(roomService.joinPublicRoom(username, Long.parseLong(roomId)));
    }


    @GetMapping("/{roomId}/owner")
    public ResponseEntity<BaseResponse> isOwner(@PathVariable String roomId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(roomService.isOwner(Long.parseLong(roomId), username));
    }
}
