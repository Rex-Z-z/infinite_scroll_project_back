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

    // Login remains mostly the same
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        // We can cast details to UserDetails or just use loadUserByUsername
        final UserDetails user = userService.loadUserByUsername(request.getEmail());
        return ResponseEntity.ok(jwtUtil.generateToken(user));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthenticationRequest request) {
        // User already exist
        if (userService.userExists(request.getEmail())) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        // Create a new User object
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());

        // Use the service to save it (which handles hashing)
        userService.registerUser(newUser);

        return ResponseEntity.ok("User registered successfully");
    }
}