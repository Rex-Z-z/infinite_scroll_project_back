package org.example.infinite_scroll_project_back.modules.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.infinite_scroll_project_back.modules.user.dto.ChangeUsernameRequest;
import org.example.infinite_scroll_project_back.modules.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth") // Requires JWT
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Change Username",
            description = "Allows the logged-in user to change their username."
    )
    @PutMapping("/change-username")
    public ResponseEntity<String> changeUsername(@RequestBody ChangeUsernameRequest request) {
        // 1. Get the current logged-in user's username from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // 2. Call service to update
        userService.changeUsername(currentUsername, request.getNewUsername());

        return ResponseEntity.ok("Username updated successfully. Please login again with the new username.");
    }

    @Operation(
            summary = "Upload Profile Image",
            description = "Uploads a profile image for the logged-in user and returns the URL."
    )
    @PostMapping(value = "/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadProfileImage(@RequestParam("file") MultipartFile file) {
        // 1. Get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // 2. Upload and get URL
        String imageUrl = userService.uploadProfileImage(currentUsername, file);

        return ResponseEntity.ok(imageUrl);
    }
}