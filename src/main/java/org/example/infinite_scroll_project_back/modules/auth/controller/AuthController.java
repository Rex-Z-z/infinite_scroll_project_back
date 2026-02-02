package org.example.infinite_scroll_project_back.modules.auth.controller;

import lombok.RequiredArgsConstructor;
import org.example.infinite_scroll_project_back.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService; // Or your custom UserService

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
        return ResponseEntity.ok(jwtUtil.generateToken(user));
    }
}