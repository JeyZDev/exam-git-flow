package com.sm.jeyz9.storemateapi.controllers;

import com.sm.jeyz9.storemateapi.dto.ChangePasswordDTO;
import com.sm.jeyz9.storemateapi.dto.LoginDTO;
import com.sm.jeyz9.storemateapi.dto.PasswordResetDTO;
import com.sm.jeyz9.storemateapi.dto.RegisterDTO;
import com.sm.jeyz9.storemateapi.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "ลงทะเบียน",
            description = "ใช้สำหรับลงทะเบียนเข้าสู่ระบบของเว็บไซต์"
    )
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @Operation(
            summary = "เข้าสู่ระบบ",
            description = "ใช้สำหรับเข้าสู่ระบบของเว็บไซต์"
    )
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginDTO request) {
        String token = authService.login(request);
        return ResponseEntity.ok(Map.of(
                "token", token,
                "type", "Bearer"
        ));
    }

    @Operation(
            summary = "รีเซ็ตรหัสผ่าน",
            description = "ใช้สำหรับส่งรีเซ็ตรหัสผ่าน"
    )
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody PasswordResetDTO request) {
        return new ResponseEntity<>(authService.resetPassword(request), HttpStatus.CREATED);
    }

    @Operation(
            summary = "เปลี่ยนรหัสผ่าน",
            description = "ใช้สำหรับเปลี่ยนรหัสผ่าน"
    )
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordDTO request, Principal principal) {
        return new ResponseEntity<>(authService.changePassword(principal.getName(), request), HttpStatus.CREATED);
    }
}
