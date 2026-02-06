package org.example.infinite_scroll_project_back.modules.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.infinite_scroll_project_back.modules.user.model.User;
import org.example.infinite_scroll_project_back.modules.user.repository.UserRepo;
import org.example.infinite_scroll_project_back.modules.user.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;

    // 1. Load user by email OR username
    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // Search by email OR username
        return userRepo.findByEmailOrUsername(identifier)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with identifier: " + identifier));
    }

    // 2. Register new user
    @Override
    public void registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
    }

    // 3. Check if user exists by email
    @Override
    public boolean userExists(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    // 4. Change username
    @Override
    public void changeUsername(String currentUsername, String newUsername) {
        // 1. Check if the new username is already taken
        if (userRepo.findByEmailOrUsername(newUsername).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // 2. Update the username
        userRepo.updateUsername(currentUsername, newUsername);
    }

    // 5. Reset password
    @Override
    public void resetPassword(String email, String newPassword) {
        // 1. Check if user exists
        if (!userExists(email)) {
            throw new RuntimeException("User with this email does not exist");
        }

        // 2. Encode the new password
        String encodedPassword = passwordEncoder.encode(newPassword);

        // 3. Update the password
        userRepo.updatePassword(email, encodedPassword);
    }

    // 6. Upload profile image
    @Override
    public String uploadProfileImage(String username, MultipartFile file) {
        // 1. Upload to Cloudinary and get the URL
        String imageUrl = cloudinaryService.uploadFile(file);

        // 2. Update the database with the cloud URL
        userRepo.updateProfileImage(username, imageUrl);

        return imageUrl;
    }
}