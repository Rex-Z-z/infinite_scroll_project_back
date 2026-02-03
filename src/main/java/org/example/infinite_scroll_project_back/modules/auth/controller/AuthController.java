package org.example.infinite_scroll_project_back.modules.auth.controller;

import lombok.RequiredArgsConstructor;
import org.example.infinite_scroll_project_back.modules.user.model.User;
import org.example.infinite_scroll_project_back.modules.user.service.UserService;
import org.example.infinite_scroll_project_back.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    // 1. Login with Email or Username
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest request) {
        // Determine the login identifier (Email or Username)
        // If the frontend sends data in the 'email' field, we use that.
        // If it sends 'username', we use that.
        String identifier = (request.getEmail() != null && !request.getEmail().isEmpty())
                ? request.getEmail()
                : request.getUsername();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(identifier, request.getPassword())
        );

        final UserDetails user = userService.loadUserByUsername(identifier);
        return ResponseEntity.ok(jwtUtil.generateToken(user));
    }

    // 2. Register new user
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthenticationRequest request) {
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword());

        userService.registerUser(newUser);

        return ResponseEntity.ok("User registered successfully");
    }
}