package dev.m2t.guessers.controller;

import dev.m2t.guessers.dto.BaseResponse;
import dev.m2t.guessers.service.AdminService;
import jakarta.ws.rs.QueryParam;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BaseResponse> fetchReadyEventsFootball(@QueryParam("league") Integer league) throws Exception {
        return ResponseEntity.ok(adminService.fetchReadyEventsFootball(league));
    }
}
