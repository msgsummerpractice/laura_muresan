package com.example.SpringBootData_project.controller;

import com.example.SpringBootData_project.dto.request.MfaVerifyRequest;
import com.example.SpringBootData_project.dto.request.RegisterRequest;
import com.example.SpringBootData_project.dto.request.SignInRequest;
import com.example.SpringBootData_project.dto.response.SignInResponse;
import com.example.SpringBootData_project.dto.response.UserResponse;
import com.example.SpringBootData_project.mapper.UserMapper;
import com.example.SpringBootData_project.model.User;
import com.example.SpringBootData_project.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Register, login, and MFA verification")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserMapper userMapper;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponse(user));
    }

    @Operation(summary = "Login — returns JWT token or MFA challenge")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody SignInRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Verify MFA OTP and receive final JWT token")
    @PostMapping("/verify-mfa")
    public ResponseEntity<SignInResponse> verifyMfa(@Valid @RequestBody MfaVerifyRequest request) {
        return ResponseEntity.ok(authService.verifyMfa(request));
    }
}
